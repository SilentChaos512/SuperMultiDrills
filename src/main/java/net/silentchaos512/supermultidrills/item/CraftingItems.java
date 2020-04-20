package net.silentchaos512.supermultidrills.item;

import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.utils.Lazy;

import java.util.Locale;

public enum CraftingItems implements IItemProvider {
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

    private final Lazy<Item> item;

    CraftingItems() {
        this.item = Lazy.of(() -> new Item(new Item.Properties().group(SuperMultiDrills.ITEM_GROUP)));
    }

    @Override
    public Item asItem() {
        return item.get();
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
