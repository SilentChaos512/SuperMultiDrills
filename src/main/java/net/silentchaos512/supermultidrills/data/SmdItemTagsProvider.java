package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.init.Registration;
import net.silentchaos512.supermultidrills.init.SmdTags;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.item.DrillBatteryItem;

import net.minecraft.data.tags.TagsProvider.TagAppender;

public class SmdItemTagsProvider extends ItemTagsProvider {
    public SmdItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blocks, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blocks, SuperMultiDrills.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        // Forge
        tag(SmdTags.Items.INGOTS_REDSTONE_ALLOY).add(CraftingItems.REDSTONE_ALLOY_INGOT.asItem());
        tag(SmdTags.Items.RODS_HEAVY_IRON).add(CraftingItems.HEAVY_IRON_ROD.asItem());
        tag(Tags.Items.RODS).addTag(SmdTags.Items.RODS_HEAVY_IRON);

        // SMD
        tag(SmdItems.DRILL_BLUEPRINT.get().getItemTag()).add(SmdItems.DRILL_BLUEPRINT.asItem());
        builder(SmdTags.Items.DRILL_BATTERIES, Registration.getItems(DrillBatteryItem.class))
                .addOptional(new ResourceLocation("silents_mechanisms", "battery"));
    }

    private TagAppender<Item> builder(TagKey<Item> tag, Iterable<? extends ItemLike> items) {
        TagAppender<Item> builder = tag(tag);
        items.forEach(item -> builder.add(item.asItem()));
        return builder;
    }
}
