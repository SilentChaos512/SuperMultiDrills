package net.silentchaos512.supermultidrills.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class Config {

  public static int battery0MaxCharge = 5000;
  public static int battery1MaxCharge = 25000;
  public static int battery2MaxCharge = 100000;
  public static int battery3MaxCharge = 250000;
  public static int battery4MaxCharge = 1000000;
  public static int motor0Level = 2;
  public static int motor1Level = 3;
  public static int motor2Level = 4;
  public static float energyCostPerSpeedLevel = 0.08f;
  public static boolean printMiningCost = false;

  public static final String CAT_DEBUG = "debug";
  public static final String CAT_ITEM = "items";

  private static Configuration c;

  public static void init(File file) {

    c = new Configuration(file);

    // Load the stuffs.
    battery0MaxCharge = c.getInt("Battery0.MaxCharge", CAT_ITEM, battery0MaxCharge, 0, 1000000000,
        "Maximum capacity for drills with the tier 0 battery.");
    battery1MaxCharge = c.getInt("Battery1.MaxCharge", CAT_ITEM, battery1MaxCharge, 0, 1000000000,
        "Maximum capacity for drills with the tier 1 battery.");
    battery2MaxCharge = c.getInt("Battery2.MaxCharge", CAT_ITEM, battery2MaxCharge, 0, 1000000000,
        "Maximum capacity for drills with the tier 2 battery.");
    battery3MaxCharge = c.getInt("Battery3.MaxCharge", CAT_ITEM, battery3MaxCharge, 0, 1000000000,
        "Maximum capacity for drills with the tier 3 battery.");
    battery4MaxCharge = c.getInt("Battery4.MaxCharge", CAT_ITEM, battery4MaxCharge, 0, 1000000000,
        "Maximum capacity for drills with the tier 4 battery.");

    motor0Level = c.getInt("Motor0.MiningLevel", CAT_ITEM, motor0Level, 0, 100,
        "The harvest level for the tier 0 motor.");
    motor1Level = c.getInt("Motor1.MiningLevel", CAT_ITEM, motor1Level, 0, 100,
        "The harvest level for the tier 1 motor.");
    motor2Level = c.getInt("Motor2.MiningLevel", CAT_ITEM, motor2Level, 0, 100,
        "The harvest level for the tier 2 motor.");

    energyCostPerSpeedLevel = c.getFloat("Speed.ExtraCost", CAT_ITEM, energyCostPerSpeedLevel, 0,
        1.0f, "The extra energy cost (ratio) per speed upgrade on a drill.");

    printMiningCost = c.getBoolean("PrintMiningCost", CAT_DEBUG, printMiningCost,
        "Print the energy cost each time a block is mined.");

    try {
      c.load();
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
