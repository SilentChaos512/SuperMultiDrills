package net.silentchaos512.supermultidrills.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentchaos512.lib.recipe.RecipeBase;
import net.silentchaos512.supermultidrills.item.ModItems;

public class RecipeDisassembleDrill extends RecipeBase {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    boolean foundDisassemblyKit = false;
    ItemStack drill = null;

    ItemStack stack;

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null) {
        if (stack.getItem() == ModItems.disassemblyKit && !foundDisassemblyKit)
          foundDisassemblyKit = true;
        else if (stack.getItem() == ModItems.drill && drill == null)
          drill = stack;
        else
          return null;
      }
    }

    if (!foundDisassemblyKit || drill == null) return null;

    return new ItemStack(ModItems.disassemblyKit);
  }
}
