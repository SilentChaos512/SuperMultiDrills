package net.silentchaos512.supermultidrills.compat.wit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.wit.api.WitBlockInfoEvent;

public class DrillsWitHandler {

  @SubscribeEvent
  public void onWitBlockInfo(WitBlockInfoEvent event) {

    EntityPlayer player = Minecraft.getMinecraft().player;
    ItemStack heldItem = player.getHeldItemMainhand();

    boolean holdingDrill = heldItem != null && heldItem.getItem() instanceof Drill;
    boolean showInfo = Config.showEnergyToBreak
        ? (Config.showEnergyToBreakSneakOnly ? event.isSneaking : true) : false;

    if (holdingDrill && showInfo) {
      int amount = ModItems.drill.getEnergyToBreakBlock(heldItem,
          event.blockState.getBlockHardness(event.world, event.pos));
      if (amount > 0) {
        event.lines.add(SuperMultiDrills.localizationHelper
            .getLocalizedString("wit.supermultidrills:RFToBreak", amount));
      }
    }
  }
}
