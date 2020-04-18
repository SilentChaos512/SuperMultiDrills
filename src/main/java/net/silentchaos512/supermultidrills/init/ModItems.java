package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.*;

public final class ModItems {
    public static DrillItem drill;
    public static DrillHeadItem drillHead;
    public static DrillChassisItem drillChassis;
    public static DrillBatteryItem testBattery;
    public static GearBlueprintItem drillBlueprint;

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        drill = register("drill", new DrillItem());
        net.silentchaos512.gear.init.ModItems.gearClasses.put(SuperMultiDrills.getId("drill"), drill);
        drillHead = register("drill_head", new DrillHeadItem());
        net.silentchaos512.gear.init.ModItems.gearClasses.put(SuperMultiDrills.getId("drill_head"), drillHead);
        drillChassis = register("drill_chassis", new DrillChassisItem());
        testBattery = register("test_battery", new DrillBatteryItem(1_000_000, 100_000));
        drillBlueprint = register("drill_blueprint", new GearBlueprintItem(false, drill));

        for (CraftingItems item : CraftingItems.values()) {
            register(item.getName(), item.asItem());
        }
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = SuperMultiDrills.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
