package net.silentchaos512.supermultidrills.client;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.utils.Color;

public class ColorHandlers {
    public static void onItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        colors.register(ColorHandlers::getDrillColor, ModItems.DRILL);
        colors.register(ColorHandlers::getDrillHeadColor, ModItems.DRILL_HEAD);
        colors.register((stack, tintIndex) -> DrillChassisItem.getColor(stack), ModItems.DRILL_CHASSIS);
    }

    private static int getDrillColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            // Chassis
            return DrillItem.getChassisColor(stack);
        }
        if (tintIndex == 1) {
            // Head
            return GearData.getHeadColor(stack, true);
        }
        if (tintIndex == 4) {
            // Tip
            PartData part = GearData.getPartOfType(stack, PartType.TIP);
            if (part == null) return Color.VALUE_WHITE;
            return part.getFallbackColor(stack, 0);
        }
        return Color.VALUE_WHITE;
    }

    private static int getDrillHeadColor(ItemStack stack, int tintIndex) {
        if (tintIndex == 0) {
            return GearData.getHeadColor(stack, true);
        }
        return Color.VALUE_WHITE;
    }
}
