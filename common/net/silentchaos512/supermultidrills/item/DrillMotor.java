package net.silentchaos512.supermultidrills.item;

import java.util.IllegalFormatException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;

public class DrillMotor extends ItemSMD {

  public static final int SUB_TYPE_COUNT = 6;
  public static final int CREATIVE_MOTOR_LEVEL = 99;
  public static final float CREATIVE_MOTOR_BOOST = 99.99f;

  public DrillMotor() {

    super(SUB_TYPE_COUNT);
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_MOTOR);
  }

  @Override
  public void addRecipes() {

    boolean addVanilla = true;

    if (SuperMultiDrills.instance.foundFunOres) {
      addRecipesFunOres();
      addVanilla = false;
    }
    if (SuperMultiDrills.instance.foundEnderIO) {
      addRecipesEnderIO();
      addVanilla = false;
    }
    if (SuperMultiDrills.instance.foundMekanism) {
      addRecipesMekanism();
      addVanilla = false;
    }
    if (SuperMultiDrills.instance.foundThermalFoundation) {
      addRecipesThermalFoundation();
      addVanilla = false;
    }
    if (addVanilla) {
      addRecipesVanilla();
    }
  }

  private void addRecipesFunOres() {

    // Copper, Iron, Steel, Enderium, Prismarinium

    String line1 = "2pr";
    String line2 = "grg";
    String line3 = "rp2";

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD);

    // Motor 0
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), line1, line2, line3, 'r',
        rod, 'p', "plateCopper", '2', "plateZinc", 'g', "gearCopper"));
    // Motor 1
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), line1, line2, line3, 'r',
        rod, 'p', "plateIron", '2', "plateTin", 'g', "gearIron"));
    // Motor 2
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), line1, line2, line3, 'r',
        rod, 'p', "plateSteel", '2', "gemDiamond", 'g', "gearSteel"));
    // Motor 3
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 3), line1, line2, line3, 'r',
        rod, 'p', "plateEnderium", '2', "plateGold", 'g', "gearEnderium"));
    // Motor 4
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 4), line1, line2, line3, 'r',
        rod, 'p', "platePrismarinium", '2', "plateElectrum", 'g', "gearPrismarinium"));
  }

  private void addRecipesEnderIO() {

    // ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    //
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), " im", "gmg", "mi ", 'i',
    // "ingotIron", 'm', rod, 'g', "ingotElectricalSteel"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), " im", "gmg", "mi ", 'i',
    // "gemDiamond", 'm', rod, 'g', "ingotEnergeticAlloy"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), " im", "gmg", "mi ", 'i',
    // "ingotDarkSteel", 'm', rod, 'g', "ingotPhasedGold"));
  }

  private void addRecipesMekanism() {

    // ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    //
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), " im", "gmg", "mi ", 'i',
    // "ingotIron", 'm', rod, 'g', "alloyAdvanced"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), " im", "gmg", "mi ", 'i',
    // "ingotOsmium", 'm', rod, 'g', "alloyElite"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), " im", "gmg", "mi ", 'i',
    // "ingotRefinedObsidian", 'm', rod, 'g', "alloyUltimate"));
  }

  private void addRecipesThermalFoundation() {

    // ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    //
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), " im", "gmg", "mi ", 'i',
    // "ingotIron", 'm', rod, 'g', "gearTin"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), " im", "gmg", "mi ", 'i',
    // "gemDiamond", 'm', rod, 'g', "gearElectrum"));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), " im", "gmg", "mi ", 'i',
    // "ingotSilver", 'm', rod, 'g', "gearEnderium"));
  }

  private void addRecipesVanilla() {

    // FIXME
    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 0), " im", "rmr", "mi ", 'i',
        Items.iron_ingot, 'm', rod, 'r', Items.redstone);
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 1), " im", "rmr", "mi ", 'i', Items.diamond,
        'm', rod, 'r', Items.redstone);
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 2), " dm", "rmr", "mi ", 'i',
        Items.ender_pearl, 'm', rod, 'r', Items.redstone, 'd', Items.diamond);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    int level = 0;
    int boost = 0;
    switch (stack.getItemDamage()) {
      case 5:
        level = CREATIVE_MOTOR_LEVEL;
        boost = (int) (CREATIVE_MOTOR_BOOST * 100);
        break;
      case 4:
        level = Config.motor4Level;
        boost = (int) (Config.motor4Boost * 100);
        break;
      case 3:
        level = Config.motor3Level;
        boost = (int) (Config.motor3Boost * 100);
        break;
      case 2:
        level = Config.motor2Level;
        boost = (int) (Config.motor2Boost * 100);
        break;
      case 1:
        level = Config.motor1Level;
        boost = (int) (Config.motor1Boost * 100);
        break;
      default:
        level = Config.motor0Level;
        boost = (int) (Config.motor0Boost * 100);
    }

    try {
      String str = LocalizationHelper.getItemDescription(itemName, 1);
      list.add(EnumChatFormatting.AQUA + String.format(str, level));
      str = LocalizationHelper.getItemDescription(itemName, 2);
      list.add(EnumChatFormatting.AQUA + String.format(str, boost));
    } catch (IllegalFormatException ex) {
      // Fallback if localization is wrong.
      list.add(EnumChatFormatting.AQUA + String.format("Mining level: %d", level));
      list.add(EnumChatFormatting.AQUA + String.format("Mining speed: %d%%", boost));
      list.add(EnumChatFormatting.RED + "Your localization file contains an error!");
    }
  }
}
