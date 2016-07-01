package net.silentchaos512.supermultidrills.item;

import java.util.IllegalFormatException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.Names;

public class DrillMotor extends ItemSL {

  public static final int SUB_TYPE_COUNT = 6;
  public static final int CREATIVE_MOTOR_LEVEL = 99;
  public static final float CREATIVE_MOTOR_BOOST = 99.99f;

  public DrillMotor() {

    super(SUB_TYPE_COUNT, SuperMultiDrills.MOD_ID, Names.DRILL_MOTOR);
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

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    Item itemMaterial = Item.getByNameOrId("EnderIO:itemMaterial");

    // Motor0
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), " im", "gmg", "mi ", 'i',
        "ingotIron", 'm', rod, 'g', "gearStone"));
    // Motor 1
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), "iim", "gmg", "mii", 'i',
        "ingotElectricalSteel", 'm', rod, 'g', "ingotConductiveIron"));
    // Motor 2
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), "iim", "gmg", "mii", 'i',
        "ingotEnergeticAlloy", 'm', rod, 'g', "itemPulsatingCrystal"));
    // Motor 3
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 3), "2im", "gmg", "mi2", 'i',
        "ingotVibrantAlloy", '2', "ingotDarkSteel", 'm', rod, 'g', "itemVibrantCrystal"));
    // Motor 4
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 4), "iim", "gmg", "mii", 'i',
        "ingotVibrantAlloy", 'm', rod, 'g', "itemEnderCrystal"));
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

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    // TODO: Oredict compat
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 0), "iim", "rmr", "mii", 'i',
        Blocks.COBBLESTONE, 'm', rod, 'r', Items.REDSTONE);
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 1), "iim", "rmr", "mii", 'i',
        Items.IRON_INGOT, 'm', rod, 'r', Items.REDSTONE);
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 2), "iim", "rmr", "mii", 'i', Items.DIAMOND,
        'm', rod, 'r', Items.REDSTONE);
    GameRegistry.addShapedRecipe(new ItemStack(this, 1, 3), "idm", "rmr", "mii", 'i',
        Items.ENDER_EYE, 'm', rod, 'r', Items.REDSTONE, 'd', Items.DIAMOND);
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
      LocalizationHelper loc = SuperMultiDrills.localizationHelper;
      list.add(TextFormatting.AQUA + loc.getItemSubText(itemName, "miningLevel", level));
      list.add(TextFormatting.AQUA + loc.getItemSubText(itemName, "miningSpeed", boost));
    } catch (IllegalFormatException ex) {
      // Fallback if localization is wrong.
      list.add(TextFormatting.AQUA + String.format("Mining level: %d", level));
      list.add(TextFormatting.AQUA + String.format("Mining speed: %d%%", boost));
      list.add(TextFormatting.RED + "Your localization file contains an error!");
    }
  }
}
