package net.silentchaos512.supermultidrills.item;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.silentchaos512.gems.api.IBlockPlacer;
import net.silentchaos512.lib.registry.IRegistryObject;
import net.silentchaos512.lib.util.ItemHelper;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.lib.util.WorldHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.lib.ColorHandlers;
import net.silentchaos512.supermultidrills.lib.DrillAreaMiner;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;

public class Drill extends ItemTool implements IRegistryObject, IEnergyContainerItem {

  /*
   * Caching the "spawnable" drills for speed. Probably doesn't make a big difference.
   */
  private static final List<ItemStack> SPAWNABLES = Lists.newArrayList();

  /*
   * Effective materials
   */
  public static final Set effectiveMaterialsBasic = Sets
      .newHashSet(new Material[] { Material.ANVIL, Material.CIRCUITS, Material.CLAY, Material.GLASS,
          Material.GRASS, Material.GROUND, Material.ICE, Material.IRON, Material.PACKED_ICE,
          Material.PISTON, Material.ROCK, Material.SAND, Material.SNOW });
  public static final Set effectiveMaterialsExtra = Sets
      .newHashSet(new Material[] { Material.CLOTH, Material.GOURD, Material.LEAVES, Material.PLANTS,
          Material.VINE, Material.WEB, Material.WOOD });

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

  protected static UUID ATTACK_DAMAGE_MODIFIER;
  protected static UUID ATTACK_SPEED_MODIFIER;

  static {

    ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    try {
      // Damage
      Field field = Item.class.getDeclaredField("ATTACK_DAMAGE_MODIFIER");
      field.setAccessible(true);
      ATTACK_DAMAGE_MODIFIER = (UUID) field.get(null);

      // Speed
      field = Item.class.getDeclaredField("ATTACK_SPEED_MODIFIER");
      field.setAccessible(true);
      ATTACK_SPEED_MODIFIER = (UUID) field.get(null);
    } catch (Exception ex) {
    }
  }

  public Drill() {

    // Some values passed into super should have no effect.
    super(4.0f, -3.0f, ToolMaterial.DIAMOND, effectiveMaterialsBasic);
    this.setMaxStackSize(1);
    this.setMaxDamage(0);
    this.setNoRepair();
    this.setCreativeTab(SuperMultiDrills.creativeTab);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
        || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    LocalizationHelper loc = SuperMultiDrills.localizationHelper;

    if (stack.getTagCompound() != null && !this.hasTag(stack, NBT_HEAD)) {
      // The "empty" drill that shows up in NEI.
      for (String str : SuperMultiDrills.localizationHelper.getItemDescriptionLines(Names.DRILL)) {
        list.add(TextFormatting.AQUA + str);
      }
    } else {
      // Energy stored
      int energy = this.getEnergyStored(stack);
      int energyMax = this.getMaxEnergyStored(stack);
      String amount;
      if (this.getTag(stack, NBT_BATTERY) == DrillBattery.CREATIVE_ID) {
        amount = loc.getMiscText("Infinite");
      } else {
        amount = String.format("%,d / %,d RF", energy, energyMax);
      }
      String str = TextFormatting.YELLOW + loc.getItemSubText(Names.DRILL, "Energy") + " " + amount;
      list.add(str);

      // Special
      str = this.getTagString(stack, NBT_SPECIAL);
      if (str != null) {
        list.add(TextFormatting.DARK_PURPLE + str);
      }

      if (shifted) {
        // Head
        String s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "Head") + " "
            + TextFormatting.AQUA + this.getDrillMaterial(stack).getMaterialName();
        String s2 = " " + TextFormatting.RESET + loc.getItemSubText(Names.DRILL, "HeadCoat");
        s2 = String.format(s2, this.getCoatMaterial(stack).getMaterialName());
        list.add(s + (this.getTag(stack, NBT_HEAD_COAT) >= 0 ? s2 : ""));

        // Operating cost
        s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "MiningCost") + " "
            + TextFormatting.GREEN + this.getEnergyToBreakBlock(stack, 1.0f) + " "
            + loc.getItemSubText(Names.DRILL, "RFPerHardness");
        list.add(s);

        // Harvest level
        s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "MiningLevel") + " "
            + TextFormatting.BLUE + this.getHarvestLevel(stack, "");
        s += this.getTagBoolean(stack, NBT_SAW) ? " " + loc.getItemSubText(Names.DRILL, "PlusSaw")
            : "";
        list.add(s);

        // Mining speed
        s = TextFormatting.GOLD + loc.getItemSubText(Names.DRILL, "MiningSpeed") + " "
            + TextFormatting.DARK_PURPLE + String.format("%.1f", this.getDigSpeed(stack));
        list.add(s);

