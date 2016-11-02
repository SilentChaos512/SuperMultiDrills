package net.silentchaos512.supermultidrills.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;

public class ItemDisassemblyKit extends ItemSL {

  public ItemDisassemblyKit() {

    super(1, SuperMultiDrills.MOD_ID, Names.DISASSEMBLER);
  }

  @Override
  public void addRecipes() {

    // Actual drill disassembly will be through a custom recipe handler (RecipeDisassembleDrill), obviously.
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "iri", "grg", "ili", 'i',
        "ingotIron", 'r', ModItems.craftingItem.magneticRod, 'l', "gemLapis", 'g', "ingotGold"));
  }
}
