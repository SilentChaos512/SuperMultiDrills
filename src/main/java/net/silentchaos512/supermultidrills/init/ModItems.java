package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.*;

import java.util.ArrayList;
import java.util.List;

public final class ModItems {
    public static DrillItem drill;
    public static DrillHeadItem drillHead;
    public static DrillChassisItem drillChassis;
    public static GearBlueprintItem drillBlueprint;

    public static final List<Item> ALL_ITEMS = new ArrayList<>();
    public static final List<DrillBatteryItem> BATTERIES = new ArrayList<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        drillBlueprint = register("drill_blueprint", new GearBlueprintItem(false, () -> drill));
        drill = register("drill", new DrillItem());
        net.silentchaos512.gear.init.ModItems.gearClasses.put(SuperMultiDrills.getId("drill"), drill);
        drillHead = register("drill_head", new DrillHeadItem());
        net.silentchaos512.gear.init.ModItems.gearClasses.put(SuperMultiDrills.getId("drill_head"), drillHead);
        drillChassis = register("drill_chassis", new DrillChassisItem());

        register("tater_battery", getBattery(2_000, 20));
        register("small_battery", getBattery(20_000, 200));
        register("medium_battery", getBattery(200_000, 2_000));
        register("large_battery", getBattery(2_000_000, 20_000));
        register("extreme_battery", getBattery(20_000_000, 200_000));

        for (CraftingItems item : CraftingItems.values()) {
            register(item.getName(), item.asItem());
        }
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = SuperMultiDrills.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        ALL_ITEMS.add(item);
        return item;
    }

    private static DrillBatteryItem getBattery(int capacity, int transferRate) {
        DrillBatteryItem item = new DrillBatteryItem(capacity, transferRate);
        BATTERIES.add(item);
        return item;
    }
}
