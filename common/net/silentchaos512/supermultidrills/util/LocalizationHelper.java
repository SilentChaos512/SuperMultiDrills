package net.silentchaos512.supermultidrills.util;

import net.minecraft.util.StatCollector;

public class LocalizationHelper {

  public final static String MISC_PREFIX = "misc.supermultidrills:";
  public final static String ITEM_PREFIX = "item.supermultidrills:";

  public static String getItemDescription(String itemName, int index) {

    return getLocalizedString(getItemDescriptionKey(itemName, index));
  }

  public static String getItemDescriptionKey(String itemName, int index) {

    return ITEM_PREFIX + itemName + ".desc" + (index > 0 ? index : "");
  }

  public static String getLocalizedString(String key) {

    return StatCollector.translateToLocal(key).trim();
  }

  public static String getMiscText(String key) {

    return getLocalizedString(MISC_PREFIX + key);
  }

  public static String getOtherItemKey(String itemName, String key) {

    return getLocalizedString(ITEM_PREFIX + itemName + "." + key);
  }
}
