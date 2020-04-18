package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.gear.item.blueprint.GearBlueprintItem;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.*;

public final class ModItems {
    public static DrillItem drill = new DrillItem();
    public static DrillHeadItem drillHead = new DrillHeadItem();
    public static DrillChassisItem drillChassis = new DrillChassisItem();
    public static DrillBatteryItem testBattery = new DrillBatteryItem(1_000_000, 1_000);
    public static GearBlueprintItem drillBlueprint = new GearBlueprintItem(false, drill);

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        register("drill", drill);
        // TODO: Might need to add drill to Gear's gearClasses map?
        register("drill_head", drillHead);
        register("drill_chassis", drillChassis);
        register("test_battery", testBattery);
        register("drill_blueprint", drillBlueprint);

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
