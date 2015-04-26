package net.silentchaos512.supermultidrills.item;

import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.recipe.RecipeCraftDrill;
import net.silentchaos512.supermultidrills.recipe.RecipeUpgradeDrill;
import net.silentchaos512.supermultidrills.registry.SRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

  public static Drill drill;
  public static DrillHead drillHead;
  public static DrillMotor drillMotor;
  public static DrillBattery drillBattery;
  public static DrillChassis drillChassis;
  public static DrillUpgrade drillUpgrade;
  public static CraftingItem craftingItem;

  public static void init() {

    drill = (Drill) SRegistry.registerItem(Drill.class, Names.DRILL);
    drillHead = (DrillHead) SRegistry.registerItem(DrillHead.class, Names.DRILL_HEAD);
    drillMotor = (DrillMotor) SRegistry.registerItem(DrillMotor.class, Names.DRILL_MOTOR);
    drillBattery = (DrillBattery) SRegistry.registerItem(DrillBattery.class, Names.DRILL_BATTERY);
    drillChassis = (DrillChassis) SRegistry.registerItem(DrillChassis.class, Names.DRILL_CHASSIS);
    drillUpgrade = (DrillUpgrade) SRegistry.registerItem(DrillUpgrade.class, Names.DRILL_UPGRADE);
    craftingItem = (CraftingItem) SRegistry.registerItem(CraftingItem.class, Names.CRAFTING_ITEM);
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
