package net.silentchaos512.supermultidrills.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.funores.core.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.client.render.SmartModelDrill;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.wit.api.WitBlockInfoEvent;
import net.silentchaos512.wit.api.WitEntityInfoEvent;

public class DrillsForgeEventHandler {

  @SubscribeEvent
  public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {

    final Drill drill = ModItems.drill;
    ItemStack heldItem = event.entityPlayer.getCurrentEquippedItem();
    EntityPlayer player = event.entityPlayer;

    if (heldItem != null && heldItem.getItem() instanceof Drill) {
      boolean sneaking = player.isSneaking();
      boolean flying = player.capabilities.isFlying;
      boolean underwater = player.isInWater();
      boolean areaMiner = drill.getTagBoolean(heldItem, Drill.NBT_AREA_MINER);
      boolean gravitonGenerator = drill.getTagBoolean(heldItem, Drill.NBT_GRAVITON_GENERATOR);

      // Restore speed if mining while flying or underwater with graviton generator.
      if (gravitonGenerator && flying) {
        event.newSpeed *= 5;
      }
      if (gravitonGenerator && underwater) {
        event.newSpeed *= 5;
      }

      // Reduce speed of Area Miner
      if (areaMiner && !sneaking) {
        event.newSpeed *= Config.areaMinerSpeedMulti;
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onModelBake(ModelBakeEvent event) {

    ModelResourceLocation modelLocation = new ModelResourceLocation(
        SuperMultiDrills.MOD_ID + ":" + Names.DRILL, "inventory");
    Object object = event.modelRegistry.getObject(modelLocation);
    if (object instanceof IBakedModel) {
      IBakedModel existingModel = (IBakedModel) object;
      SmartModelDrill customModel = new SmartModelDrill(existingModel);
      event.modelRegistry.putObject(modelLocation, customModel);
    }
  }

  @SubscribeEvent
  public void onWitBlockInfo(WitBlockInfoEvent event) {

    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    ItemStack heldItem = player.getHeldItem();

    if (heldItem != null && heldItem.getItem() instanceof Drill) {
      int amount = ModItems.drill.getEnergyToBreakBlock(heldItem,
          event.blockState.getBlock().getBlockHardness(event.world, event.pos));
      if (amount > 0) {
        String str = LocalizationHelper.getLocalizedString("wit.supermultidrills:RFToBreak");
        event.lines.add(String.format(str, amount));
      }
    }
  }

//  @SubscribeEvent
//  public void onWitEntityInfo(WitEntityInfoEvent event) {
//
//    event.lines.add("Testing!");
//  }
}
