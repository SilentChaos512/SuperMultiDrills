package net.silentchaos512.supermultidrills.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreTool;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.client.util.GearClientHelper;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.gear.item.gear.GearDiggerItem;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.capability.EnergyStorageItemImpl;
import net.silentchaos512.supermultidrills.lib.SmdConst;
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
import java.util.Set;

public class DrillItem extends GearDiggerItem {
    public static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(
            Material.HEAVY_METAL,
            Material.CLAY,
            Material.DIRT,
            Material.GLASS,
            Material.ICE,
            Material.METAL,
            Material.DECORATION,
            Material.GRASS,
            Material.ICE_SOLID,
            Material.PISTON,
            Material.BUILDABLE_GLASS,
            Material.STONE,
            Material.SAND,
            Material.TOP_SNOW,
            Material.SNOW
    );
    public static final Set<Material> EXTRA_MATERIALS = ImmutableSet.of(
            Material.BAMBOO,
            Material.VEGETABLE,
            Material.LEAVES,
            Material.PLANT,
            Material.REPLACEABLE_PLANT,
            Material.WOOD,
            Material.WOOL
    );
    public static final GearType GEAR_TYPE = GearType.getOrCreate("drill", GearType.HARVEST_TOOL, b ->
            b.toolActions(ToolActions.PICKAXE_DIG, ToolActions.SHOVEL_DIG));

    /*
     * NBT keys
     */
    public static final String NBT_BATTERY_CAPACITY = "SMD.BatteryCapacity";
    public static final String NBT_CHASSIS_COLOR = "SMD.ChassisColor";

    public DrillItem() {
        super(GEAR_TYPE, BlockTags.MINEABLE_WITH_PICKAXE, EFFECTIVE_MATERIALS, new Item.Properties().tab(SuperMultiDrills.ITEM_GROUP));
    }

    @Override
    public Collection<PartType> getRequiredParts() {
        return ImmutableList.of(PartType.MAIN, PartType.ROD, MotorPart.TYPE, BatteryPart.TYPE, ChassisPart.TYPE);
    }

    //region Energy and Drills Properties

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
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
            ItemStack battery = part.getItem();
            SuperMultiDrills.LOGGER.debug("getBatteryCapability: {}", battery);
            LazyOptional<IEnergyStorage> optional = battery.getCapability(CapabilityEnergy.ENERGY);
            return optional.orElse(new EnergyStorageItemImpl(battery, 1_000_000, 1000, 1000));
        }
        return new EnergyStorageItemImpl(stack, 1_000_000, 1000, 1000);
    }

    public static int getBatteryCapacity(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
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
        int efficiencyLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
        int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
        int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack);
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
        return SmdConst.Parts.SAW_UPGRADE.isPresent() && GearData.hasPart(stack, SmdConst.Parts.SAW_UPGRADE.get());
    }

    //endregion

    //region Gear properties

    private static final Set<PartType> BLACKLISTED_PARTS = ImmutableSet.of(
            PartType.BINDING,
            PartType.GRIP
    );

    @Override
    public boolean supportsPart(ItemStack gear, PartData part) {
        return !BLACKLISTED_PARTS.contains(part.getType()) && super.supportsPart(gear, part);
    }

    private static final Collection<PartType> RENDER_PARTS = ImmutableList.of(
            ChassisPart.TYPE,
            BatteryPart.TYPE,
            PartType.MAIN,
            PartType.COATING,
            PartType.TIP
    );

    @Override
    public Collection<PartType> getRenderParts() {
        return RENDER_PARTS;
    }

    //endregion

    //region Harvest tool overrides

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        boolean pickaxeOrShovelDrops = GearHelper.isCorrectToolForDrops(stack, state, BlockTags.MINEABLE_WITH_PICKAXE, EFFECTIVE_MATERIALS)
                || GearHelper.isCorrectToolForDrops(stack, state, BlockTags.MINEABLE_WITH_SHOVEL, EFFECTIVE_MATERIALS);

        if (!pickaxeOrShovelDrops && hasSaw(stack)) {
            return GearHelper.isCorrectToolForDrops(stack, state, BlockTags.MINEABLE_WITH_AXE, EXTRA_MATERIALS);
        }

        return pickaxeOrShovelDrops;
    }

    //endregion

    //region Standard tool overrides


    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (hasSaw(stack) && !GearHelper.isBroken(stack) && toolAction == ToolActions.AXE_DIG) {
            return true;
        }
        return super.canPerformAction(stack, toolAction);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        TextUtil.addEnergyInfo(stack, tooltip);
        tooltip.add(new TranslatableComponent("item.supermultidrills.drill.energyPerUse", getBaseEnergyCost(stack)));

        GearClientHelper.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage energy = optional.orElseThrow(IllegalStateException::new);
            boolean canHarvest = this.isCorrectToolForDrops(stack, state);
            // Just assume hardness of 1, since we don't have a World object
            boolean hasEnoughPower = energy.getEnergyStored() > getEnergyToBreakBlock(stack, state, 1);

            return canHarvest && hasEnoughPower ? GearHelper.getDestroySpeed(stack, state, EXTRA_MATERIALS) : 1.0f;
        }
        return GearHelper.getDestroySpeed(stack, state, EXTRA_MATERIALS);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int cost = getEnergyToBreakBlock(stack, Blocks.STONE.defaultBlockState(), 1);
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> EnergyUtil.drainEnergy(e, cost));
        return GearHelper.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        float hardness = state.getDestroySpeed(worldIn, pos);
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
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
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
        // Intentional NO-OP, drills use power instead of durability
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
        return Mth.hsvToRgb((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public int getDamage(ItemStack stack) {
        int value = (int) (100 * this.getDurabilityForDisplay(stack));
        return Mth.clamp(value, 0, 99);
    }

    //endregion
}
