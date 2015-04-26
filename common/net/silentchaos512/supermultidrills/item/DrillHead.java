package net.silentchaos512.supermultidrills.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class DrillHead extends ItemSMD {

  public DrillHead() {

    this.icons = new IIcon[EnumDrillMaterial.values().length];
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
      if (i > 2) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, i), " mm", "mim", "im ",
            'm', material.getMaterial(), 'i', rod));
      } else {
        // Vanilla materials
        ItemStack stack = new ItemStack(Items.poisonous_potato);
        if (material == EnumDrillMaterial.IRON) {
          stack = new ItemStack(Items.iron_ingot);
        } else if (material == EnumDrillMaterial.GOLD) {
          stack = new ItemStack(Items.gold_ingot);
        } else if (material == EnumDrillMaterial.DIAMOND) {
          stack = new ItemStack(Items.diamond);
        }
        GameRegistry.addShapedRecipe(new ItemStack(this, 1, i), " mm", "mim", "im ", 'm', stack,
            'i', rod);
      }
    }
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    EnumDrillMaterial material = this.getDrillMaterial(stack);

    // Cost
    String s = EnumChatFormatting.GOLD
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningCost") + " "
        + EnumChatFormatting.GREEN + String.format("%.1f", material.getCostPerHardness()) + " "
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "RFPerHardness");
    list.add(s);
    
    // Speed
    s = EnumChatFormatting.GOLD
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningSpeed") + " "
        + EnumChatFormatting.DARK_PURPLE + String.format("%.1f", material.getEfficiency());
    list.add(s);
    
    // Damage
    s = EnumChatFormatting.GOLD
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "AttackDamage") + " "
        + EnumChatFormatting.DARK_RED + String.format("%.1f", material.getDamageVsEntity());
    list.add(s);
  }

  public EnumDrillMaterial getDrillMaterial(ItemStack stack) {

    int meta = stack.getItemDamage();
    if (meta >= EnumDrillMaterial.values().length) {
      meta = 0;
    }
    return EnumDrillMaterial.values()[meta];
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    EnumDrillMaterial material = this.getDrillMaterial(stack);
    return LocalizationHelper.ITEM_PREFIX + this.itemName + "_" + material.toString();
  }

  @Override
  public void registerIcons(IIconRegister reg) {

    for (int i = 0; i < icons.length; ++i) {
      EnumDrillMaterial material = EnumDrillMaterial.values()[i];
      icons[i] = reg.registerIcon(Strings.RESOURCE_PREFIX + this.itemName + "_"
          + material.toString());
    }
  }
}
