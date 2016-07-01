package net.silentchaos512.supermultidrills.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.recipe.RecipeCraftDrill;
import net.silentchaos512.supermultidrills.recipe.RecipeUpgradeDrill;

public class ModItems {

  public static Drill drill = new Drill();
  public static DrillHead drillHead = new DrillHead();
  public static DrillMotor drillMotor = new DrillMotor();
  public static DrillBattery drillBattery = new DrillBattery();
  public static DrillChassis drillChassis = new DrillChassis();
  public static DrillUpgrade drillUpgrade = new DrillUpgrade();
  public static CraftingItem craftingItem = new CraftingItem();

  public static void init(SRegistry reg) {

    reg.registerItem(drill, Names.DRILL);
    reg.registerItem(drillHead);
    reg.registerItem(drillMotor);
    reg.registerItem(drillBattery);
    reg.registerItem(drillChassis);
    reg.registerItem(drillUpgrade);
    reg.registerItem(craftingItem);
  }

  public static void initItemRecipes(SRegistry reg) {

    try {
      reg.addRecipeHandler(RecipeCraftDrill.class, "RecipeCraftDrill", Category.SHAPELESS,
          "after:minecraft:shapeless");
      reg.addRecipeHandler(RecipeUpgradeDrill.class, "RecipeUpgradeDrill", Category.SHAPELESS,
          "after:minecraft:shapeless");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
