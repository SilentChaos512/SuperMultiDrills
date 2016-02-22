package net.silentchaos512.supermultidrills.item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.udojava.evalex.Expression;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.silentchaos512.gems.api.IPlaceable;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.client.render.SmartModelDrill;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.DrillAreaMiner;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.registry.IAddRecipe;
import net.silentchaos512.supermultidrills.registry.IHasVariants;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class Drill extends ItemTool implements IAddRecipe, IEnergyContainerItem, IHasVariants {

  /*
   * Caching the "spawnable" drills for speed. Probably doesn't make a big difference.
   */
  private static final List<ItemStack> SPAWNABLES = Lists.newArrayList();

  /*
   * Effective materials
   */
  public static final Set effectiveMaterialsBasic = Sets
      .newHashSet(new Material[] { Material.anvil, Material.circuits, Material.clay, Material.glass,
          Material.grass, Material.ground, Material.ice, Material.iron, Material.packedIce,
          Material.piston, Material.rock, Material.sand, Material.snow });
  public static final Set effectiveMaterialsExtra = Sets
      .newHashSet(new Material[] { Material.cloth, Material.gourd, Material.leaves, Material.plants,
          Material.vine, Material.web, Material.wood });

  /*
   * NBT keys
   */
  public static final String NBT_BASE = "Drill";
  public static final String NBT_HEAD = "Head";
  public static final String NBT_MOTOR = "Motor";
  public static final String NBT_BATTERY = "Battery";
  public static final String NBT_CHASSIS = "Chassis";
  public static final String NBT_ENERGY = "Energy";
  public static final String NBT_SAW = "Saw";
  public static final String NBT_HEAD_COAT = "HeadCoat";
  public static final String NBT_SPECIAL = "Special";
  public static final String NBT_AREA_MINER = "AreaMiner";
  public static final String NBT_GRAVITON_GENERATOR = "GravitonGenerator";

  /*
   * Battery gauge icons
   */
  // public final IIcon[] iconBatteryGauge = new IIcon[4];

  public Drill() {

    // The values passed into super should have no effect.
    super(4.0f, ToolMaterial.EMERALD, effectiveMaterialsBasic);
    this.setMaxStackSize(1);
    this.setMaxDamage(0);
    this.setNoRepair();
    this.setCreativeTab(SuperMultiDrills.creativeTab);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
        || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

    if (stack.getTagCompound() != null && !this.hasTag(stack, NBT_HEAD)) {
      // The "empty" drill that shows up in NEI.
      int i = 1;
      String itemName = Names.DRILL;

      // Crafting description
      String s = LocalizationHelper.getOtherItemKey(itemName, "craft" + i);
      while (!s.equals(LocalizationHelper.getOtherItemKey(itemName, "craft" + i))) {
        list.add(EnumChatFormatting.YELLOW + s);
        s = LocalizationHelper.getOtherItemKey(itemName, "craft" + ++i);
      }
      i = 1;

      // Other description
      s = LocalizationHelper.getItemDescription(itemName, i);
      while (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, i)) && i < 16) {
        list.add(EnumChatFormatting.DARK_AQUA + s);
        s = LocalizationHelper.getItemDescription(itemName, ++i);
      }

      if (i == 1) {
        s = LocalizationHelper.getItemDescription(itemName, 0);
        if (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, 0))) {
          list.add(
              EnumChatFormatting.DARK_AQUA + LocalizationHelper.getItemDescription(itemName, 0));
        }
      }
    } else {
      // Energy stored
      int energy = this.getEnergyStored(stack);
      int energyMax = this.getMaxEnergyStored(stack);
      String amount;
      if (this.getTag(stack, NBT_BATTERY) == DrillBattery.CREATIVE_ID) {
        amount = LocalizationHelper.getMiscText("Infinite");
      } else {
        amount = String.format("%,d / %,d RF", energy, energyMax);
      }
      String str = EnumChatFormatting.YELLOW
          + LocalizationHelper.getOtherItemKey(Names.DRILL, "Energy") + " " + amount;
      list.add(str);

      // Special
      str = this.getTagString(stack, NBT_SPECIAL);
      if (str != null) {
        list.add(EnumChatFormatting.DARK_PURPLE + str);
      }

      if (shifted) {
        // Head
        String s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "Head")
            + " " + EnumChatFormatting.AQUA + this.getDrillMaterial(stack).getMaterialName();
        String s2 = " " + EnumChatFormatting.RESET
            + LocalizationHelper.getOtherItemKey(Names.DRILL, "HeadCoat");
        s2 = String.format(s2, this.getCoatMaterial(stack).getMaterialName());
        list.add(s + (this.getTag(stack, NBT_HEAD_COAT) >= 0 ? s2 : ""));

        // Operating cost
        s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningCost")
            + " " + EnumChatFormatting.GREEN + this.getEnergyToBreakBlock(stack, 1.0f) + " "
            + LocalizationHelper.getOtherItemKey(Names.DRILL, "RFPerHardness");
        list.add(s);

        // Harvest level
        s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningLevel")
            + " " + EnumChatFormatting.BLUE + this.getHarvestLevel(stack, "");
        s += this.getTagBoolean(stack, NBT_SAW)
            ? " " + LocalizationHelper.getOtherItemKey(Names.DRILL, "PlusSaw") : "";
        list.add(s);

        // Mining speed
        s = EnumChatFormatting.GOLD + LocalizationHelper.getOtherItemKey(Names.DRILL, "MiningSpeed")
            + " " + EnumChatFormatting.DARK_PURPLE + String.format("%.1f", this.getDigSpeed(stack));
        list.add(s);

        // No need to display attack damage here! Vanilla does that.
      } else {
        list.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC
            + LocalizationHelper.getMiscText(Strings.PRESS_CTRL));
      }

      // Graviton generator
      if (getTagBoolean(stack, NBT_GRAVITON_GENERATOR)) {
        str = LocalizationHelper.getOtherItemKey(Names.DRILL, "GravitonGenerator");
        list.add(EnumChatFormatting.LIGHT_PURPLE + str);
      }

      // Area Miner (since it's not an enchantment)
      if (getTagBoolean(stack, NBT_AREA_MINER)) {
        str = LocalizationHelper.getOtherItemKey(Names.DRILL, "AreaMiner");
        list.add(str);
      }
    }
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    ItemStack drill = new ItemStack(item);
    setTag(drill, NBT_CHASSIS, -1);
    list.add(drill);

    if (Config.showSpawnableDrills) {
      if (SPAWNABLES.isEmpty()) {
        // Shiny drill
        drill = new ItemStack(item, 1, 0);
        drill.setStackDisplayName("Shiny Multi-Drill");
        this.setTag(drill, NBT_HEAD, 11);
        this.setTag(drill, NBT_HEAD_COAT, -1);
        this.setTag(drill, NBT_MOTOR, 1);
        this.setTag(drill, NBT_BATTERY, 3);
        this.setTag(drill, NBT_CHASSIS, 6);
        this.setTag(drill, NBT_ENERGY, this.getMaxEnergyStored(drill));
        this.setTagBoolean(drill, NBT_SAW, false);
        this.setTagString(drill, NBT_SPECIAL, "For testing purposes and cheaters.");
        SPAWNABLES.add(drill);

        // Ender drill
        drill = new ItemStack(item, 1, 0);
        drill.setStackDisplayName("Ender Drill");
        setTag(drill, NBT_HEAD, 32);
        setTag(drill, NBT_HEAD_COAT, -1);
        setTag(drill, NBT_MOTOR, 2);
        setTag(drill, NBT_BATTERY, 4);
        setTag(drill, NBT_CHASSIS, 8);
        setTag(drill, NBT_ENERGY, getMaxEnergyStored(drill));
        setTagBoolean(drill, NBT_SAW, true);
        setTagBoolean(drill, NBT_GRAVITON_GENERATOR, true);
        setTagString(drill, NBT_SPECIAL, "Because I got tired of spawning in the parts.");
        SPAWNABLES.add(drill);

        // Mani Mani
        drill = new ItemStack(item, 1, 0);
        drill.setStackDisplayName("Mani Mani Drill");
        this.setTag(drill, NBT_HEAD, 28);
        this.setTag(drill, NBT_HEAD_COAT, -1);
        this.setTag(drill, NBT_MOTOR, 2);
        this.setTag(drill, NBT_BATTERY, 5);
        this.setTag(drill, NBT_CHASSIS, 5);
        this.setTag(drill, NBT_ENERGY, this.getMaxEnergyStored(drill));
        this.setTagBoolean(drill, NBT_SAW, true);
        this.setTagString(drill, NBT_SPECIAL, "+5 coolness for getting the reference.");
        SPAWNABLES.add(drill);
      }

      list.addAll(SPAWNABLES);
    }
  }

  @Override
  public void addRecipes() {

  }

  @Override
  public void addOreDict() {

  }

  public EnumDrillMaterial getDrillMaterial(ItemStack stack) {

    int headId = this.getTag(stack, NBT_HEAD);
    if (headId < 0 || headId >= EnumDrillMaterial.values().length) {
      headId = 0;
    }
    return EnumDrillMaterial.values()[headId];
  }

  public EnumDrillMaterial getCoatMaterial(ItemStack stack) {

    int coatId = this.getTag(stack, NBT_HEAD_COAT);
    if (coatId < 0 || coatId >= EnumDrillMaterial.values().length) {
      coatId = 0;
    }
    return EnumDrillMaterial.values()[coatId];
  }

  public ItemStack getBattery(ItemStack stack) {

    ItemStack battery = new ItemStack(ModItems.drillBattery, 1, this.getTag(stack, NBT_BATTERY));
    ModItems.drillBattery.setTag(battery, ModItems.drillBattery.NBT_ENERGY,
        this.getEnergyStored(stack));
    return battery;
  }

  public ItemStack getHead(ItemStack stack) {

    return new ItemStack(ModItems.drillHead, 1, this.getTag(stack, NBT_HEAD));
  }

  public ItemStack getMotor(ItemStack stack) {

    return new ItemStack(ModItems.drillMotor, 1, this.getTag(stack, NBT_MOTOR));
  }

  public ItemStack getChassis(ItemStack stack) {

    return new ItemStack(ModItems.drillChassis, 1, this.getTag(stack, NBT_CHASSIS));
  }

  public boolean canHarvestBlock(ItemStack drill, IBlockState state, int meta) {

    Block block = state.getBlock();
    if (block.getHarvestLevel(state) > this.getHarvestLevel(drill, "")) {
      return false;
    }
    boolean isEffective = effectiveMaterialsBasic.contains(block.getMaterial());
    if (!isEffective && this.getTagBoolean(drill, NBT_SAW)) {
      isEffective = effectiveMaterialsExtra.contains(block.getMaterial());
    }
    return isEffective;
  }

  public float getDigSpeed(ItemStack stack) {

    return getDrillMaterial(stack).getEfficiency() * getMotorSpeedBoost(stack);
  }

  @Override
  public float getDigSpeed(ItemStack stack, IBlockState state) {

    Block block = state.getBlock();
    // Is this correct?
    String tool = state.getBlock().getHarvestTool(state);
    boolean canHarvest = tool == null || tool.equals("pickaxe") || tool.equals("shovel")
        || this.getTagBoolean(stack, NBT_SAW) && tool.equals("axe")
        || this.canHarvestBlock(block, stack);
    // I wasn't sure how to get block hardness here, as it requires a world object. There's probably
    // an easy way to do it, but it shouldn't matter in most cases, so I just used 1.
    boolean hasEnoughPower = this.getEnergyStored(stack) > 0
        || this.getEnergyToBreakBlock(stack, 1.0f) == 0;

    if (canHarvest && hasEnoughPower) {
      return this.getDrillMaterial(stack).getEfficiency() * getMotorSpeedBoost(stack);
    } else {
      return 1.0f;
    }
  }

  public float getMotorSpeedBoost(ItemStack stack) {

    switch (getTag(stack, NBT_MOTOR)) {
      case 5:
        return DrillMotor.CREATIVE_MOTOR_BOOST;
      case 4:
        return Config.motor4Boost;
      case 3:
        return Config.motor3Boost;
      case 2:
        return Config.motor2Boost;
      case 1:
        return Config.motor1Boost;
      case 0:
        return Config.motor0Boost;
      default:
        return 1.0f;
    }
  }

  public int getEnergyToBreakBlock(ItemStack stack, float hardness) {

    int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId,
        stack);
    int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId,
        stack);
    int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);

    EnumDrillMaterial material = this.getDrillMaterial(stack);

    Expression exp = Config.energyCostExpression;
    exp.setVariable("durability", BigDecimal.valueOf(material.getDurability()));
    exp.setVariable("efficiency", BigDecimal.valueOf(efficiencyLevel));
    exp.setVariable("silk_touch", BigDecimal.valueOf(silkTouchLevel));
    exp.setVariable("fortune", BigDecimal.valueOf(fortuneLevel));
    exp.setVariable("hardness", BigDecimal.valueOf(hardness));
    exp.setVariable("mining_speed", BigDecimal.valueOf(material.getEfficiency()));
    exp.setVariable("motor_boost", BigDecimal.valueOf(getMotorSpeedBoost(stack)));

    int result = exp.eval().intValue();
    if (result < 0) {
      result = 0; // Energy cost should be non-negative!
    }
    return result;
  }

  public void createTagCompoundIfNeeded(ItemStack stack) {

    if (stack.getTagCompound() == null) {
      stack.setTagCompound(new NBTTagCompound());
    }
    if (!stack.getTagCompound().hasKey(NBT_BASE)) {
      stack.getTagCompound().setTag(NBT_BASE, new NBTTagCompound());
    }
  }

  public boolean hasTag(ItemStack stack, String key) {

    if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey(NBT_BASE)) {
      return false;
    }
    return ((NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE)).hasKey(key);
  }

  public int getTag(ItemStack stack, String key) {

    if (stack == null) {
      return -1;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    if (tags.hasKey(key)) {
      return tags.getInteger(key);
    } else {
      return 0;
    }
  }

  public boolean getTagBoolean(ItemStack stack, String key) {

    if (stack == null) {
      return false;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    if (tags.hasKey(key)) {
      return tags.getBoolean(key);
    } else {
      return false;
    }
  }

  public String getTagString(ItemStack stack, String key) {

    if (stack == null) {
      return null;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    if (tags.hasKey(key)) {
      return tags.getString(key);
    } else {
      return null;
    }
  }

  public void setTag(ItemStack stack, String key, int value) {

    if (stack == null) {
      return;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    tags.setInteger(key, value);
  }

  public void setTagBoolean(ItemStack stack, String key, boolean value) {

    if (stack == null) {
      return;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    tags.setBoolean(key, value);
  }

  public void setTagString(ItemStack stack, String key, String value) {

    if (stack == null) {
      return;
    }
    this.createTagCompoundIfNeeded(stack);

    NBTTagCompound tags = (NBTTagCompound) stack.getTagCompound().getTag(NBT_BASE);
    tags.setString(key, value);
  }

  @Override
  public int getHarvestLevel(ItemStack stack, String toolClass) {

    int motorLevel = this.getTag(stack, NBT_MOTOR);
    switch (motorLevel) {
      case 5:
        return DrillMotor.CREATIVE_MOTOR_LEVEL;
      case 4:
        return Config.motor4Level;
      case 3:
        return Config.motor3Level;
      case 2:
        return Config.motor2Level;
      case 1:
        return Config.motor1Level;
      default:
        return Config.motor0Level;
    }
  }

  @Override
  public Set getToolClasses(ItemStack stack) {

    boolean hasSaw = this.getTagBoolean(stack, NBT_SAW);
    if (hasSaw) {
      return ImmutableSet.of("pickaxe", "shovel", "axe");
    } else {
      return ImmutableSet.of("pickaxe", "shovel");
    }
  }

  // Can harvest block? Direct copy from ItemPickaxe.
  public boolean func_150897_b(Block block) {

    return block == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3
        : (block != Blocks.diamond_block
            && block != Blocks.diamond_ore
                ? (block != Blocks.emerald_ore
                    && block != Blocks.emerald_block
                        ? (block != Blocks.gold_block
                            && block != Blocks.gold_ore
                                ? (block != Blocks.iron_block
                                    && block != Blocks.iron_ore
                                        ? (block != Blocks.lapis_block && block != Blocks.lapis_ore
                                            ? (block != Blocks.redstone_ore
                                                && block != Blocks.lit_redstone_ore
                                                    ? (block.getMaterial() == Material.rock ? true
                                                        : (block.getMaterial() == Material.iron
                                                            ? true
                                                            : block
                                                                .getMaterial() == Material.anvil))
                                                    : this.toolMaterial.getHarvestLevel() >= 2)
                                            : this.toolMaterial.getHarvestLevel() >= 1)
                                        : this.toolMaterial.getHarvestLevel() >= 1)
                                : this.toolMaterial.getHarvestLevel() >= 2)
                        : this.toolMaterial.getHarvestLevel() >= 2)
                : this.toolMaterial.getHarvestLevel() >= 2);
  }

  @Override
  public int getItemEnchantability(ItemStack stack) {

    return 0; // Prevents enchanting thru the enchantment table.
  }

  @Override
  public boolean hasEffect(ItemStack stack) {

    return false; // Prevents the enchanted item glowing effect
  }

  @Override
  public boolean hitEntity(ItemStack stack, EntityLivingBase entity1, EntityLivingBase entity2) {

    this.extractEnergy(stack, this.getEnergyToBreakBlock(stack, 1.0f), false);
    return true;
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos,
      EntityLivingBase player) {

    float hardness = block.getBlockHardness(world, pos);
    if (hardness != 0.0f) {
      int cost = getEnergyToBreakBlock(stack, hardness);
      if (Config.printMiningCost && player.worldObj.isRemote) {
        String str = "%d RF (%.2f hardness)";
        str = String.format(str, cost, hardness);
        LogHelper.info(str);
      }
      extractEnergy(stack, cost, false);
    }

    return true;
  }

  @Override
  public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {

    boolean canceled = super.onBlockStartBreak(stack, pos, player);

    if (!canceled) {
      // Number of blocks broken (not used at this time).
      int amount = 1;
      // Try to activate Area Miner
      if (getTagBoolean(stack, NBT_AREA_MINER)) {
        amount += DrillAreaMiner.tryActivate(stack, pos, player);
      }
    }

    return canceled;
  }

  @Override
  public boolean isFull3D() {

    return true;
  }

  @Override
  public Multimap getAttributeModifiers(ItemStack stack) {

    Multimap multimap = HashMultimap.create();
    double damage = this.getDrillMaterial(stack).getDamageVsEntity() + 4.0;
    multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
        new AttributeModifier("Tool modifier", damage, 0));
    return multimap;
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

    if (container.getTagCompound() == null || !this.hasTag(container, NBT_ENERGY)
        || this.getTag(container, NBT_BATTERY) == DrillBattery.CREATIVE_ID) {
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

    int battery = this.getTag(container, NBT_BATTERY);
    return ModItems.drillBattery
        .getMaxEnergyStored(new ItemStack(ModItems.drillBattery, 1, battery));
  }

  @Override
  public int getDamage(ItemStack stack) {

    int value = (int) (100 * this.getDurabilityForDisplay(stack));
    return MathHelper.clamp_int(value, 0, 99);
  }

  @Override
  public void setDamage(ItemStack stack, int damage) {

  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return LocalizationHelper.ITEM_PREFIX + Names.DRILL;
  }

  @Override
  public int getColorFromItemStack(ItemStack stack, int pass) {

    if (pass == SmartModelDrill.PASS_CHASSIS) {
      int color = this.getTag(stack, NBT_CHASSIS);
      color = color < 0 ? 15 : color;
      return ItemDye.dyeColors[color & 15];
    } else {
      return 0xFFFFFF;
    }
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {

    return stack.getTagCompound() != null && this.hasTag(stack, NBT_ENERGY)
        && this.getEnergyStored(stack) != this.getMaxEnergyStored(stack);
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {

    int energy = this.getEnergyStored(stack);
    int energyMax = this.getMaxEnergyStored(stack);
    return (double) (energyMax - energy) / (double) energyMax;
  }

  @Override
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
      EnumFacing side, float hitX, float hitY, float hitZ) {

    boolean used = false;
    int toolSlot = player.inventory.currentItem;
    int itemSlot = toolSlot + 1;
    ItemStack nextStack = null;
    ItemStack lastStack = player.inventory.getStackInSlot(8); // Slot 9 in hotbar

    if (toolSlot < 8) {
      // Get stack in slot after tool.
      nextStack = player.inventory.getStackInSlot(itemSlot);

      // If there's nothing there we can use, try slot 9 instead.
      if (nextStack == null || (!(nextStack.getItem() instanceof ItemBlock)
          && !(nextStack.getItem() instanceof IPlaceable))) {
        nextStack = lastStack;
        itemSlot = 8;
      }

      if (nextStack != null) {
        Item item = nextStack.getItem();
        if (item instanceof ItemBlock || item instanceof IPlaceable) {
          BlockPos targetPos = pos.offset(side);
          int playerX = (int) Math.floor(player.posX);
          int playerY = (int) Math.floor(player.posY);
          int playerZ = (int) Math.floor(player.posZ);

          // Check for block overlap with player, if necessary.
          if (item instanceof ItemBlock) {
            int px = targetPos.getX();
            int py = targetPos.getY();
            int pz = targetPos.getZ();
            AxisAlignedBB blockBounds = AxisAlignedBB.fromBounds(px, py, pz, px + 1, py + 1,
                pz + 1);
            AxisAlignedBB playerBounds = player.getEntityBoundingBox();
            Block block = ((ItemBlock) item).getBlock();
            if (block.getMaterial().blocksMovement() && playerBounds.intersectsWith(blockBounds)) {
              return false;
            }
          }

          used = item.onItemUse(nextStack, player, world, pos, side, hitX, hitY, hitZ);
          if (nextStack.stackSize < 1) {
            nextStack = null;
            player.inventory.setInventorySlotContents(itemSlot, null);
          }
        }
      }
    }

    return used;
  }

  @Override
  public String[] getVariantNames() {

    return new String[] { getFullName() };
  }

  @Override
  public String getName() {

    return Names.DRILL;
  }

  @Override
  public String getFullName() {

    return SuperMultiDrills.MOD_ID + ":" + getName();
  }
}
