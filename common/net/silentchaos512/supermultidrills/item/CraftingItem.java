package net.silentchaos512.supermultidrills.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.api.FunOresAPI;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;

public class CraftingItem extends ItemSMD {

  public static final String[] NAMES = { Names.MAGNETIC_ROD, Names.HEAVY_MAGNETIC_ROD,
      Names.BATTERY_GAUGE, Names.REDSTONE_ALLOY_INGOT, Names.REDSTONE_ALLOY_PLATE };

  public CraftingItem() {

    super(NAMES.length);
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.CRAFTING_ITEM);
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
        'o', Blocks.obsidian, 'l', "blockGlass", 'r', "dyeRed", 'g', "dyeGreen", 'b', "dyeBlue",
        'a', funOres ? "plateGold" : "ingotGold", 'd', "dustRedstone"));
  }

  @Override
  public void addOreDict() {

    OreDictionary.registerOre("ingotRedstoneAlloy", getStack(Names.REDSTONE_ALLOY_INGOT));
    OreDictionary.registerOre("plateRedstoneAlloy", getStack(Names.REDSTONE_ALLOY_PLATE));
  }

  public ItemStack getStack(String name) {

    return this.getStack(name, 1);
  }

  public ItemStack getStack(String name, int count) {

    for (int i = 0; i < NAMES.length; ++i) {
      if (NAMES[i].equals(name)) {
        return new ItemStack(this, count, i);
      }
    }
    return null;
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[NAMES.length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = SuperMultiDrills.MOD_ID + ":" + NAMES[i];
    }
    return result;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    int meta = stack.getItemDamage();
    if (meta >= NAMES.length) {
      return super.getUnlocalizedName(stack);
    }
    return getUnlocalizedName(NAMES[meta]);
  }
}
