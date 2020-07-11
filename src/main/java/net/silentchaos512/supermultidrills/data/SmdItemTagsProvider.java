package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.init.Registration;
import net.silentchaos512.supermultidrills.init.SmdTags;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.item.DrillBatteryItem;

public class SmdItemTagsProvider extends ItemTagsProvider {
    public SmdItemTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        // Forge
        getBuilder(SmdTags.Items.INGOTS_REDSTONE_ALLOY).add(CraftingItems.REDSTONE_ALLOY_INGOT.asItem());

        // SMD
        getBuilder(ModItems.DRILL_BLUEPRINT.get().getItemTag()).add(ModItems.DRILL_BLUEPRINT.asItem());
        builder(SmdTags.Items.DRILL_BATTERIES, Registration.getItems(DrillBatteryItem.class));
    }

    private void builder(Tag<Item> tag, Iterable<? extends IItemProvider> items) {
        Tag.Builder<Item> builder = getBuilder(tag);
        items.forEach(item -> builder.add(item.asItem()));
    }
}
