package net.silentchaos512.supermultidrills.util;

import java.util.Date;
import java.util.logging.Logger;

import net.silentchaos512.supermultidrills.SuperMultiDrills;
import cpw.mods.fml.common.FMLLog;

public class LogHelper {

  public static void severe(Object object) {

    SuperMultiDrills.logger.error(object);
  }

  public static void debug(Object object) {

    SuperMultiDrills.logger.error(object);
    System.out.println(object);
  }

  public static void warning(Object object) {

    SuperMultiDrills.logger.warn(object);
  }

  public static void info(Object object) {

    SuperMultiDrills.logger.info(object);
  }

  /**
   * Prints a derp message to the console.
   */
  public static void derp() {

    debug("Derp!");
  }

  public static void derp(String message) {

    debug("Derp! " + message);
  }

  public static void derpRand() {

    String s = "";
    for (int i = 0; i < SuperMultiDrills.instance.random.nextInt(6); ++i) {
      s += " ";
    }
    debug(s + "Derp!");
  }

  public static void yay() {

    debug("Yay!");
  }

  // Prints XYZ coordinates in a nice format.
  public static String coord(int x, int y, int z) {

    return "(" + x + ", " + y + ", " + z + ")";
  }

  public static void list(Object... objects) {

    String s = "";
    for (int i = 0; i < objects.length; ++i) {
      if (i != 0) {
        s += ", ";
      }
      s += objects[i];
    }
    debug(s);
  }
}
