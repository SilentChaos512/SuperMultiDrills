package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.registry.IAddRecipe;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;

public class ItemSMD extends Item implements IAddRecipe {

  public IIcon[] icons = null;
  protected String itemName = "null";
  
  public ItemSMD() {
    
    this.setCreativeTab(SuperMultiDrills.creativeTab);
  }
  
  /**
   * Should be overridden if the deriving class needs ore dictionary entries.
   */
  @Override
  public void addOreDict() {

  }

  /**
   * Adds all recipes to make this item to the GameRegistry. Used to separate out recipe code so that ModItems is easier
   * to read.
   */
  @Override
  public void addRecipes() {

  }
  
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

    int i = 1;
    String s = LocalizationHelper.getItemDescription(itemName, i);
    while (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, i)) && i < 8) {
      list.add(EnumChatFormatting.ITALIC + s);
      s = LocalizationHelper.getItemDescription(itemName, ++i);
    }

    if (i == 1) {
      s = LocalizationHelper.getItemDescription(itemName, 0);
      if (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, 0))) {
        list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getItemDescription(itemName, 0));
      }
    }
  }
  
  @Override
  public IIcon getIconFromDamage(int meta) {

    if (hasSubtypes && icons != null && meta < icons.length) {
      return icons[meta];
    } else {
      return super.getIconFromDamage(meta);
    }
  }

  public String getLocalizedName(ItemStack stack) {

    return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name");
  }
  
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    if (hasSubtypes) {
      for (int i = 0; i < icons.length; ++i) {
        list.add(new ItemStack(this, 1, i));
      }
    } else {
      list.add(new ItemStack(this, 1, 0));
    }
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    int d = stack.getItemDamage();
    String s = LocalizationHelper.ITEM_PREFIX + itemName;

    if (hasSubtypes) {
      s += d;
    }

    return s;
  }

  public String getUnlocalizedName(String itemName) {

    return LocalizationHelper.ITEM_PREFIX + itemName;
  }
  
  @Override
  public void registerIcons(IIconRegister reg) {

    if (this.hasSubtypes && icons != null) {
      for (int i = 0; i < icons.length; ++i) {
        icons[i] = reg.registerIcon(Strings.RESOURCE_PREFIX + itemName + i);
      }
    } else {
      itemIcon = reg.registerIcon(Strings.RESOURCE_PREFIX + itemName);
    }
  }
  
  @Override
  public Item setUnlocalizedName(String itemName) {

    this.itemName = itemName;
    return this;
  }
}
