package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.silentchaos512.gear.item.ToolHeadItem;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.item.DrillBatteryItem;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;
import net.silentchaos512.supermultidrills.item.DrillItem;

import java.util.function.Supplier;

public final class ModItems {
    public static final ItemRegistryObject<GearBlueprintItem> DRILL_BLUEPRINT = register("drill_blueprint", () ->
            new GearBlueprintItem(DrillItem.GEAR_TYPE, false, baseProps()));
    public static final ItemRegistryObject<DrillItem> DRILL = register("drill", DrillItem::new);
    public static final ItemRegistryObject<ToolHeadItem> DRILL_HEAD = register("drill_head", () ->
            new ToolHeadItem(DrillItem.GEAR_TYPE, baseProps().maxStackSize(1)));
    public static final ItemRegistryObject<DrillChassisItem> DRILL_CHASSIS = register("drill_chassis", DrillChassisItem::new);

    public static final ItemRegistryObject<DrillBatteryItem> TATER_BATTERY = register("tater_battery", () ->
            new DrillBatteryItem(2_000, 20));
    public static final ItemRegistryObject<DrillBatteryItem> SMALL_BATTERY = register("small_battery", () ->
            new DrillBatteryItem(20_000, 200));
    public static final ItemRegistryObject<DrillBatteryItem> MEDIUM_BATTERY = register("medium_battery", () ->
            new DrillBatteryItem(200_000, 2_000));
    public static final ItemRegistryObject<DrillBatteryItem> LARGE_BATTERY = register("large_battery", () ->
            new DrillBatteryItem(2_000_000, 20_000));
    public static final ItemRegistryObject<DrillBatteryItem> EXTREME_BATTERY = register("extreme_battery", () ->
            new DrillBatteryItem(20_000_000, 200_000));

    static {
        CraftingItems.register();
    }

    private ModItems() {}

    static void register() {}

    private static Item.Properties baseProps() {
        return new Item.Properties().group(SuperMultiDrills.ITEM_GROUP);
    }

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return new ItemRegistryObject<>(Registration.ITEMS.register(name, item));
    }
}
