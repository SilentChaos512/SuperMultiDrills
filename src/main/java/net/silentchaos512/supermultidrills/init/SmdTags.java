package net.silentchaos512.supermultidrills.init;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdTags {
    public static class Items {
        public static final TagKey<Item> INGOTS_REDSTONE_ALLOY = forge("ingots/redstone_alloy");
        public static final TagKey<Item> RODS_HEAVY_IRON = forge("rods/heavy_iron");

        public static final TagKey<Item> DRILL_BATTERIES = mod("drill_batteries");

        private static TagKey<Item> forge(String path) {
            return ItemTags.create(new ResourceLocation("forge", path));
        }

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(SuperMultiDrills.getId(path));
        }
    }
}
