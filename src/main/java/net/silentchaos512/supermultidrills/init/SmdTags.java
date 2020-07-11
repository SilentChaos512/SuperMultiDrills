package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdTags {
    public static class Items {
        public static final Tag<Item> INGOTS_REDSTONE_ALLOY = forge("ingots/redstone_alloy");

        public static final Tag<Item> DRILL_BATTERIES = mod("drill_batteries");

        private static Tag<Item> forge(String path) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", path));
        }

        private static Tag<Item> mod(String path) {
            return new ItemTags.Wrapper(SuperMultiDrills.getId(path));
        }
    }
}
