package net.silentchaos512.supermultidrills.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.api.stats.ItemStat;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.client.util.GearClientHelper;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.gear.util.GearHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class DrillHeadItem extends Item implements ICoreItem {
    public static final GearType GEAR_TYPE = GearType.getOrCreate("drill_head", GearType.TOOL);

    public static final Set<ItemStat> RELEVANT_STATS = ImmutableSet.of(
            ItemStats.HARVEST_LEVEL,
            ItemStats.HARVEST_SPEED,
            ItemStats.MELEE_DAMAGE,
            ItemStats.ATTACK_SPEED,
            ItemStats.DURABILITY,
            ItemStats.ENCHANTABILITY,
            ItemStats.RARITY
    );

    public DrillHeadItem() {
        super(new Properties().group(SuperMultiDrills.ITEM_GROUP).maxStackSize(1));
    }

    @Override
    public boolean supportsPartOfType(PartType type) {
        return false;
    }

    @Override
    public GearType getGearType() {
        return GEAR_TYPE;
    }

    @Override
    public Set<ItemStat> getRelevantStats(ItemStack stack) {
        return RELEVANT_STATS;
    }

    @Override
    public PartData[] getRenderParts(ItemStack stack) {
        return new PartData[]{GearData.getPrimaryPart(stack)};
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        GearClientHelper.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return GearHelper.getDisplayName(stack);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        GearHelper.fillItemGroup(this, group, items);
    }
}
