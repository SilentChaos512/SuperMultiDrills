package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class DrillBattery extends ItemSMD {

  public DrillBattery() {

    this.icons = new IIcon[5];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_BATTERY);
  }

  @Override
  public void addRecipes() {

    // if (SuperMultiDrills.instance.foundEnderIO) {
    // // EnderIO recipes
    // Item itemCapacitor = (Item) Item.itemRegistry.getObject("EnderIO:itemBasicCapacitor");
    // Item itemMaterial = (Item) Item.itemRegistry.getObject("EnderIO:itemMaterial");
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 0), "iri", "xyx", "iri",
    // 'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotX", 'y', Items.potato));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 1), "iri", "xyx", "iri",
    // 'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotConductiveIron", 'y',
    // new ItemStack(itemCapacitor, 1, 0)));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 2), "iri", "xyx", "iri",
    // 'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotElectricalSteel", 'y',
    // new ItemStack(itemCapacitor, 1, 1)));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 3), "iri", "xyx", "iri",
    // 'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotEnergeticAlloy", 'y',
    // new ItemStack(itemCapacitor, 1, 2)));
    // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, 4), "iri", "xyx", "iri",
    // 'i', Items.iron_ingot, 'r', Items.redstone, 'x', "ingotPhasedGold", 'y', new ItemStack(
    // itemMaterial, 1, 8)));
    // }
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

    list.add(EnumChatFormatting.GOLD
        + LocalizationHelper.getOtherItemKey(Names.DRILL_BATTERY, "Capacity")
        + EnumChatFormatting.YELLOW + " " + String.format("%,d", this.getEnergyCapacity(stack))
        + " RF");
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
}
