package net.silentchaos512.supermultidrills.compat.wit;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import net.silentchaos512.wit.api.WitBlockInfoEvent;

public class DrillsWitHandler {

  @SubscribeEvent
  public void onWitBlockInfo(WitBlockInfoEvent event) {

    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    ItemStack heldItem = player.getHeldItem();

    boolean holdingDrill = heldItem != null && heldItem.getItem() instanceof Drill;
    boolean showInfo = Config.showEnergyToBreak
        ? (Config.showEnergyToBreakSneakOnly ? event.isSneaking : true) : false;

    if (holdingDrill && showInfo) {
      int amount = ModItems.drill.getEnergyToBreakBlock(heldItem,
          event.blockState.getBlock().getBlockHardness(event.world, event.pos));
      if (amount > 0) {
        String str = LocalizationHelper.getLocalizedString("wit.supermultidrills:RFToBreak");
        event.lines.add(String.format(str, amount));
      }
    }
  }
}
