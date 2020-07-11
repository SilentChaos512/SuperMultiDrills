package net.silentchaos512.supermultidrills.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreTool;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.api.stats.StatInstance;
import net.silentchaos512.gear.client.util.GearClientHelper;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.capability.EnergyStorageItemImpl;
import net.silentchaos512.supermultidrills.lib.Constants;
import net.silentchaos512.supermultidrills.part.BatteryPart;
import net.silentchaos512.supermultidrills.part.ChassisPart;
import net.silentchaos512.supermultidrills.part.MotorPart;
import net.silentchaos512.supermultidrills.util.EnergyUtil;
import net.silentchaos512.supermultidrills.util.TextUtil;
import net.silentchaos512.utils.Color;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DrillItem extends PickaxeItem implements ICoreTool {
    public static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(
            Material.ANVIL,
            Material.CLAY,
            Material.EARTH,
            Material.GLASS,
            Material.ICE,
            Material.IRON,
            Material.MISCELLANEOUS,
            Material.ORGANIC,
            Material.PACKED_ICE,
            Material.PISTON,
            Material.REDSTONE_LIGHT,
            Material.ROCK,
            Material.SAND,
            Material.SNOW,
            Material.SNOW_BLOCK
    );
    public static final Set<Material> EXTRA_MATERIALS = ImmutableSet.of(
            Material.BAMBOO,
            Material.GOURD,
            Material.LEAVES,
            Material.PLANTS,
            Material.TALL_PLANTS,
            Material.WOOD,
            Material.WOOL
    );
    public static final GearType GEAR_TYPE = GearType.getOrCreate("drill", GearType.HARVEST_TOOL);

    public static final ImmutableSet<ToolType> TOOL_TYPES = ImmutableSet.of(ToolType.PICKAXE, ToolType.SHOVEL);
    public static final ImmutableSet<ToolType> TOOL_TYPES_WITH_SAW = ImmutableSet.of(ToolType.PICKAXE, ToolType.SHOVEL, ToolType.AXE);

    /*
     * NBT keys
     */
    public static final String NBT_BATTERY_CAPACITY = "SMD.BatteryCapacity";
    public static final String NBT_CHASSIS_COLOR = "SMD.ChassisColor";

    public DrillItem() {
        super(ItemTier.DIAMOND, 0, 0, GearHelper.getBuilder(ToolType.PICKAXE).addToolType(ToolType.SHOVEL, 3).group(SuperMultiDrills.ITEM_GROUP));
    }

    @Override
    public Collection<PartType> getRequiredParts() {
        return ImmutableList.of(PartType.MAIN, PartType.ROD, MotorPart.TYPE, BatteryPart.TYPE, ChassisPart.TYPE);
    }

    //region Energy and Drills Properties

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityEnergy.ENERGY) {
                    int capacity = getBatteryCapacity(stack);
                    // TODO: Transfer rate?
                    return LazyOptional.of(() -> new EnergyStorageItemImpl(stack, capacity, 100_000, 100_000)).cast();
                }
                return LazyOptional.empty();
            }
        };
    }

    private static IEnergyStorage getBatteryCapability(ItemStack stack) {
        PartData part = GearData.getPartOfType(stack, BatteryPart.TYPE);
        if (part != null) {
            ItemStack battery = part.getCraftingItem();
            SuperMultiDrills.LOGGER.debug("getBatteryCapability: {}", battery);
            LazyOptional<IEnergyStorage> optional = battery.getCapability(CapabilityEnergy.ENERGY);
            return optional.orElse(new EnergyStorageItemImpl(battery, 1_000_000, 1000, 1000));
        }
        return new EnergyStorageItemImpl(stack, 1_000_000, 1000, 1000);
    }

    public static int getBatteryCapacity(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        if (tag.contains(NBT_BATTERY_CAPACITY)) {
            return tag.getInt(NBT_BATTERY_CAPACITY);
        }

        // Battery capacity tag is missing, calculate it from the battery itself
        IEnergyStorage energy = getBatteryCapability(stack);
        int capacity = energy.getMaxEnergyStored();
        tag.putInt(NBT_BATTERY_CAPACITY, capacity);
        return capacity;
    }

    public static void setBatteryCapacity(ItemStack stack, int capacity) {
        stack.getOrCreateTag().putInt(NBT_BATTERY_CAPACITY, capacity);
    }

    private static float getChargeRatio(ItemStack stack) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage energyStorage = optional.orElseThrow(IllegalStateException::new);
            return (float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    public static int getEnergyToBreakBlock(ItemStack stack, BlockState state, float hardness) {
        return Math.round(getBaseEnergyCost(stack) * hardness);
    }

    public static int getBaseEnergyCost(ItemStack stack) {
        int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        int silkTouchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
        int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        float durability = GearData.getStat(stack, ItemStats.DURABILITY);

        // FIXME
//        Expression exp = Constants.DRILL_ENERGY_EXPR;
//        exp.setVariable("durability", BigDecimal.valueOf(durability));
//        exp.setVariable("efficiency", BigDecimal.valueOf(efficiencyLevel));
//        exp.setVariable("silk_touch", BigDecimal.valueOf(silkTouchLevel));
//        exp.setVariable("fortune", BigDecimal.valueOf(fortuneLevel));
//        exp.setVariable("hardness", BigDecimal.valueOf(hardness));
//        exp.setVariable("mining_speed", BigDecimal.valueOf(GearHelper.getDestroySpeed(stack, state, EXTRA_MATERIALS)));
//        exp.setVariable("motor_boost", BigDecimal.valueOf(getMotorSpeedBoost(stack)));

        int result = (int) Math.round(25 + Math.exp(7 - 0.0003 * durability) * (1 + 0.06 * efficiencyLevel)); //exp.eval().intValue();
        if (result < 0) {
            result = 0; // Energy cost should be non-negative!
        }
        return result;
    }

    public static int getChassisColor(ItemStack stack) {
        if (stack.getOrCreateTag().contains(NBT_CHASSIS_COLOR))
            return stack.getOrCreateTag().getInt(NBT_CHASSIS_COLOR);
        return Color.VALUE_WHITE;
    }

    public static void setChassisColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt(NBT_CHASSIS_COLOR, color);
    }

    public static boolean hasSaw(ItemStack stack) {
        return GearData.hasPart(stack, Constants.SAW_UPGRADE);
    }

    //endregion

    //region Gear properties

    @Override
    public GearType getGearType() {
        return GEAR_TYPE;
    }

    @Override
    public Optional<StatInstance> getBaseStatModifier(ItemStat stat) {
        if (stat == ItemStats.MELEE_DAMAGE)
            return Optional.of(StatInstance.makeBaseMod(0));
        if (stat == ItemStats.ATTACK_SPEED)
            return Optional.of(StatInstance.makeBaseMod(-2.0f));
        if (stat == ItemStats.REPAIR_EFFICIENCY)
            return Optional.of(StatInstance.makeBaseMod(1));
        return Optional.empty();
    }

    @Override
    public Optional<StatInstance> getStatModifier(ItemStat stat) {
        if (stat == ItemStats.ENCHANTABILITY)
            return Optional.of(StatInstance.makeGearMod(-0.5f));
        return Optional.empty();
    }

    @Override
    public Set<ItemStat> getRelevantStats(@Nonnull ItemStack stack) {
        return ImmutableSet.of(
                ItemStats.HARVEST_LEVEL,
                ItemStats.HARVEST_SPEED,
                ItemStats.MELEE_DAMAGE,
                ItemStats.ATTACK_SPEED,
                ItemStats.ENCHANTABILITY,
                ItemStats.RARITY
        );
    }

    //endregion

    //region Harvest tool overrides

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        // Forge ItemStack-sensitive version
        int harvestLevel = getStatInt(stack, ItemStats.HARVEST_LEVEL);
        // Wrong harvest level?
        if (state.getBlock().getHarvestLevel(state) > harvestLevel)
            return false;
        // Included in base or extra materials?
        Material material = state.getMaterial();
        if (EFFECTIVE_MATERIALS.contains(material) || (hasSaw(stack) && EXTRA_MATERIALS.contains(material)))
            return true;
        return super.canHarvestBlock(stack, state);
    }

    @Deprecated
    @Override
    public boolean canHarvestBlock(BlockState state) {
        // Vanilla version... Not good because we can't get the actual harvest level.
        Material material = state.getMaterial();
        return EFFECTIVE_MATERIALS.contains(material) || super.canHarvestBlock(state);
    }
    //endregion

    //region Standard tool overrides

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        TextUtil.addEnergyInfo(stack, tooltip);
        tooltip.add(new TranslationTextComponent("item.supermultidrills.drill.energyPerUse", getBaseEnergyCost(stack)));

        GearClientHelper.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return GearHelper.getAttributeModifiers(slot, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage energy = optional.orElseThrow(IllegalStateException::new);
            boolean canHarvest = this.canHarvestBlock(stack, state);
            // Just assume hardness of 1, since we don't have a World object
            boolean hasEnoughPower = energy.getEnergyStored() > getEnergyToBreakBlock(stack, state, 1);

            return canHarvest && hasEnoughPower ? GearHelper.getDestroySpeed(stack, state, EXTRA_MATERIALS) : 1.0f;
        }
        return GearHelper.getDestroySpeed(stack, state, EXTRA_MATERIALS);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return GearHelper.getHarvestLevel(stack, tool, blockState, EXTRA_MATERIALS);
    }