        // No need to display attack damage here! Vanilla does that.
      } else {
        list.add(
            TextFormatting.GOLD + "" + TextFormatting.ITALIC + loc.getMiscText(Strings.PRESS_CTRL));
      }

      // Graviton generator
      if (getTagBoolean(stack, NBT_GRAVITON_GENERATOR)) {
        str = loc.getItemSubText(Names.DRILL, "GravitonGenerator");
        list.add(TextFormatting.LIGHT_PURPLE + str);
      }

      // Area Miner (since it's not an enchantment)
      if (getTagBoolean(stack, NBT_AREA_MINER)) {
        str = loc.getItemSubText(Names.DRILL, "AreaMiner");
        list.add(str);
      }
    }
  }

  // getSubItems 1.10.2
  public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {

    compatGetSubItems(item, tab, list);
  }

  public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {

    compatGetSubItems(item, tab, list);
  }

  protected void compatGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    ItemStack drill = new ItemStack(item);
    setTag(drill, NBT_CHASSIS, -1);
    list.add(drill);

    if (Config.showSpawnableDrills) {
      if (SPAWNABLES.isEmpty()) {
        // Shiny drill
        drill = new ItemStack(item, 1, 0);
        drill.setStackDisplayName("Shiny Multi-Drill");
        this.setTag(drill, NBT_HEAD, EnumDrillMaterial.PLATINUM.getMeta());
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
        setTag(drill, NBT_HEAD, EnumDrillMaterial.ENDERIUM.getMeta());
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
        this.setTag(drill, NBT_HEAD, EnumDrillMaterial.MANYULLYN.getMeta());
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

    return new ItemStack(ModItems.drillChassis, 1, ~this.getTag(stack, NBT_CHASSIS) & 15);
  }

  @Override
  public boolean canHarvestBlock(IBlockState state, ItemStack drill) {

    Block block = state.getBlock();
    if (block.getHarvestLevel(state) > this.getHarvestLevel(drill, "")) {
      return false;
    }
    boolean isEffective = effectiveMaterialsBasic.contains(state.getMaterial());
    if (!isEffective && this.getTagBoolean(drill, NBT_SAW)) {
      isEffective = effectiveMaterialsExtra.contains(state.getMaterial());
    }
    return isEffective;
  }

  public float getDigSpeed(ItemStack stack) {

    return getDrillMaterial(stack).getEfficiency() * getMotorSpeedBoost(stack);
  }

  @Override
  public float getStrVsBlock(ItemStack stack, IBlockState state) {

    // Is this correct?
    String tool = state.getBlock().getHarvestTool(state);
    boolean canHarvest = tool == null || tool.equals("pickaxe") || tool.equals("shovel")
        || this.getTagBoolean(stack, NBT_SAW) && tool.equals("axe") || this.canHarvestBlock(state);
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

    int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
    int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
    int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

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

  // 1.10.2 (Forge method, so no obfuscation)
  public int getHarvestLevel(ItemStack stack, String toolClass) {

    return getHarvestLevel(stack, toolClass, null, null);
  }

  @Override
  public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState state) {

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

  @Override
  public int getItemEnchantability(ItemStack stack) {

    return 0; // Prevents enchanting thru the enchantment table.
  }

  @Override
  public boolean hasEffect(ItemStack stack) {

    return false; // Prevents the enchanted item glowing effect (broken for multi-layer models)
  }

  @Override
  public boolean hitEntity(ItemStack stack, EntityLivingBase entity1, EntityLivingBase entity2) {

    this.extractEnergy(stack, this.getEnergyToBreakBlock(stack, 1.0f), false);
    return true;
  }

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
      EntityLivingBase player) {

    float hardness = state.getBlockHardness(world, pos);
    if (hardness != 0.0f) {
      int cost = getEnergyToBreakBlock(stack, hardness);
      if (Config.printMiningCost && player.world.isRemote) {
        String str = "%d RF (%.2f hardness)";
        str = String.format(str, cost, hardness);
        SuperMultiDrills.logHelper.info(str);
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
  public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot,
      ItemStack stack) {

    // TODO: Is this right?
    Multimap<String, AttributeModifier> map = HashMultimap.<String, AttributeModifier> create();

    if (slot == EntityEquipmentSlot.MAINHAND) {
      double damage = this.getDrillMaterial(stack).getDamageVsEntity() + 4.0;
      map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
          new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damage, 0));
      map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
          new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3.0f, 0));
    }
    return map;
  }

  public int getMaxEnergyExtracted(ItemStack container) {

    return Integer.MAX_VALUE;
  }

  public int getMaxEnergyReceived(ItemStack container) {

    return this.getMaxEnergyStored(container) / 200;
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
    return MathHelper.clamp(value, 0, 99);
  }

  @Override
  public void setDamage(ItemStack stack, int damage) {

  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return "item." + SuperMultiDrills.RESOURCE_PREFIX + Names.DRILL;
  }

  public int getColorFromItemStack(ItemStack stack, int pass) {

    if (pass == ColorHandlers.TINT_INDEX_CHASSIS) {
      int color = this.getTag(stack, NBT_CHASSIS);
      color = color < 0 ? 15 : color;
      return ItemDye.DYE_COLORS[color & 15];
    } else if (pass == ColorHandlers.TINT_INDEX_HEAD) {
      EnumDrillMaterial material = getDrillMaterial(stack);
      return material.getTint();
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

  // 1.10.2 onItemUse
  public EnumActionResult func_180614_a(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
      EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

    return onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
      EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

    // If block is in offhand, allow that to place instead.
    ItemStack stackOffHand = player.getHeldItemOffhand();
    if (StackHelper.isValid(stackOffHand) && stackOffHand.getItem() instanceof ItemBlock) {
      ItemBlock itemBlock = (ItemBlock) stackOffHand.getItem();
      BlockPos target = pos;

      if (!itemBlock.getBlock().isReplaceable(world, pos))
        target = pos.offset(side);

      if (player.canPlayerEdit(target, side, stackOffHand) && WorldHelper.mayPlace(world,
          itemBlock.block, target, false, side, player, stackOffHand))
        return EnumActionResult.PASS;
    }

    EnumActionResult result = EnumActionResult.PASS;
    int toolSlot = player.inventory.currentItem;
    int itemSlot = toolSlot + 1;
    ItemStack nextStack = StackHelper.empty();
    ItemStack lastStack = player.inventory.getStackInSlot(8); // Slot 9 in hotbar

    if (toolSlot < 8) {
      // Get stack in slot after tool.
      nextStack = player.inventory.getStackInSlot(itemSlot);

      // If there's nothing there we can use, try slot 9 instead.
      if (StackHelper.isEmpty(nextStack) || (!(nextStack.getItem() instanceof ItemBlock)
          && !(nextStack.getItem() instanceof IBlockPlacer))) {
        nextStack = lastStack;
        itemSlot = 8;
      }

      if (StackHelper.isValid(nextStack)) {
        Item item = nextStack.getItem();
        if (item instanceof ItemBlock || item instanceof IBlockPlacer) {
          BlockPos targetPos = pos.offset(side);
          int playerX = (int) Math.floor(player.posX);
          int playerY = (int) Math.floor(player.posY);
          int playerZ = (int) Math.floor(player.posZ);

          // Check for block overlap with player, if necessary.
          if (item instanceof ItemBlock) {
            int px = targetPos.getX();
            int py = targetPos.getY();
            int pz = targetPos.getZ();
            AxisAlignedBB blockBounds = new AxisAlignedBB(px, py, pz, px + 1, py + 1, pz + 1);
            AxisAlignedBB playerBounds = player.getEntityBoundingBox();
            ItemBlock itemBlock = (ItemBlock) item;
            Block block = itemBlock.getBlock();
            IBlockState state = block.getStateFromMeta(itemBlock.getMetadata(nextStack));
            if (state.getMaterial().blocksMovement() && playerBounds.intersectsWith(blockBounds)) {
              return EnumActionResult.FAIL;
            }
          }

          int prevSize = StackHelper.getCount(nextStack);
          result = ItemHelper.useItemAsPlayer(nextStack, player, world, pos, side, hitX, hitY, hitZ);

          // Don't consume in creative mode?
          if (player.capabilities.isCreativeMode) {
            StackHelper.setCount(nextStack, prevSize);
          }

          // Remove empty stacks.
          if (StackHelper.isEmpty(nextStack)) {
            nextStack = StackHelper.empty();
            player.inventory.setInventorySlotContents(itemSlot, StackHelper.empty());
          }
        }
      }
    }

    return result;
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    return Lists.newArrayList(new ModelResourceLocation(getFullName().toLowerCase(), "inventory"));
  }

  @Override
  public String getName() {

    return Names.DRILL;
  }

  @Override
  public String getModId() {

    return SuperMultiDrills.MOD_ID;
  }

  @Override
  public String getFullName() {

    return getModId() + ":" + getName();
  }

  @Override
  public boolean registerModels() {

    // TODO Auto-generated method stub
    return false;
  }
}
