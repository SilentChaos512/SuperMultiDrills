package net.silentchaos512.supermultidrills.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.silentchaos512.lib.recipe.IRecipeSL;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.supermultidrills.item.ModItems;

public class RecipeDisassembleDrill implements IRecipeSL {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    boolean foundDisassemblyKit = false;
    ItemStack drill = null;

    ItemStack stack;

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        if (stack.getItem() == ModItems.disassemblyKit && !foundDisassemblyKit)
          foundDisassemblyKit = true;
        else if (stack.getItem() == ModItems.drill && drill == null)
          drill = stack;
        else
          return StackHelper.empty();
      }
    }

    if (!foundDisassemblyKit || drill == null) return StackHelper.empty();

    return new ItemStack(ModItems.disassemblyKit);
  }
}
