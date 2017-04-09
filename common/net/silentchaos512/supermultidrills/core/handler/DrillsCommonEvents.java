package net.silentchaos512.supermultidrills.core.handler;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.DrillHead;
import net.silentchaos512.supermultidrills.item.DrillMotor;
import net.silentchaos512.supermultidrills.item.DrillUpgrade;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.recipe.ModRecipes;
import net.silentchaos512.supermultidrills.util.InventoryHelper;

public class DrillsCommonEvents {

  @SubscribeEvent
  public void onItemCraftedEvent(ItemCraftedEvent event) {

    if (event.craftMatrix instanceof InventoryCrafting) {
      InventoryCrafting inv = (InventoryCrafting) event.craftMatrix;
      EntityPlayer player = event.player;
      World world = player.world;

      if (ModRecipes.upgradeDrill.matches(inv, world))
        handleDrillUpgrade(inv, player, world);
      else if (ModRecipes.disassembleDrill.matches(inv, world))
        handleDrillDisassembly(inv, player, world);
    }
  }

  private void handleDrillUpgrade(InventoryCrafting inv, EntityPlayer player, World world) {

    ItemStack stack = null;
    ItemStack drill = null;

    ItemStack oldBattery = null;
    ItemStack oldHead = null;
    ItemStack oldMotor = null;
    ItemStack oldChassis = null;

    boolean replaceBattery = false;
    boolean replaceHead = false;
    boolean replaceMotor = false;
    boolean replaceChassis = false;

    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null) {
        if (stack.getItem() instanceof Drill) {
          // Get old parts
          drill = stack;
          oldBattery = ModItems.drill.getBattery(drill);
          oldHead = ModItems.drill.getHead(drill);
          oldMotor = ModItems.drill.getMotor(drill);
          oldChassis = ModItems.drill.getChassis(drill);
        } else if (stack.getItem() instanceof DrillBattery) {
          // New battery
          replaceBattery = true;
        } else if (stack.getItem() instanceof DrillHead) {
          // New head
          replaceHead = true;
        } else if (stack.getItem() instanceof DrillMotor) {
          // New motor
          replaceMotor = true;
        } else if (stack.getItem() instanceof DrillChassis) {
          // New chassis
          replaceChassis = true;
        }
      }
    }

    if (replaceBattery && oldBattery != null)
      InventoryHelper.addItemToInventoryOrDrop(player, oldBattery);
    if (replaceHead && oldHead != null)
      InventoryHelper.addItemToInventoryOrDrop(player, oldHead);
    if (replaceMotor && oldMotor != null)
      InventoryHelper.addItemToInventoryOrDrop(player, oldMotor);
    if (replaceChassis && oldChassis != null)
      InventoryHelper.addItemToInventoryOrDrop(player, oldChassis);
  }

  private void handleDrillDisassembly(InventoryCrafting inv, EntityPlayer player, World world) {

    ItemStack stack;
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null && stack.getItem() == ModItems.drill) {
        // Found the drill.
        List<ItemStack> partsToReturn = Lists.newArrayList();
        Drill drill = ModItems.drill;
        DrillUpgrade upgrade = ModItems.drillUpgrade;

        // Primary components
        partsToReturn.add(drill.getBattery(stack));
        partsToReturn.add(drill.getChassis(stack));
        partsToReturn.add(drill.getHead(stack));
        partsToReturn.add(drill.getMotor(stack));

        // Upgrades (non-enchantments)
        if (drill.getTagBoolean(stack, drill.NBT_SAW))
          partsToReturn.add(upgrade.saw);
        if (drill.getTagBoolean(stack, drill.NBT_AREA_MINER))
          partsToReturn.add(upgrade.areaMiner);
        if (drill.getTagBoolean(stack, drill.NBT_GRAVITON_GENERATOR))
          partsToReturn.add(upgrade.gravitonGenerator);
        SuperMultiDrills.logHelper.debug(partsToReturn.size());

        // Upgrades (enchantments)
        int levelEfficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        int levelSilk = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
        int levelFortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        int levelSharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
        SuperMultiDrills.logHelper.debug(levelEfficiency, levelSilk, levelFortune, levelSharpness);
        if (levelEfficiency > 0)
          partsToReturn.add(new ItemStack(upgrade, levelEfficiency, upgrade.speed.getItemDamage()));
        if (levelSilk > 0)
          partsToReturn.add(new ItemStack(upgrade, levelSilk, upgrade.silk.getItemDamage()));
        if (levelFortune > 0)
          partsToReturn.add(new ItemStack(upgrade, levelFortune, upgrade.fortune.getItemDamage()));
        if (levelSharpness > 0)
          partsToReturn.add(new ItemStack(upgrade, levelSharpness, upgrade.sharpness.getItemDamage()));

        // Give all parts to the player.
        for (ItemStack stackToGive : partsToReturn)
          InventoryHelper.addItemToInventoryOrDrop(player, stackToGive);

        break;
      }
    }
  }

  @SubscribeEvent
  public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {

    final Drill drill = ModItems.drill;
    ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
    EntityPlayer player = event.getEntityPlayer();

    if (heldItem != null && heldItem.getItem() instanceof Drill) {
      boolean sneaking = player.isSneaking();
      boolean flying = player.capabilities.isFlying;
      boolean underwater = player.isInWater();
      boolean areaMiner = drill.getTagBoolean(heldItem, Drill.NBT_AREA_MINER);
      boolean gravitonGenerator = drill.getTagBoolean(heldItem, Drill.NBT_GRAVITON_GENERATOR);

      // Restore speed if mining while flying or underwater with graviton generator.
      if (gravitonGenerator && flying) {
        event.setNewSpeed(event.getNewSpeed() * 5);
      }
      if (gravitonGenerator && underwater) {
        event.setNewSpeed(event.getNewSpeed() * 5);
      }

      // Reduce speed of Area Miner
      if (areaMiner && !sneaking) {
        event.setNewSpeed(event.getNewSpeed() * Config.areaMinerSpeedMulti);
      }
    }
  }
}
