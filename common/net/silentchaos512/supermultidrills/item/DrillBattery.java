package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.registry.GameRegistry;

public class DrillBattery extends ItemSMD implements IEnergyContainerItem {

  public static final String NBT_BASE = "Battery";
  public static final String NBT_ENERGY = "Energy";

  public DrillBattery() {

    this.icons = new IIcon[5];
    this.setMaxDamage(0);
    this.setMaxStackSize(1);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_BATTERY);
  }

  @Override
  public void addRecipes() {

    if (SuperMultiDrills.instance.foundEnderIO) {
      // EnderIO recipes
      Item itemCapacitor = (Item) Item.itemRegistry.getObject("EnderIO:itemBasicCapacitor");
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), "iri", "iyi", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'y', Items.potato));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), "iri", "xrx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotConductiveIron"));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotElectricalSteel", 'y',
          new ItemStack(itemCapacitor, 1, 0)));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 3), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotEnergeticAlloy", 'y',
          new ItemStack(itemCapacitor, 1, 1)));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 4), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotPhasedGold", 'y', new ItemStack(
              itemCapacitor, 1, 2)));
    }
    if (SuperMultiDrills.instance.foundThermalFoundation) {
      // Thermal Foundation recipes
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotCopper", 'y', Items.potato));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotLead", 'y', "dustSulfur"));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotInvar", 'y', new ItemStack(this,
              1, 1)));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 3), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotElectrum", 'y', new ItemStack(
              this, 1, 2)));
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 4), "iri", "xyx", "iri",
          'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotEnderium", 'y', new ItemStack(
              this, 1, 3)));
    }
    if (!SuperMultiDrills.instance.foundEnderIO
        && !SuperMultiDrills.instance.foundThermalFoundation) {
      // Vanilla
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 0), " x ", "xyx", "zxz", 'x',
          Blocks.dirt, 'y', Items.potato, 'z', Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 1), " x ", "xyx", "zxz", 'x',
          Items.iron_ingot, 'y', Items.gold_ingot, 'z', Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 2), " x ", "xyx", "zxz", 'x',
          Items.gold_nugget, 'y', Items.glowstone_dust, 'z', Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 3), " x ", "xyx", "zxz", 'x',
          Items.gold_ingot, 'y', Items.ender_pearl, 'z', Items.redstone);
      GameRegistry.addShapedRecipe(new ItemStack(this, 1, 4), " x ", "xyx", "zxz", 'x',
          Items.diamond, 'y', Items.nether_star, 'z', Items.redstone);
    }
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    // Energy stored
    int energy = this.getEnergyStored(stack);
    int energyMax = this.getMaxEnergyStored(stack);
    String str = EnumChatFormatting.YELLOW
        + LocalizationHelper.getOtherItemKey(Names.DRILL, "Energy") + " "
        + String.format("%,d / %,d RF", energy, energyMax);
    list.add(str);
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {

    return true;
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {

    int energy = this.getEnergyStored(stack);
    int energyMax = this.getMaxEnergyStored(stack);
    return (double) (energyMax - energy) / (double) energyMax;
  }

  public int getEnergyCapacity(ItemStack stack) {

    switch (stack.getItemDamage()) {
      case 4:
        return Config.battery4MaxCharge;
      case 3:
        return Config.battery3MaxCharge;
      case 2:
        return Config.battery2MaxCharge;
      case 1:
        return Config.battery1MaxCharge;
      default:
        return Config.battery0MaxCharge;
    }
  }

  public int getMaxEnergyExtracted(ItemStack container) {

    return Integer.MAX_VALUE;
  }

  public int getMaxEnergyReceived(ItemStack container) {

    return this.getMaxEnergyStored(container) / 100;
  }

  @Override
  public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

    int energy = getEnergyStored(container);
    int energyReceived = Math.min(getMaxEnergyStored(container) - energy,
        Math.min(this.getMaxEnergyReceived(container), maxReceive));

    if (!simulate) {
      energy += energyReceived;
      this.setTag(container, NBT_ENERGY, energy);
    }
    return energyReceived;
  }

  @Override
  public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

    if (container.stackTagCompound == null || !this.hasTag(container, NBT_ENERGY)) {
      return 0;
    }
    int energy = getEnergyStored(container);
    int energyExtracted = Math.min(energy,
        Math.min(this.getMaxEnergyExtracted(container), maxExtract));

    if (!simulate) {
      energy -= energyExtracted;
      this.setTag(container, NBT_ENERGY, energy);
    }
    return energyExtracted;
  }

  @Override
  public int getEnergyStored(ItemStack container) {

    return this.getTag(container, NBT_ENERGY);
  }

  @Override
  public int getMaxEnergyStored(ItemStack container) {

    switch (container.getItemDamage()) {
      case 4:
        return Config.battery4MaxCharge;
      case 3:
        return Config.battery3MaxCharge;
      case 2:
        return Config.battery2MaxCharge;
      case 1:
        return Config.battery1MaxCharge;
      default:
        return Config.battery0MaxCharge;
    }
  }

  public void createTagCompoundIfNeeded(ItemStack stack) {

    if (stack.stackTagCompound == null) {
      stack.setTagCompound(new NBTTagCompound());
    }
    if (!stack.stackTagCompound.hasKey(NBT_BASE)) {
      stack.stackTagCompound.setTag(NBT_BASE, new NBTTagCompound());
    }
  }

  public boolean hasTag(ItemStack stack, String key) {

    if (stack.stackTagCompound == null || !stack.stackTagCompound.hasKey(NBT_BASE)) {
      return false;
    }
    return ((NBTTagCompound) stack.stackTagCompound.getTag(NBT_BASE)).hasKey(key);
  }

  public int getTag(ItemStack stack, String key) {

    if (stack == null) {
      return -1;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.stackTagCompound.getTag(NBT_BASE);
    if (tags.hasKey(key)) {
      return tags.getInteger(key);
    } else {
      return 0;
    }
  }

  public void setTag(ItemStack stack, String key, int value) {

    if (stack == null) {
      return;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.stackTagCompound.getTag(NBT_BASE);
    tags.setInteger(key, value);
  }
}