//    @Override
//    public void setHarvestLevel(String toolClass, int level) {
//        super.setHarvestLevel(toolClass, level);
//        GearHelper.setHarvestLevel(this, toolClass, level, this.toolClasses);
//    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return GearHelper.getIsRepairable(toRepair, repair);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return GearData.getStatInt(stack, ItemStats.ENCHANTABILITY);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return GearHelper.getDisplayName(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return GearData.getStatInt(stack, ItemStats.DURABILITY);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return GearHelper.getRarity(stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return GearClientHelper.hasEffect(stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int cost = getEnergyToBreakBlock(stack, Blocks.STONE.getDefaultState(), 1);
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> EnergyUtil.drainEnergy(e, cost));
        return GearHelper.hitEntity(stack, target, attacker);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        GearHelper.fillItemGroup(this, group, items);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        float hardness = state.getBlockHardness(worldIn, pos);
        if (hardness != 0.0f) {
            int cost = getEnergyToBreakBlock(stack, state, hardness);
            SuperMultiDrills.LOGGER.debug("onBlockDestroyed {} (h={})", cost, hardness);
//            if (Config.printMiningCost && entityLiving.world.isRemote) {
//                String str = "%d RF (%.2f hardness)";
//                str = String.format(str, cost, hardness);
//                SuperMultiDrills.logHelper.info(str);
//            }
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> EnergyUtil.drainEnergy(e, cost));
        }

        return GearHelper.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        GearHelper.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return GearClientHelper.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        if (hasSaw(stack))
            return TOOL_TYPES_WITH_SAW;
        return TOOL_TYPES;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        boolean canceled = super.onBlockStartBreak(stack, pos, player);

        if (!canceled) {
            // Number of blocks broken (not used at this time).
            int amount = 1;
            // Try to activate Area Miner
//            if (getTagBoolean(stack, NBT_AREA_MINER)) {
//                amount += DrillAreaMiner.tryActivate(stack, pos, player);
//            }
        }

        return canceled;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - getChargeRatio(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public int getDamage(ItemStack stack) {
        int value = (int) (100 * this.getDurabilityForDisplay(stack));
        return MathHelper.clamp(value, 0, 99);
    }

    //endregion
}
