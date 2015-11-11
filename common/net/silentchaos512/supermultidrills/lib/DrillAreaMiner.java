package net.silentchaos512.supermultidrills.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.ModItems;

public class DrillAreaMiner {
  
  public static boolean isToolEffective(ItemStack tool, Block block, int meta) {

    if (!(tool.getItem() instanceof Drill)) {
      return false;
    }
    
    Drill drill = (Drill) tool.getItem();
    Material material = block.getMaterial();
    boolean toolEffective = ForgeHooks.isToolEffective(tool, block, meta);

    if (drill.canHarvestBlock(block, tool)) {
      return true;
    }

    // Block material on basic materials list?
    if (Drill.effectiveMaterialsBasic.contains(material)) {
      return true; // Material is like stone/dirt/etc
    }
    // Or is the material on extra materials list?
    if (tool.getItem() instanceof Drill && drill.getTagBoolean(tool, Drill.NBT_SAW)) {
      if (Drill.effectiveMaterialsExtra.contains(material)) {
        return true; // Drill has saw and material is like wood/etc
      }
    }

    return toolEffective;
  }

  public static int tryActivate(ItemStack tool, int x, int y, int z, EntityPlayer player) {

    Block block = player.worldObj.getBlock(x, y, z);
    int meta = player.worldObj.getBlockMetadata(x, y, z);

    if (!(tool.getItem() instanceof ItemTool) || block == null || player.isSneaking()) {
      return 0;
    }

    boolean toolEffective = isToolEffective(tool, block, meta);
    if (!toolEffective) {
      return 0;
    }

    MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5);
    if (mop == null) {
      return 0;
    }
    int sideHit = mop.sideHit;

    int xRange = 1;
    int yRange = 1;
    int zRange = 0;
    switch (sideHit) {
      case 0:
      case 1:
        yRange = 0;
        zRange = 1;
        break;
      case 2:
      case 3:
        xRange = 1;
        zRange = 0;
        break;
      case 4:
      case 5:
        xRange = 0;
        zRange = 1;
        break;
    }

    int blocksBroken = 0;

    for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
      for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
        for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
          if (xPos == x && yPos == y && zPos == z) {
            continue;
          }

          if (breakExtraBlock(tool, player.worldObj, xPos, yPos, zPos, sideHit, player, x, y, z)) {
            ++blocksBroken;
          }
        }
      }
    }

    return blocksBroken;
  }

  public static boolean breakExtraBlock(ItemStack tool, World world, int x, int y, int z,
      int sidehit, EntityPlayer playerEntity, int refX, int refY, int refZ) {

    if (world.isAirBlock(x, y, z))
      return false;

    if (!(playerEntity instanceof EntityPlayerMP)) {
      return false;
    }

    EntityPlayerMP player = (EntityPlayerMP) playerEntity;

    Block block = world.getBlock(x, y, z);
    int meta = world.getBlockMetadata(x, y, z);

    if (!isToolEffective(tool, block, world.getBlockMetadata(x, y, z))) {
      return false;
    }

    Block refBlock = world.getBlock(refX, refY, refZ);
    float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
    float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

    // LogHelper.list(Block.getIdFromBlock(refBlock), refStrength, strength, refStrength / strength);
    if (!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f) {
      return false;
    }

    BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world,
        player.theItemInWorldManager.getGameType(), player, x, y, z);
    if (event.isCanceled()) {
      return false;
    }

    if (player.capabilities.isCreativeMode) {
      block.onBlockHarvested(world, x, y, z, meta, player);
      if (block.removedByPlayer(world, player, x, y, z, false)) {
        block.onBlockDestroyedByPlayer(world, x, y, z, meta);
      }

      if (!world.isRemote) {
        player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
      }
      return true;
    }

    player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

    if (!world.isRemote) {

      block.onBlockHarvested(world, x, y, z, meta, player);

      if (block.removedByPlayer(world, player, x, y, z, true)) {
        block.onBlockDestroyedByPlayer(world, x, y, z, meta);
        block.harvestBlock(world, player, x, y, z, meta);
        block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
      }

      player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
    } else {
      world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
      if (block.removedByPlayer(world, player, x, y, z, true)) {
        block.onBlockDestroyedByPlayer(world, x, y, z, meta);
      }

      ItemStack itemstack = player.getCurrentEquippedItem();
      if (itemstack != null) {
        itemstack.func_150999_a(world, block, x, y, z, player);

        if (itemstack.stackSize == 0) {
          player.destroyCurrentEquippedItem();
        }
      }
    }

    return true;
  }

  private static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3,
      double range) {

    float f = 1.0F;
    float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
    float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
    double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
    double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
    if (!world.isRemote && player instanceof EntityPlayer)
      d1 += 1.62D;
    double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
    Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = range;
    if (player instanceof EntityPlayerMP) {
      d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
    }
    Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
    return world.func_147447_a(vec3, vec31, par3, !par3, par3);
  }
}
