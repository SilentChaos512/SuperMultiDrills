package net.silentchaos512.supermultidrills.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHelper {

  public static void addItemToInventoryOrDrop(EntityPlayer player, ItemStack stack) {

    if (!player.inventory.addItemStackToInventory(stack)) {
      // Spawn item entity
      EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY + 1.5,
          player.posZ, stack);
      player.worldObj.spawnEntityInWorld(entityItem);
    }
  }
  
  public static boolean isItemDye(ItemStack stack) {
    
    for (int id : OreDictionary.getOreIDs(stack)) {
      if (OreDictionary.getOreName(id).startsWith("dye")) { // This may be a bit naive?
        return true;
      }
    }
    return false;
  }
  
  public static ItemStack oreDictDyeToVanilla(ItemStack stack) {
    
    for (int id : OreDictionary.getOreIDs(stack)) {
      String name = OreDictionary.getOreName(id);
      if (name.startsWith("dye")) {
        name = name.substring(3).toLowerCase();
        if (name.equals("lightgray")) {
          name = "silver";
        }
        for (int i = 0; i < ItemDye.field_150923_a.length; ++i) {
          if (ItemDye.field_150923_a[i].toLowerCase().equals(name)) {
            return new ItemStack(Items.dye, 1, i);
          }
        }
      }
    }
    return null;
  }
}
