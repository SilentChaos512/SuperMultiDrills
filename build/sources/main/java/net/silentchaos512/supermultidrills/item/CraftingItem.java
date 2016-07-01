package net.silentchaos512.supermultidrills.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.api.FunOresAPI;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.lib.item.ItemNamedSubtypesSorted;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;

public class CraftingItem extends ItemNamedSubtypesSorted {

  public static final String[] NAMES = { Names.MAGNETIC_ROD, Names.HEAVY_MAGNETIC_ROD,
      Names.BATTERY_GAUGE, Names.REDSTONE_ALLOY_INGOT, Names.REDSTONE_ALLOY_PLATE };

  public CraftingItem() {

    super(NAMES, NAMES, SuperMultiDrills.MOD_ID, Names.CRAFTING_ITEM);
  }

  @Override
  public void addRecipes() {

    final boolean funOres = SuperMultiDrills.instance.foundFunOres;

    // Magnetic rod
    if (funOres) {
      GameRegistry.addRecipe(new ShapedOreRecipe(getStack(Names.MAGNETIC_ROD, 4), "r", "i", "r",
          'r', "plateRedstoneAlloy", 'i', "plateIron"));
    } else {
      GameRegistry.addRecipe(new ShapedOreRecipe(getStack(Names.MAGNETIC_ROD, 2), " ri", "rir",
          "ir ", 'i', "ingotIron", 'r', "dustRedstone"));
    }

    // Heavy magnetic rod
    GameRegistry.addShapedRecipe(getStack(Names.HEAVY_MAGNETIC_ROD), "rr", "rr", 'r',
        getStack(Names.MAGNETIC_ROD));

    if (funOres) {
      // Redstone Alloy Ingot
      AlloySmelterRecipeObject iron = new AlloySmelterRecipeObject("ingotIron", 1);
      AlloySmelterRecipeObject redstone = new AlloySmelterRecipeObject("dustRedstone", 4);
      FunOresAPI.addAlloySmelterRecipe(getStack(Names.REDSTONE_ALLOY_INGOT), 400, 1.0f, iron,
          redstone);

      // Redstone Alloy Plate
      FunOresAPI.addPlateRecipe(getStack(Names.REDSTONE_ALLOY_PLATE), "ingotRedstoneAlloy");
    }

    // Battery Gauge
    GameRegistry.addRecipe(new ShapedOreRecipe(getStack(Names.BATTERY_GAUGE), "olo", "rgb", "ada",
        'o', "obsidian", 'l', "blockGlass", 'r', "dyeRed", 'g', "dyeGreen", 'b', "dyeBlue",
        'a', funOres ? "plateGold" : "ingotGold", 'd', "dustRedstone"));
  }

  @Override
  public void addOreDict() {

    OreDictionary.registerOre("ingotRedstoneAlloy", getStack(Names.REDSTONE_ALLOY_INGOT));
    OreDictionary.registerOre("plateRedstoneAlloy", getStack(Names.REDSTONE_ALLOY_PLATE));
  }
}
