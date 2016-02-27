package net.silentchaos512.supermultidrills.configuration;

import java.io.File;

import com.udojava.evalex.Expression;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class Config {

  public static float areaMinerSpeedMulti = 0.2f;

  public static int battery0MaxCharge = 2500;
  public static int battery1MaxCharge = 10000;
  public static int battery2MaxCharge = 100000;
  public static int battery3MaxCharge = 1000000;
  public static int battery4MaxCharge = 10000000;

  public static int motor0Level = 1;
  public static int motor1Level = 2;
  public static int motor2Level = 3;
  public static int motor3Level = 5;
  public static int motor4Level = 7;

  public static float motor0Boost = 0.6f;
  public static float motor1Boost = 0.8f;
  public static float motor2Boost = 1.0f;
  public static float motor3Boost = 1.2f;
  public static float motor4Boost = 1.4f;

  public static boolean showUncraftableHeads = false;
  public static boolean showSpawnableDrills = true;

  public static boolean useCustomEnergyExpression = false;
  public static String energyCostExpressionString = "(300 - 0.09 * durability) * (1 + 0.06 * efficiency) * hardness";
  //public static String energyCostExpressionString = "135 * (1 + COS(180 * durability / 4096)) * (1 + 0.08 * efficiency) * hardness";
  public static Expression energyCostExpression;

  public static boolean printMiningCost = false;

  public static final String CAT_MAIN = "main";
  public static final String CAT_DEBUG = CAT_MAIN + Configuration.CATEGORY_SPLITTER + "debug";
  public static final String CAT_BATTERY = CAT_MAIN + Configuration.CATEGORY_SPLITTER + "battery";
  public static final String CAT_MOTOR = CAT_MAIN + Configuration.CATEGORY_SPLITTER + "motor";
  public static final String CAT_MISC = CAT_MAIN + Configuration.CATEGORY_SPLITTER + "misc";
  public static final String CAT_MATH = CAT_MAIN + Configuration.CATEGORY_SPLITTER + "math";

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

      // Load the stuffs.
      areaMinerSpeedMulti = c.getFloat("AreaMinerSpeedMultiplier", CAT_MISC,
          areaMinerSpeedMulti, 0.01f, 1.0f,
          "The dig speed of drills with Area Miner is multiplied by this.");

      // Battery options.
      battery0MaxCharge = c.getInt("MaxCharge0", CAT_BATTERY, battery0MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 0 battery.");
      battery1MaxCharge = c.getInt("MaxCharge1", CAT_BATTERY, battery1MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 1 battery.");
      battery2MaxCharge = c.getInt("MaxCharge2", CAT_BATTERY, battery2MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 2 battery.");
      battery3MaxCharge = c.getInt("MaxCharge3", CAT_BATTERY, battery3MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 3 battery.");
      battery4MaxCharge = c.getInt("MaxCharge4", CAT_BATTERY, battery4MaxCharge, 0,
          Integer.MAX_VALUE, "Maximum capacity for drills with the tier 4 battery.");

      // Motor options.
      motor0Level = c.getInt("MiningLevel0", CAT_MOTOR, motor0Level, 0, 1000,
          "The harvest level for the tier 0 motor.");
      motor1Level = c.getInt("MiningLevel1", CAT_MOTOR, motor1Level, 0, 1000,
          "The harvest level for the tier 1 motor.");
      motor2Level = c.getInt("MiningLevel2", CAT_MOTOR, motor2Level, 0, 1000,
          "The harvest level for the tier 2 motor.");
      motor3Level = c.getInt("MiningLevel3", CAT_MOTOR, motor3Level, 0, 1000,
          "The harvest level for the tier 3 motor.");
      motor4Level = c.getInt("MiningLevel4", CAT_MOTOR, motor4Level, 0, 1000,
          "The harvest level for the tier 4 motor.");

      motor0Boost = c.getFloat("SpeedBoost0", CAT_MOTOR, motor0Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 0 motor");
      motor1Boost = c.getFloat("SpeedBoost1", CAT_MOTOR, motor1Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 1 motor");
      motor2Boost = c.getFloat("SpeedBoost2", CAT_MOTOR, motor2Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 2 motor");
      motor3Boost = c.getFloat("SpeedBoost3", CAT_MOTOR, motor3Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 3 motor");
      motor4Boost = c.getFloat("SpeedBoost4", CAT_MOTOR, motor4Boost, 0.01f, Float.MAX_VALUE,
          "The mining speed multiplier for drills with the tier 4 motor");

      // Show/hide stuff in NEI options.
      showUncraftableHeads = c.getBoolean("Head.ShowUncraftables", CAT_MISC, showUncraftableHeads,
          "Show the drill heads that are not craftable with the available mods.");
      showSpawnableDrills = c.getBoolean("Drill.ShowSpawnables", CAT_MISC, showSpawnableDrills,
          "Show some pre-made drills for creative/cheaty purposes.");

      // Energy cost expression
      useCustomEnergyExpression = c.getBoolean("UseCustomEnergyExpression", CAT_MATH,
          useCustomEnergyExpression,
          "Use the expression entered in EnergyToBreakBlock to calculate mining cost."
              + "If false, the default value will be used.");

      if (useCustomEnergyExpression) {
        energyCostExpressionString = c.getString("EnergyToBreakBlock", CAT_MATH,
            energyCostExpressionString, commentEnergyCostExpression);
      } else {
        // Generate the config option, but don't use it.
        c.getString("EnergyToBreakBlock", CAT_MATH, energyCostExpressionString,
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
