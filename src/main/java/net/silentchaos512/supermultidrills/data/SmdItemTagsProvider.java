package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.init.Registration;
import net.silentchaos512.supermultidrills.init.SmdTags;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.item.DrillBatteryItem;

public class SmdItemTagsProvider extends ItemTagsProvider {
    public SmdItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blocks, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blocks, SuperMultiDrills.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        // Forge
        getOrCreateBuilder(SmdTags.Items.INGOTS_REDSTONE_ALLOY).add(CraftingItems.REDSTONE_ALLOY_INGOT.asItem());
        getOrCreateBuilder(SmdTags.Items.RODS_HEAVY_IRON).add(CraftingItems.HEAVY_IRON_ROD.asItem());
        getOrCreateBuilder(Tags.Items.RODS).addTag(SmdTags.Items.RODS_HEAVY_IRON);

        // SMD
        getOrCreateBuilder(SmdItems.DRILL_BLUEPRINT.get().getItemTag()).add(SmdItems.DRILL_BLUEPRINT.asItem());
        builder(SmdTags.Items.DRILL_BATTERIES, Registration.getItems(DrillBatteryItem.class))
                .addOptional(new ResourceLocation("silents_mechanisms", "battery"));
    }

    private Builder<Item> builder(ITag.INamedTag<Item> tag, Iterable<? extends IItemProvider> items) {
        Builder<Item> builder = getOrCreateBuilder(tag);
        items.forEach(item -> builder.add(item.asItem()));
        return builder;
    }
}
