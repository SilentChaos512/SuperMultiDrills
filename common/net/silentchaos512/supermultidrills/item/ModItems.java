package net.silentchaos512.supermultidrills.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.recipe.RecipeCraftDrill;
import net.silentchaos512.supermultidrills.recipe.RecipeUpgradeDrill;
import net.silentchaos512.supermultidrills.registry.SRegistry;

public class ModItems {

  public static Drill drill;
  public static DrillHead drillHead;
  public static DrillMotor drillMotor;
  public static DrillBattery drillBattery;
  public static DrillChassis drillChassis;
  public static DrillUpgrade drillUpgrade;
  public static CraftingItem craftingItem;

  public static void init() {

    drill = (Drill) SRegistry.registerItem(new Drill(), Names.DRILL);
    drillHead = (DrillHead) SRegistry.registerItem(new DrillHead(), Names.DRILL_HEAD);
    drillMotor = (DrillMotor) SRegistry.registerItem(new DrillMotor(), Names.DRILL_MOTOR);
    drillBattery = (DrillBattery) SRegistry.registerItem(new DrillBattery(), Names.DRILL_BATTERY);
    drillChassis = (DrillChassis) SRegistry.registerItem(new DrillChassis(), Names.DRILL_CHASSIS);
    drillUpgrade = (DrillUpgrade) SRegistry.registerItem(new DrillUpgrade(), Names.DRILL_UPGRADE);
    craftingItem = (CraftingItem) SRegistry.registerItem(new CraftingItem(), Names.CRAFTING_ITEM);
  }

  public static void initItemRecipes() {

    GameRegistry.addRecipe(new RecipeCraftDrill());
    GameRegistry.addRecipe(new RecipeUpgradeDrill());

    RecipeSorter.INSTANCE.register(Strings.RESOURCE_PREFIX + "RecipeCraftDrill",
        RecipeCraftDrill.class, Category.SHAPED, "after:minecraft:shapeless");
    RecipeSorter.INSTANCE.register(Strings.RESOURCE_PREFIX + "RecipeUpgradeDrill",
        RecipeUpgradeDrill.class, Category.SHAPELESS, "after:minecraft:shapeless");
  }
}
