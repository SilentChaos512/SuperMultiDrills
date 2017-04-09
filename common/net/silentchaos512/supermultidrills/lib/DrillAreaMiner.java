package net.silentchaos512.supermultidrills.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.silentchaos512.supermultidrills.item.Drill;

public class DrillAreaMiner {

  public static boolean isToolEffective(ItemStack tool, IBlockState state) {

    if (!(tool.getItem() instanceof Drill)) {
      return false;
    }

    Drill drill = (Drill) tool.getItem();
    Material material = state.getMaterial();
    boolean toolEffective = drill.canHarvestBlock(state, tool); // ForgeHooks.isToolEffective(tool, block, meta);

    if (toolEffective) {
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

  public static int tryActivate(ItemStack tool, BlockPos pos, EntityPlayer player) {

    IBlockState state = player.world.getBlockState(pos);

    if (!(tool.getItem() instanceof ItemTool) || state == null || player.isSneaking()) {
      return 0;
    }

    boolean toolEffective = isToolEffective(tool, state);
    if (!toolEffective) {
      return 0;
    }

    RayTraceResult mop = raytraceFromEntity(player.world, player, false, 4.5);
    if (mop == null) {
      return 0;
    }
    int sideHit = mop.sideHit.getIndex();

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

    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();
    for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
      for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
        for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
          if (xPos == x && yPos == y && zPos == z) {
            continue;
          }

          if (breakExtraBlock(tool, player.world, new BlockPos(xPos, yPos, zPos), sideHit,
              player, pos)) {
            ++blocksBroken;
          }
        }
      }
    }

    return blocksBroken;
  }

  public static boolean breakExtraBlock(ItemStack tool, World world, BlockPos pos, int sidehit,
      EntityPlayer playerEntity, BlockPos refPos) {

    if (world.isAirBlock(pos))
      return false;

    if (!(playerEntity instanceof EntityPlayerMP)) {
      return false;
    }

    EntityPlayerMP player = (EntityPlayerMP) playerEntity;

    IBlockState state = world.getBlockState(pos);
    Block block = state.getBlock();

    if (!isToolEffective(tool, state)) {
      return false;
    }

    IBlockState refState = world.getBlockState(refPos);
    float refStrength = ForgeHooks.blockStrength(refState, player, world, refPos);
    float strength = ForgeHooks.blockStrength(state, player, world, pos);

    // LogHelper.list(Block.getIdFromBlock(refBlock), refStrength, strength, refStrength / strength);
    if (!ForgeHooks.canHarvestBlock(block, player, world, pos) || refStrength / strength > 10f) {
      return false;
    }

    int xpDrop = ForgeHooks.onBlockBreakEvent(world, player.interactionManager.getGameType(),
        player, pos);
    if (xpDrop < 0) {
      return false;
    }

    if (player.capabilities.isCreativeMode) {
      block.onBlockHarvested(world, pos, state, player);
      if (block.removedByPlayer(state, world, pos, player, true)) {
        block.onBlockDestroyedByPlayer(world, pos, state);
      }

      if (!world.isRemote) {
        player.connection.sendPacket(new SPacketBlockChange(world, pos));
      }
      return true;
    }

    player.getHeldItemMainhand().onBlockDestroyed(world, state, pos, player);

    if (!world.isRemote) {

      block.onBlockHarvested(world, pos, state, player);

      if (block.removedByPlayer(state, world, pos, player, true)) {
        block.onBlockDestroyedByPlayer(world, pos, state);
        block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool);
        block.dropXpOnBlockBreak(world, pos, xpDrop);
      }

      player.connection.sendPacket(new SPacketBlockChange(world, pos));
    } else {
      int meta = block.getMetaFromState(state);
      world.playEvent(2001, pos, Block.getIdFromBlock(block) + (meta << 12));
      if (block.removedByPlayer(state, world, pos, player, true)) {
        block.onBlockDestroyedByPlayer(world, pos, state);
      }

      tool.onBlockDestroyed(world, state, pos, player);
    }

    return true;
  }

  private static RayTraceResult raytraceFromEntity(World world, Entity player, boolean par3,
      double range) {

    float f = 1.0F;
    float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
    float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
    double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
    double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
    if (!world.isRemote && player instanceof EntityPlayer)
      d1 += 1.62D;
    double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
    Vec3d vec3 = new Vec3d(d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = range;
    if (player instanceof EntityPlayerMP) {
      d3 = ((EntityPlayerMP) player).interactionManager.getBlockReachDistance();
    }
    Vec3d vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
    return world.rayTraceBlocks(vec3, vec31, par3, !par3, par3);
  }
}
