package net.silentchaos512.supermultidrills.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.util.LogHelper;

@JEIPlugin
public class SuperMultiDrillsPlugin implements IModPlugin {

  public static IJeiHelpers jeiHelper;

  public static void doItemBlacklisting() {

    // Hide uncraftable drills?
    if (!Config.showUncraftableHeads) {
      for (EnumDrillMaterial material : EnumDrillMaterial.values()) {
        LogHelper.list(material, material.canCraftHead());
        if (!material.canCraftHead()) {
          jeiHelper.getItemBlacklist().addItemToBlacklist(material.getHead());
        }
      }
    }
  }

  @Override
  public void onItemRegistryAvailable(IItemRegistry arg0) {

  }

  @Override
  public void onJeiHelpersAvailable(IJeiHelpers arg0) {

    LogHelper.info("Drills JEI helper test");
    jeiHelper = arg0;
    jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(ModItems.drillBattery, DrillBattery.NBT_BASE);
//    jeiHelper.getNbtIgnoreList().ignoreNbtTagNames(ModItems.drillBattery, DrillBattery.NBT_ENERGY);
  }

  @Override
  public void onRecipeRegistryAvailable(IRecipeRegistry arg0) {

  }

  @Override
  public void register(IModRegistry reg) {

    IGuiHelper guiHelper = jeiHelper.getGuiHelper();
  }
}
