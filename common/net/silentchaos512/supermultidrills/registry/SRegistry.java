package net.silentchaos512.supermultidrills.registry;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.silentchaos512.supermultidrills.item.ItemSMD;
import net.silentchaos512.supermultidrills.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class SRegistry {

  private final static HashMap<String, Item> items = new HashMap<String, Item>();

  /**
   * Creates a new Item instance and add it to the hash map.
   * 
   * @param itemClass
   *          The Item to add.
   * @param key
   *          The name of the Item.
   * @param constructorParams
   *          The list of parameters for the constructor (minus the ID).
   */
  public static Item registerItem(Class<? extends Item> itemClass, String key,
      Object... constructorParams) {

    int i;

    try {
      // Create an array of the classes in the constructor parameters and the int for id.
      Class[] paramClasses = getParameterClasses(constructorParams);

      // Get the constructor for this Item.
      Constructor<?> c = itemClass.getDeclaredConstructor(paramClasses);

      // Instantiate and add to hash map.
      Item item = (Item) c.newInstance(constructorParams);
      items.put(key, item);
      GameRegistry.registerItem(item, key);
      return item;
    } catch (Exception e) {
      LogHelper.severe("Failed to register item " + key);
      e.printStackTrace();
    }
    return null;
  }

  private static Class[] getParameterClasses(Object[] params) {

    Class[] result = new Class[params.length];
    for (int i = 0; i < params.length; ++i) {
      result[i] = params[i].getClass();
      if (result[i] == Integer.class) {
        result[i] = int.class;
      } else if (result[i] == Float.class) {
        result[i] = float.class;
      } else if (result[i] == Boolean.class) {
        result[i] = boolean.class;
      }
    }
    return result;
  }

  /**
   * Calls the addRecipes and addOreDict methods of all Blocks and Items that can be cast to IAddRecipe. Should be
   * called after registering all Blocks and Items.
   */
  public static void addRecipesAndOreDictEntries() {

    for (Item item : items.values()) {
      if (item instanceof IAddRecipe) {
        ((IAddRecipe) item).addRecipes();
        ((IAddRecipe) item).addOreDict();
      }
    }
  }

  /**
   * Gets the Item registered with the given key.
   * 
   * @param key
   * @return
   */
  public static Item getItem(String key) {

    if (!items.containsKey(key)) {
      LogHelper.severe("No item with key " + key + "! This is a bug!");
    }

    return items.get(key);
  }

  /**
   * Gets the Item registered with the given key and cast to ItemSG, if possible. Returns null otherwise.
   * 
   * @param key
   * @return
   */
  public static ItemSMD getItemSG(String key) {

    if (!items.containsKey(key)) {
      LogHelper.severe("No item with key " + key + "! This is a bug!");
    }

    if (items.get(key) instanceof ItemSMD) {
      return (ItemSMD) items.get(key);
    } else {
      return null;
    }
  }
}
