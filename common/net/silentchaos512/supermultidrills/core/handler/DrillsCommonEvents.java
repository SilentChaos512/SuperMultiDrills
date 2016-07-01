package net.silentchaos512.supermultidrills.core.handler;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.client.render.ModelDrill;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillBattery;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.DrillHead;
import net.silentchaos512.supermultidrills.item.DrillMotor;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.recipe.RecipeUpgradeDrill;
import net.silentchaos512.supermultidrills.util.InventoryHelper;

public class DrillsCommonEvents {

  @SubscribeEvent
  public void onItemCraftedEvent(ItemCraftedEvent event) {

    if (event.craftMatrix instanceof InventoryCrafting) {
      InventoryCrafting inv = (InventoryCrafting) event.craftMatrix;
      EntityPlayer player = event.player;
      World world = player.worldObj;

      if ((new RecipeUpgradeDrill()).matches(inv, world)) {
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
        
        if (replaceBattery && oldBattery != null) {
          InventoryHelper.addItemToInventoryOrDrop(player, oldBattery);
        }
        if (replaceHead && oldHead != null) {
          InventoryHelper.addItemToInventoryOrDrop(player, oldHead);
        }
        if (replaceMotor && oldMotor != null) {
          InventoryHelper.addItemToInventoryOrDrop(player, oldMotor);
        }
        if (replaceChassis && oldChassis != null) {
          InventoryHelper.addItemToInventoryOrDrop(player, oldChassis);
        }
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
