package net.silentchaos512.supermultidrills.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;

public class InventoryHelper {

  public static void addItemToInventoryOrDrop(EntityPlayer player, ItemStack stack) {

    if (!player.inventory.addItemStackToInventory(stack)) {
      // Spawn item entity
      EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY + 1.5,
          player.posZ, stack);
      player.worldObj.spawnEntityInWorld(entityItem);
    }
  }
  
  public static boolean isDrillHeadMaterial(ItemStack stack) {
    
    return getDrillHeadMaterialId(stack) >= 0;
  }
  
  public static int getDrillHeadMaterialId(ItemStack stack) {
    
    if (stack.getItem() == Items.IRON_INGOT) {
      return 0;
    } else if (stack.getItem() == Items.GOLD_INGOT) {
      return 1;
    } else if (stack.getItem() == Items.DIAMOND) {
      return 2;
    }
    
    for (int id : OreDictionary.getOreIDs(stack)) {
      for (int i = 0; i < EnumDrillMaterial.values().length; ++i) {
        if (EnumDrillMaterial.values()[i].getMaterial().equals(OreDictionary.getOreName(id))) {
          return i;
        }
      }
    }

    return -1;
  }
}
