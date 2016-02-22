package net.silentchaos512.supermultidrills.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;

public class DrillHead extends ItemSMD {

  public DrillHead() {

    super(EnumDrillMaterial.values().length);
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_HEAD);
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    EnumDrillMaterial material;
    for (int i = 0; i < EnumDrillMaterial.values().length; ++i) {
      material = EnumDrillMaterial.values()[i];
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, i), " mm", "mim", "im ",
          'm', material.getMaterial(), 'i', rod));
    }
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    EnumDrillMaterial material = getDrillMaterial(stack);

    // Cost
    String s = EnumChatFormatting.GOLD
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningCost") + " "
        + EnumChatFormatting.GREEN + String.format("%.1f", material.getCostPerHardness()) + " "
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "RFPerHardness");
    list.add(s);

    // Speed
    s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningSpeed")
        + " " + EnumChatFormatting.DARK_PURPLE + String.format("%.1f", material.getEfficiency());
    list.add(s);

    // Damage
    s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "AttackDamage")
        + " " + EnumChatFormatting.DARK_RED + String.format("%.1f", material.getDamageVsEntity());
    list.add(s);

    if (advanced) {
      s = LocalizationHelper.getOtherItemKey(itemName, "Group");
      list.add(s + " " + material.getGroup());
      s = LocalizationHelper.getOtherItemKey(itemName, "Material");
      list.add(s + " " + material.getMaterial());
    }
  }

  public EnumDrillMaterial getDrillMaterial(ItemStack stack) {

    int meta = stack.getItemDamage();
    if (meta >= EnumDrillMaterial.values().length) {
      meta = 0;
    }
    return EnumDrillMaterial.values()[meta];
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (String group : EnumDrillMaterial.GROUPS_ORDERED) {
      ArrayList<EnumDrillMaterial> sub = EnumDrillMaterial.getAllInGroup(group);
      for (EnumDrillMaterial material : sub) {
        list.add(new ItemStack(this, 1, material.ordinal()));
      }
    }
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[EnumDrillMaterial.values().length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = getFullName() + "_" + EnumDrillMaterial.values()[i].toString();
    }
    return result;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    EnumDrillMaterial material = this.getDrillMaterial(stack);
    return LocalizationHelper.ITEM_PREFIX + this.itemName + "_" + material.toString();
  }
}
