package net.silentchaos512.supermultidrills.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingItem extends ItemSMD {

  public static final String[] NAMES = { Names.MAGNETIC_ROD };

  public CraftingItem() {

    icons = new IIcon[NAMES.length];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.CRAFTING_ITEM);
  }

  @Override
  public void addRecipes() {

    // Magnetic rod
    GameRegistry.addShapedRecipe(getStack(Names.MAGNETIC_ROD, 2), " ri", "rir", "ir ", 'i',
        Items.iron_ingot, 'r', Items.redstone);
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
