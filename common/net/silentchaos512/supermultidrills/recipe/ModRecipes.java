package net.silentchaos512.supermultidrills.recipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.silentchaos512.lib.registry.SRegistry;

public class ModRecipes {

  public static IRecipe craftDrill;
  public static IRecipe upgradeDrill;
  public static IRecipe disassembleDrill;

  public static void init(SRegistry reg) {

    Category cat = Category.SHAPELESS;
    String dep = "after:minecraft:shapeless";
    try {
      craftDrill = reg.addRecipeHandler(RecipeCraftDrill.class, "RecipeCraftDrill", cat, dep);
      upgradeDrill = reg.addRecipeHandler(RecipeUpgradeDrill.class, "RecipeUpgradeDrill", cat, dep);
      disassembleDrill = reg.addRecipeHandler(RecipeDisassembleDrill.class, "RecipeDisassembleDrill", cat, dep);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
