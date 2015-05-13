package net.silentchaos512.supermultidrills.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.supermultidrills.util.LogHelper;

import com.udojava.evalex.Expression;

public class Config {

  public static int battery0MaxCharge = 5000;
  public static int battery1MaxCharge = 25000;
  public static int battery2MaxCharge = 100000;
  public static int battery3MaxCharge = 250000;
  public static int battery4MaxCharge = 1000000;
  public static int motor0Level = 2;
  public static int motor1Level = 3;
  public static int motor2Level = 4;
  public static boolean showUncraftableHeads = false;

  public static String energyCostExpressionString = "(270 - 0.12 * durability) * (1 + 0.08 * efficiency) * hardness";
  public static Expression energyCostExpression;

  public static boolean printMiningCost = false;

  public static final String CAT_DEBUG = "debug";
  public static final String CAT_ITEM = "items";

  public static final String commentEnergyCostExpression = "Uses the EvalEx expression parser (https://github.com/uklimaschewski/EvalEx)\n\n"
      + "Available variables:\n"
      + "durability: The 'max uses' of the drill head material.\n"
      + "efficiency: The level of the Efficiency enchantment (or number of speed upgrades) on the drill.\n"
      + "silk_touch: The level of the Silk Touch enchantment on the drill.\n"
      + "fortune: The level of the Fortune enchantment on the drill.\n"
      + "hardness: The hardness of the block being mined.\n"
      + "mining_speed: The digging speed of the drill head material.\n";

  private static Configuration c;

  public static void init(File file) {

    c = new Configuration(file);

    try {
      c.load();

      // Load the stuffs.
      battery0MaxCharge = c.getInt("Battery0.MaxCharge", CAT_ITEM, battery0MaxCharge, 0,
          1000000000, "Maximum capacity for drills with the tier 0 battery.");
      battery1MaxCharge = c.getInt("Battery1.MaxCharge", CAT_ITEM, battery1MaxCharge, 0,
          1000000000, "Maximum capacity for drills with the tier 1 battery.");
      battery2MaxCharge = c.getInt("Battery2.MaxCharge", CAT_ITEM, battery2MaxCharge, 0,
          1000000000, "Maximum capacity for drills with the tier 2 battery.");
      battery3MaxCharge = c.getInt("Battery3.MaxCharge", CAT_ITEM, battery3MaxCharge, 0,
          1000000000, "Maximum capacity for drills with the tier 3 battery.");
      battery4MaxCharge = c.getInt("Battery4.MaxCharge", CAT_ITEM, battery4MaxCharge, 0,
          1000000000, "Maximum capacity for drills with the tier 4 battery.");

      motor0Level = c.getInt("Motor0.MiningLevel", CAT_ITEM, motor0Level, 0, 100,
          "The harvest level for the tier 0 motor.");
      motor1Level = c.getInt("Motor1.MiningLevel", CAT_ITEM, motor1Level, 0, 100,
          "The harvest level for the tier 1 motor.");
      motor2Level = c.getInt("Motor2.MiningLevel", CAT_ITEM, motor2Level, 0, 100,
          "The harvest level for the tier 2 motor.");

      showUncraftableHeads = c.getBoolean("Head.ShowUncraftables", CAT_ITEM, showUncraftableHeads,
          "Show the drill heads that are not craftable with the available mods.");

      energyCostExpressionString = c.getString("Math.EnergyToBreakBlock", CAT_ITEM,
          energyCostExpressionString, commentEnergyCostExpression);
      energyCostExpression = new Expression(energyCostExpressionString);

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
