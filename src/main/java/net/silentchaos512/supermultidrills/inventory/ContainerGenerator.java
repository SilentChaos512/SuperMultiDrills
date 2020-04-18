//package net.silentchaos512.supermultidrills.inventory;
//
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.inventory.Container;
//import net.minecraft.inventory.IInventory;
//import net.minecraft.inventory.Slot;
//import net.minecraft.item.ItemStack;
//
//
//public class ContainerGenerator extends Container {
//  
//  private final IInventory tileGenerator;
//  
//  public ContainerGenerator(InventoryPlayer playerInventory, IInventory generator) {
//
//    this.tileGenerator = generator;
//    addSlotToContainer(new Slot(generator, 0, 56, 35));
//    addSlotToContainer(new Slot(generator, 1, 111, 35));
//    addSlotToContainer(new Slot(generator, 2, 130, 35));
//    
//    int i;
//    for (i = 0; i < 3; ++i) {
//      for (int j = 0; j < 9; ++j) {
//        this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
//      }
//    }
//
//    for (i = 0; i < 9; ++i) {
//      this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
//    }
//  }
//
//  @Override
//  public boolean canInteractWith(EntityPlayer player) {
//
//    return tileGenerator.isUsableByPlayer(player);
//  }
//
//  @Override
//  public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
//
//    ItemStack stack = null;
//    Slot slot = (Slot) this.inventorySlots.get(slotIndex);
//
//    if (slot != null && slot.getHasStack()) {
//      ItemStack stack1 = slot.getStack();
//      stack = stack1.copy();
//
//      if (slotIndex == 1) {
//        if (!this.mergeItemStack(stack1, 2, 38, true)) {
//          return null;
//        }
//
//        slot.onSlotChange(stack1, stack);
//      } else if (slotIndex != 0) {
//        if (tileGenerator.isItemValidForSlot(0, stack1)) {
//          if (!this.mergeItemStack(stack1, 0, 1, false)) {
//            return null;
//          }
//        } else if (slotIndex >= 3 && slotIndex < 30) {
//          if (!this.mergeItemStack(stack1, 30, 39, false)) {
//            return null;
//          }
//        } else if (slotIndex >= 30 && slotIndex < 39 && !this.mergeItemStack(stack1, 3, 30, false)) {
//          return null;
//        }
//      } else if (!this.mergeItemStack(stack1, 3, 39, false)) {
//        return null;
//      }
//      
//      if (stack1.stackSize == 0) {
//        slot.putStack((ItemStack) null);
//      } else {
//        slot.onSlotChanged();
//      }
//      
//      if (stack1.stackSize == stack.stackSize) {
//        return null;
//      }
//      
//      slot.onPickupFromSlot(player, stack1);
//    }
//    
//    return stack;
//  }
//}
