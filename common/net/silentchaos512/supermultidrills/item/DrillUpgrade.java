package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class DrillUpgrade extends ItemSMD {

  public static final String[] NAMES = { Names.UPGRADE_SAW, Names.UPGRADE_SPEED,
      Names.UPGRADE_SILK, Names.UPGRADE_FORTUNE };

  public DrillUpgrade() {

    icons = new IIcon[NAMES.length];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_UPGRADE);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    if (stack.getItemDamage() < 0 || stack.getItemDamage() >= NAMES.length) {
      return;
    }

    String itemName = NAMES[stack.getItemDamage()];
    int i = 1;
    String s = LocalizationHelper.getItemDescription(itemName, i);
    while (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, i)) && i < 8) {
      list.add(EnumChatFormatting.GREEN + s);
      s = LocalizationHelper.getItemDescription(itemName, ++i);
    }

    if (i == 1) {
      s = LocalizationHelper.getItemDescription(itemName, 0);
      if (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, 0))) {
        list.add(EnumChatFormatting.GREEN + LocalizationHelper.getItemDescription(itemName, 0));
      }
    }
    
    list.add(EnumChatFormatting.DARK_GRAY
        + LocalizationHelper.getItemDescription(Names.DRILL_UPGRADE, 0));
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    // Saw
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SAW)),
        " i ", "imi", "mi ", 'i', Items.iron_ingot, 'm', rod);

    // Speed
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SPEED)),
        " m ", "grg", " m ", 'm', rod, 'g', Items.gold_ingot, 'r', Items.redstone);

    // Silk
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SILK)),
        "eee", "rgr", 'e', Items.emerald, 'r', rod, 'g', Items.gold_ingot);

    // Fortune
    GameRegistry.addShapedRecipe(
        new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_FORTUNE)), "ddd", "rgr", 'd',
        Items.diamond, 'r', rod, 'g', Items.gold_ingot);
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
