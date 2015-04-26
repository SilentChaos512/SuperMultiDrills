package net.silentchaos512.supermultidrills.item;

import java.util.IllegalFormatException;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;

public class DrillMotor extends ItemSMD {

  public DrillMotor() {

    this.icons = new IIcon[3];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_MOTOR);
  }

  @Override
  public void addRecipes() {
    
    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    if (SuperMultiDrills.instance.foundEnderIO) {
      // Ender IO recipes
    }
    if (SuperMultiDrills.instance.foundThermalFoundation) {
      // Thermal Foundation recipes
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), " im", "gmg", "mi ",
          'i', Items.iron_ingot, 'm', rod, 'g', "gearTin"));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), " im", "gmg", "mi ",
          'i', Items.diamond, 'm', rod, 'g', "gearElectrum"));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), " im", "gmg", "mi ",
          'i', "ingotSilver", 'm', rod, 'g', "gearEnderium"));
    }
    if (!SuperMultiDrills.instance.foundEnderIO
        && !SuperMultiDrills.instance.foundThermalFoundation) {
      // Vanilla
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 0), " im", "rmr", "mi ", 'i',
          Items.iron_ingot, 'm', rod, 'r',
          Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 1), " im", "rmr", "mi ", 'i',
          Items.diamond, 'm', rod, 'r',
          Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 2), " dm", "rmr", "mi ", 'i',
          Items.ender_pearl, 'm', rod, 'r',
          Items.redstone, 'd', Items.diamond);
    }
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    int level = 0;
    switch (stack.getItemDamage()) {
      case 2:
        level = Config.motor2Level;
        break;
      case 1:
        level = Config.motor1Level;
        break;
      default:
        level = Config.motor0Level;
    }
    String str = LocalizationHelper.getItemDescription(this.itemName, 0);
    try {
      list.add(EnumChatFormatting.AQUA + String.format(str, level));
    } catch (IllegalFormatException ex) {
      // Fallback if localization is wrong.
      list.add(EnumChatFormatting.AQUA + String.format("Mining level %d", level));
      list.add(EnumChatFormatting.RED + "Your localization file contains an error!");
    }
  }
}
