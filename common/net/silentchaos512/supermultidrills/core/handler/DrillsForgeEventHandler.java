package net.silentchaos512.supermultidrills.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.ModItems;

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
}
