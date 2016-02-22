package net.silentchaos512.supermultidrills.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
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
    
    if (stack.getItem() == Items.iron_ingot) {
      return 0;
    } else if (stack.getItem() == Items.gold_ingot) {
      return 1;
    } else if (stack.getItem() == Items.diamond) {
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
        for (int i = 0; i < EnumDyeColor.values().length; ++i) {
          if (EnumDyeColor.values()[i].getUnlocalizedName().toLowerCase().equals(name)) {
            return new ItemStack(Items.dye, 1, i);
          }
        }
      }
    }
    return null;
  }
}
