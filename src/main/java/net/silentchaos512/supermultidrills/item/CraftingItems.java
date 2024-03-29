package net.silentchaos512.supermultidrills.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.Registration;

import java.util.Locale;

public enum CraftingItems implements ItemLike {
    HEAVY_IRON_ROD,
    BATTERY_GAUGE,
    REDSTONE_ALLOY_INGOT,
    REDSTONE_ALLOY_PLATE,
    RUDIMENTARY_MOTOR,
    BASIC_MOTOR,
    PERFORMANT_MOTOR,
    ENTHUSIAST_MOTOR,
    ELITE_MOTOR,
    SAW_UPGRADE,
    ;

    @SuppressWarnings("NonFinalFieldInEnum") private ItemRegistryObject<Item> item;

    public static void register() {
        for (CraftingItems item : values()) {
            item.item = new ItemRegistryObject<>(Registration.ITEMS.register(item.getName(), () ->
                    new Item(new Item.Properties().tab(SuperMultiDrills.ITEM_GROUP))));
        }
    }

    @Override
    public Item asItem() {
        return item.get();
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
