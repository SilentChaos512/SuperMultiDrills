package net.silentchaos512.supermultidrills.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;

public class DrillUpgrade extends ItemSMD {

  public static final String[] NAMES = { Names.UPGRADE_SAW, Names.UPGRADE_SILK, Names.UPGRADE_SPEED };

  public DrillUpgrade() {

    icons = new IIcon[NAMES.length];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_UPGRADE);
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    // Saw
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SAW)),
        " i ", "imi", "mi ", 'i', Items.iron_ingot, 'm', rod);

    // Silk
    // TODO

    // Speed
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SPEED)),
        " m ", "grg", " m ", 'm', rod, 'g', Items.gold_ingot, 'r', Items.redstone);
  }

  public int getMetaForName(String name) {

    for (int i = 0; i < NAMES.length; ++i) {
      if (NAMES[i].equals(name)) {
        return i;
      }
    }
    return -1;
  }

  public ItemStack getStack(String name, int count) {

    for (int i = 0; i < NAMES.length; ++i) {
      if (NAMES[i].equals(name)) {
        return new ItemStack(this, count, i);
      }
    }
    return null;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    int meta = stack.getItemDamage();
    if (meta >= NAMES.length) {
      return super.getUnlocalizedName(stack);
    }
    return getUnlocalizedName(NAMES[meta]);
  }

  @Override
  public void registerIcons(IIconRegister iconRegister) {

    for (int i = 0; i < NAMES.length; ++i) {
      icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + NAMES[i]);
    }
  }
}
