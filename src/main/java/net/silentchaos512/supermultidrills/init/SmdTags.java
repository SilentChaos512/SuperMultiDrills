package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdTags {
    public static class Items {
        public static final ITag.INamedTag<Item> INGOTS_REDSTONE_ALLOY = forge("ingots/redstone_alloy");
        public static final ITag.INamedTag<Item> RODS_HEAVY_IRON = forge("rods/heavy_iron");

        public static final ITag.INamedTag<Item> DRILL_BATTERIES = mod("drill_batteries");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.makeWrapperTag(SuperMultiDrills.getId(path).toString());
        }
    }
}
