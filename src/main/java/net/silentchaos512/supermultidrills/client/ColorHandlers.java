package net.silentchaos512.supermultidrills.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;

public class ColorHandlers {
    public static void onItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
//        register(colors, ColorHandlers::getDrillColor, ModItems.DRILL);
//        register(colors, (stack, tintIndex) -> {
//            if (tintIndex == 0) {
//                return ToolHeadItem.getColor(stack);
//            }
//            return Color.VALUE_WHITE;
//        }, ModItems.DRILL_HEAD);
        register(colors, (stack, tintIndex) -> DrillChassisItem.getColor(stack), SmdItems.DRILL_CHASSIS);
    }

    private static void register(ItemColors colors, ItemColor itemColor, ItemRegistryObject<? extends Item> item) {
        if (item.isPresent()) {
            colors.register(itemColor, item);
        } else {
            SuperMultiDrills.LOGGER.error("Failed to register color handler for {}, object not present. Check log for other errors.", item.getId());
        }
    }

//    public static int getDrillColor(ItemStack stack, int tintIndex) {
//        if (tintIndex == 0) {
//            // Chassis
//            return GearData.getColor(stack, ChassisPart.TYPE);
//        }
//        if (tintIndex == 1) {
//            // Head
//            return GearData.getColor(stack, PartType.MAIN);
//        }
//        if (tintIndex == 4) {
//            // Tip
//            return GearData.getColor(stack, PartType.TIP);
//        }
//        return Color.VALUE_WHITE;
//    }
}
