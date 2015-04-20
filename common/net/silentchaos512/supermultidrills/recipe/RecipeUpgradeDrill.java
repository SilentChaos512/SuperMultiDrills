package net.silentchaos512.supermultidrills.recipe;

import java.util.Set;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.DrillHead;
import net.silentchaos512.supermultidrills.item.DrillMotor;
import net.silentchaos512.supermultidrills.item.DrillUpgrade;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LogHelper;

import com.google.common.collect.ImmutableSet;

public class RecipeUpgradeDrill implements IRecipe {

  public static final Set upgrades = ImmutableSet.of(ModItems.drillUpgrade, ModItems.drillBattery,
      ModItems.drillChassis, ModItems.drillHead, ModItems.drillMotor, Items.dye);

  @Override
  public boolean matches(InventoryCrafting inv, World world) {

    int countDrill = 0;
    int countUpgrade = 0;
    ItemStack stack;
    
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null) {
        if (stack.getItem() instanceof Drill) {
          ++countDrill;
        } else if (upgrades.contains(stack.getItem())) {
          ++countUpgrade;
        } else {
          return false;
        }
      }
    }
    
//    LogHelper.debug(countDrill + ", " + countUpgrade);
    return countDrill == 1 && countUpgrade >= 1;
  }

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    ItemStack stack;
    ItemStack drill = null;
    
    // Find the drill
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null && stack.getItem() instanceof Drill) {
        drill = stack;
        break;
      }
    }
    
    // Did we get a drill?
    if (drill == null) {
      return null;
    }
    
    // Copy the drill
    ItemStack result = drill.copy();
    
    // Find and apply all upgrades
    Item item;
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null) {
        item = stack.getItem();
        if (item instanceof DrillUpgrade) {
          // TODO: Check that upgrade can be applied?
          result = applyDrillUpgrade(result, stack);
        } else if (item instanceof DrillBattery) {
          ModItems.drill.setTag(result, Drill.NBT_BATTERY, stack.getItemDamage());
        } else if (item instanceof DrillChassis) { // TODO: Dyes also!
          ModItems.drill.setTag(result, Drill.NBT_CHASSIS, stack.getItemDamage());
        } else if (item instanceof DrillHead) {
          ModItems.drill.setTag(result, Drill.NBT_HEAD, stack.getItemDamage());
        } else if (item instanceof DrillMotor) {
          ModItems.drill.setTag(result, Drill.NBT_MOTOR, stack.getItemDamage());
        }
      }
    }
    
    return result;
  }
  
  private ItemStack applyDrillUpgrade(ItemStack drill, ItemStack upgrade) {
    
    if (upgrade.getItemDamage() == ModItems.drillUpgrade.getMetaForName(Names.UPGRADE_SAW)) {
      ModItems.drill.setTagBoolean(drill, Drill.NBT_SAW, true);
    } else if (upgrade.getItemDamage() == ModItems.drillUpgrade.getMetaForName(Names.UPGRADE_SILK)) {
      drill.addEnchantment(Enchantment.silkTouch, 1);
    } else if (upgrade.getItemDamage() == ModItems.drillUpgrade.getMetaForName(Names.UPGRADE_SPEED)) {
//      int speed = ModItems.drill.getTag(drill, Drill.NBT_SPEED);
//      ModItems.drill.setTag(drill, Drill.NBT_SPEED, speed + 1);
      int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, drill);
      if (level == 0) {
        drill.addEnchantment(Enchantment.efficiency, 1);
      } else {
        NBTTagCompound tagCompound;
        for (int i = 0; i < drill.getEnchantmentTagList().tagCount(); ++i) {
          tagCompound = (NBTTagCompound) drill.getEnchantmentTagList().getCompoundTagAt(i);
          int id = tagCompound.getShort("id");
          if (id == Enchantment.efficiency.effectId) {
            tagCompound.setShort("lvl", (short) (level + 1));
          }
        }
      }
    }
    
    return drill;
  }

  @Override
  public int getRecipeSize() {

    return 9;
  }

  @Override
  public ItemStack getRecipeOutput() {

    // TODO Auto-generated method stub
    return null;
  }

}
