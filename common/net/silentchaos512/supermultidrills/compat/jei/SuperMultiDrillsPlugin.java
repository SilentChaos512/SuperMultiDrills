package net.silentchaos512.supermultidrills.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;

@JEIPlugin
public class SuperMultiDrillsPlugin implements IModPlugin {

  public static IJeiHelpers jeiHelper;

  @Override
  public void register(IModRegistry reg) {

    jeiHelper = reg.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelper.getGuiHelper();
    doItemBlacklisting();
  }

  public static void doItemBlacklisting() {

    // Hide uncraftable drills?
    if (!Config.showUncraftableHeads) {
      for (EnumDrillMaterial material : EnumDrillMaterial.values()) {
        SuperMultiDrills.logHelper
            .info(material + (material.canCraftHead() ? " (craftable)" : " (not craftable)"));
        if (!material.canCraftHead()) {
          jeiHelper.getItemBlacklist().addItemToBlacklist(material.getHead());
        }
      }
    }
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime arg0) {

  }
}
