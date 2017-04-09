package net.silentchaos512.supermultidrills.recipe;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.silentchaos512.lib.recipe.IRecipeSL;
import net.silentchaos512.lib.util.DyeHelper;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.DrillHead;
import net.silentchaos512.supermultidrills.item.DrillMotor;
import net.silentchaos512.supermultidrills.item.DrillUpgrade;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.InventoryHelper;

public class RecipeUpgradeDrill implements IRecipeSL {

  @Override
  public ItemStack getCraftingResult(InventoryCrafting inv) {

    ItemStack stack;
    ItemStack drill = null;

    // Find the drill
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack) && stack.getItem() instanceof Drill) {
        drill = stack;
        break;
      }
    }

    // Did we get a drill?
    if (drill == null) {
      return StackHelper.empty();
    }

    // Copy the drill, we can't modify the original!
    ItemStack result = StackHelper.safeCopy(drill);

    // Find and apply all upgrades
    Item item;
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (StackHelper.isValid(stack)) {
        item = stack.getItem();
        if (item instanceof DrillUpgrade) {
          // Upgrades
          result = applyDrillUpgrade(result, stack);
        } else if (item instanceof DrillBattery) {
          // Battery change
          // Remove next 2 statements!
          ItemStack oldBattery = new ItemStack(ModItems.drillBattery, 1,
              ModItems.drill.getTag(result, Drill.NBT_BATTERY));
          ModItems.drillBattery.setTag(oldBattery, ModItems.drillBattery.NBT_ENERGY,
              ModItems.drill.getEnergyStored(result));

          ModItems.drill.setTag(result, Drill.NBT_BATTERY, stack.getItemDamage());
          ModItems.drill.setTag(result, Drill.NBT_ENERGY,
              ModItems.drillBattery.getEnergyStored(stack));
          // Cap to new max charge
          int maxEnergy = ModItems.drill.getMaxEnergyStored(result);
          if (ModItems.drill.getEnergyStored(result) > maxEnergy) {
            ModItems.drill.setTag(result, Drill.NBT_ENERGY, maxEnergy);
          }
        } else if (item instanceof DrillChassis) {
          // Chassis change (same as dyeing)
          ModItems.drill.setTag(result, Drill.NBT_CHASSIS, ~stack.getItemDamage() & 15);
        } else if (DyeHelper.isItemDye(stack)) {
          // Dye the chassis
          int value = ~DyeHelper.oreDictDyeToVanilla(stack).getItemDamage() & 15;
          ModItems.drill.setTag(result, Drill.NBT_CHASSIS, value);
        } else if (item instanceof DrillHead) {
          // Head change
          ModItems.drill.setTag(result, Drill.NBT_HEAD, stack.getItemDamage());
        } else if (item instanceof DrillMotor) {
          // Motor change
          ModItems.drill.setTag(result, Drill.NBT_MOTOR, stack.getItemDamage());
        } else if (InventoryHelper.isDrillHeadMaterial(stack)) {
          int id = InventoryHelper.getDrillHeadMaterialId(stack);
          ModItems.drill.setTag(result, Drill.NBT_HEAD_COAT, id);
        }
      }
    }

    return result;
  }

  private ItemStack applyDrillUpgrade(ItemStack drill, ItemStack upgrade) {

    // If the upgrade cannot be applied, empty will be returned, so this could happen.
    if (StackHelper.isEmpty(drill)) {
      return StackHelper.empty();
    }

    // This shouldn't happen, but...
    if (StackHelper.isEmpty(upgrade)) {
      SuperMultiDrills.logHelper.warning("RecipeUpgradeDrill.applyDrillUpgrade: upgrade is null!");
      return StackHelper.empty();
    }

    int meta = upgrade.getItemDamage();
    int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, drill);
    int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, drill);

    if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_SAW)) {
      // Saw
      if (ModItems.drill.getTagBoolean(drill, Drill.NBT_SAW)) {
        return StackHelper.empty();
      }
      ModItems.drill.setTagBoolean(drill, Drill.NBT_SAW, true);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_FORTUNE)) {
      // Fortune
      if (fortuneLevel >= 3 || silkTouchLevel > 0) {
        return StackHelper.empty();
      }
      return this.increaseEnchantmentLevel(drill, Enchantments.FORTUNE, 3);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_SILK)) {
      // Silk
      if (silkTouchLevel >= 1 || fortuneLevel > 0) {
        return StackHelper.empty();
      }
      return this.increaseEnchantmentLevel(drill, Enchantments.SILK_TOUCH, 1);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_SPEED)) {
      // Speed
      return this.increaseEnchantmentLevel(drill, Enchantments.EFFICIENCY, 5);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_SHARPNESS)) {
      // Sharpness
      return increaseEnchantmentLevel(drill, Enchantments.SHARPNESS, 5);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_AREA_MINER)) {
      // Area Miner
      if (ModItems.drill.getTagBoolean(drill, Drill.NBT_AREA_MINER)) {
        return StackHelper.empty();
      }
      ModItems.drill.setTagBoolean(drill, Drill.NBT_AREA_MINER, true);
    } else if (meta == ModItems.drillUpgrade.getMetaFor(Names.UPGRADE_GRAVITON_GENERATOR)) {
      // Graviton Generator
      if (ModItems.drill.getTagBoolean(drill, Drill.NBT_GRAVITON_GENERATOR)) {
        return StackHelper.empty();
      }
      ModItems.drill.setTagBoolean(drill, Drill.NBT_GRAVITON_GENERATOR, true);
    }

    return drill;
  }

  private ItemStack increaseEnchantmentLevel(ItemStack drill, Enchantment enchantment,
      int maxLevel) {

    int level = EnchantmentHelper.getEnchantmentLevel(enchantment, drill);
    if (level == 0) {
      drill.addEnchantment(enchantment, 1);
    } else if (level < maxLevel) {
      NBTTagCompound tagCompound;
      for (int i = 0; i < drill.getEnchantmentTagList().tagCount(); ++i) {
        tagCompound = (NBTTagCompound) drill.getEnchantmentTagList().getCompoundTagAt(i);
        int id = tagCompound.getShort("id");
        if (id == Enchantment.getEnchantmentID(enchantment)) {
          tagCompound.setShort("lvl", (short) (level + 1));
        }
      }
    } else {
      return StackHelper.empty();
    }
    return drill;
  }
}
