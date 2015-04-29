package net.silentchaos512.supermultidrills.nei;

import net.minecraft.item.ItemStack;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;


public class NEISuperMultiDrillsConfig implements IConfigureNEI {

  @Override
  public void loadConfig() {
    
    // Hide uncraftable heads.
    for (int i = 0; i < EnumDrillMaterial.values().length; ++i) {
      if (!EnumDrillMaterial.values()[i].canCraftHead()) {
        API.hideItem(new ItemStack(ModItems.drillHead, 1, i));
      }
    }
  }

  @Override
  public String getName() {

    return "Super Multi-Drills NEI Plugin";
  }

  @Override
  public String getVersion() {

    return SuperMultiDrills.VERSION_NUMBER;
  }

}
