package net.silentchaos512.supermultidrills.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.lib.Names;

public class DrillHead extends ItemSL {

  public DrillHead() {

    super(EnumDrillMaterial.values().length, SuperMultiDrills.MOD_ID, Names.DRILL_HEAD);
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.HEAVY_MAGNETIC_ROD);
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
    LocalizationHelper loc = SuperMultiDrills.localizationHelper;

    // Cost
    String s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "MiningCost") + " "
        + TextFormatting.GREEN + String.format("%.1f", material.getCostPerHardness()) + " "
        + loc.getItemSubText(Names.DRILL, "RFPerHardness");
    list.add(s);

    // Speed
    s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "MiningSpeed") + " "
        + TextFormatting.DARK_PURPLE + String.format("%.1f", material.getEfficiency());
    list.add(s);

    // Damage
    s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "AttackDamage") + " "
        + TextFormatting.DARK_RED + String.format("%.1f", material.getDamageVsEntity());
    list.add(s);

    if (advanced) {
      s = loc.getItemSubText(itemName, "Group");
      list.add(s + " " + material.getGroup());
      s = loc.getItemSubText(itemName, "Material");
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
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> result = Lists.newArrayList();
    for (EnumDrillMaterial mat : EnumDrillMaterial.values()) {
      String str = getFullName() + "_" + mat.toString();
      result.add(new ModelResourceLocation(str, "inventory"));
    }
    return result;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    EnumDrillMaterial material = this.getDrillMaterial(stack);
    return "item." + SuperMultiDrills.RESOURCE_PREFIX + itemName + "_" + material.toString();
  }
}
