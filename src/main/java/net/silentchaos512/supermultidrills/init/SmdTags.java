package net.silentchaos512.supermultidrills.init;

import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdTags {
    public static class Items {
        public static final Tag.Named<Item> INGOTS_REDSTONE_ALLOY = forge("ingots/redstone_alloy");
        public static final Tag.Named<Item> RODS_HEAVY_IRON = forge("rods/heavy_iron");

        public static final Tag.Named<Item> DRILL_BATTERIES = mod("drill_batteries");

        private static Tag.Named<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Item> mod(String path) {
            return ItemTags.bind(SuperMultiDrills.getId(path).toString());
        }
    }
}
