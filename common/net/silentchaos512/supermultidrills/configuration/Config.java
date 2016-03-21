package net.silentchaos512.supermultidrills.configuration;

import java.io.File;

import com.udojava.evalex.Expression;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class Config {

  public static float areaMinerSpeedMulti = 0.2f;
  public static int battery0MaxCharge = 5000;
  public static int battery1MaxCharge = 25000;
  public static int battery2MaxCharge = 100000;
  public static int battery3MaxCharge = 250000;
  public static int battery4MaxCharge = 1000000;
  public static int motor0Level = 2;
  public static int motor1Level = 3;
  public static int motor2Level = 4;
  public static float motor0Boost = 0.8f;
  public static float motor1Boost = 1.0f;
  public static float motor2Boost = 1.2f;
  public static boolean showUncraftableHeads = false;
  public static boolean showSpawnableDrills = true;

  public static boolean disableBotaniaHeads = false;
  public static boolean disableEnderIOHeads = false;
  public static boolean disableMetallurgyHeads = false;
  public static boolean disableSilentGemsHeads = false;
  public static boolean disableThermalFoundationHeads = false;
  public static boolean disableTinkersConstructHeads = false;
  public static boolean disableAllThermalFoundationRecipes = false;
  public static boolean disableAllEnderIORecipes = false;
  public static boolean disableAllMekanismRecipes = false;

  public static boolean useCustomEnergyExpression = false;
  public static String energyCostExpressionString = "(280 - 0.085 * durability) * (1 + 0.08 * efficiency) * hardness";
  public static Expression energyCostExpression;

  public static boolean printMiningCost = false;

  public static final String CAT_DEBUG = "debug";
  public static final String CAT_ITEM = "items";
  public static final String CAT_RECIPE = "recipes";

  public static final String commentEnergyCostExpression = "The expression that determines the cost of mining a block with a drill.\n"
      + "Note that Math.UseCustomEnergyExpression must be true, or the default will be used!\n\n"
      + "Uses the EvalEx expression parser (https://github.com/uklimaschewski/EvalEx)\n\n"
      + "Available variables:\n" + "durability: The 'max uses' of the drill head material.\n"
      + "efficiency: The level of the Efficiency enchantment (or number of speed upgrades) on the drill.\n"
      + "silk_touch: The level of the Silk Touch enchantment on the drill.\n"
      + "fortune: The level of the Fortune enchantment on the drill.\n"
      + "hardness: The hardness of the block being mined.\n"
      + "mining_speed: The digging speed of the drill head material.\n"
      + "motor_boost: The digging speed multiplier of the motor.\n";

  private static Configuration c;

  public static void init(File file) {

    c = new Configuration(file);

    try {
      c.load();

      disableBotaniaHeads = c.getBoolean("Head.DisableBotania", CAT_ITEM, false,
          "Disable drill head group's recipes.");
      disableEnderIOHeads = c.getBoolean("Head.DisableEnderIO", CAT_ITEM, false,
          "Disable drill head group's recipes.");
      disableMetallurgyHeads = c.getBoolean("Head.DisableMetallurgy", CAT_ITEM, false,
          "Disable drill head group's recipes.");
      disableSilentGemsHeads = c.getBoolean("Head.DisableSilentsGems", CAT_ITEM, false,
          "Disable drill head group's recipes.");
      disableThermalFoundationHeads = c.getBoolean("Head.DisableThermalFoundation", CAT_ITEM, false,
          "Disable drill head group's recipes.");
      disableTinkersConstructHeads = c.getBoolean("Head.DisableTinkersConstruct", CAT_ITEM, false,
          "Disable drill head group's recipes.");

      disableAllThermalFoundationRecipes = c.getBoolean("DisableThermalFoundation", CAT_RECIPE,
          false,
          "Disables all recipes that use Thermal Foundation materials (does not include drill heads).");
      disableAllEnderIORecipes = c.getBoolean("DisableEnderIO", CAT_RECIPE, false,
          "Disables all recipes that use Ender IO materials (does not include drill heads).");
      disableAllMekanismRecipes = c.getBoolean("DisableMekanism", CAT_RECIPE, false,
          "Disables all recipes that use Mekanism materials (does not include drill heads).");

      areaMinerSpeedMulti = c.getFloat("Drill.AreaMinerSpeedMultiplier", CAT_ITEM,
          areaMinerSpeedMulti, 0.01f, 1.0f,
          "The dig speed of drills with Area Miner is multiplied by this.");

      // Battery options.
      battery0MaxCharge = c.getInt("Battery0.MaxCharge", CAT_ITEM, battery0MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 0 battery.");
      battery1MaxCharge = c.getInt("Battery1.MaxCharge", CAT_ITEM, battery1MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 1 battery.");
      battery2MaxCharge = c.getInt("Battery2.MaxCharge", CAT_ITEM, battery2MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 2 battery.");
      battery3MaxCharge = c.getInt("Battery3.MaxCharge", CAT_ITEM, battery3MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 3 battery.");
      battery4MaxCharge = c.getInt("Battery4.MaxCharge", CAT_ITEM, battery4MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 4 battery.");

      // Motor options.
      motor0Level = c.getInt("Motor0.MiningLevel", CAT_ITEM, motor0Level, 0, 1000,
          "The harvest level for the tier 0 motor.");
      motor1Level = c.getInt("Motor1.MiningLevel", CAT_ITEM, motor1Level, 0, 1000,
          "The harvest level for the tier 1 motor.");
      motor2Level = c.getInt("Motor2.MiningLevel", CAT_ITEM, motor2Level, 0, 1000,
          "The harvest level for the tier 2 motor.");

      motor0Boost = c.getFloat("Motor0.SpeedBoost", CAT_ITEM, motor0Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 0 motor");
      motor1Boost = c.getFloat("Motor1.SpeedBoost", CAT_ITEM, motor1Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 1 motor");
      motor2Boost = c.getFloat("Motor2.SpeedBoost", CAT_ITEM, motor2Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 2 motor");

      // Show/hide stuff in NEI options.
      showUncraftableHeads = c.getBoolean("Head.ShowUncraftables", CAT_ITEM, showUncraftableHeads,
          "Show the drill heads that are not craftable with the available mods.");
      showSpawnableDrills = c.getBoolean("Drill.ShowSpawnables", CAT_ITEM, showSpawnableDrills,
          "Show some pre-made drills for creative/cheaty purposes.");

      // Energy cost expression
      useCustomEnergyExpression = c.getBoolean("Math.UseCustomEnergyExpression", CAT_ITEM,
          useCustomEnergyExpression,
          "Use the expression entered in Math.EnergyToBreakBlock to calculate mining cost."
              + "If false, the default value will be used.");

      if (useCustomEnergyExpression) {
        energyCostExpressionString = c.getString("Math.EnergyToBreakBlock", CAT_ITEM,
            energyCostExpressionString, commentEnergyCostExpression);
      } else {
        // Generate the config option, but don't use it.
        c.getString("Math.EnergyToBreakBlock", CAT_ITEM, energyCostExpressionString,
            commentEnergyCostExpression);
      }

      energyCostExpression = new Expression(energyCostExpressionString);

      // Debug
      printMiningCost = c.getBoolean("PrintMiningCost", CAT_DEBUG, printMiningCost,
          "Print the energy cost each time a block is mined.");
    } catch (Exception e) {
      LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
    } finally {
      c.save();
    }
  }

  public static void save() {

    c.save();
  }
}
