package net.silentchaos512.supermultidrills.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentchaos512.lib.recipe.IRecipeSL;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.DrillHead;
import net.silentchaos512.supermultidrills.item.DrillMotor;
import net.silentchaos512.supermultidrills.item.ModItems;

public class RecipeCraftDrill implements IRecipeSL {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    ItemStack head = null;
    ItemStack motor = null;
    ItemStack battery = null;
    ItemStack chassis = null;
    ItemStack stack;

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        if (stack.getItem() instanceof DrillHead) {
          head = stack;
        } else if (stack.getItem() instanceof DrillMotor) {
          motor = stack;
        } else if (stack.getItem() instanceof DrillBattery) {
          battery = stack;
        } else if (stack.getItem() instanceof DrillChassis) {
          chassis = stack;
        } else {
          return StackHelper.empty();
        }
      }
    }

    if (head == null || motor == null || battery == null || chassis == null) {
      return StackHelper.empty();
    }

    Drill drill = ModItems.drill;
    stack = new ItemStack(drill);
    drill.setTag(stack, drill.NBT_HEAD, head.getItemDamage());
    drill.setTag(stack, drill.NBT_HEAD_COAT, -1);
    drill.setTag(stack, drill.NBT_MOTOR, motor.getItemDamage());
    drill.setTag(stack, drill.NBT_BATTERY, battery.getItemDamage());
    drill.setTag(stack, drill.NBT_CHASSIS, ~chassis.getItemDamage() & 15);
    drill.setTag(stack, drill.NBT_ENERGY, ModItems.drillBattery.getEnergyStored(battery));

    return stack;
  }
}
