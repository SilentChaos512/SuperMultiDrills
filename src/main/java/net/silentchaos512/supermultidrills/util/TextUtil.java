package net.silentchaos512.supermultidrills.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

import java.util.List;

public final class TextUtil {
    private static final String ENERGY_FORMAT = "%,d";

    private TextUtil() {throw new IllegalAccessError("Utility class");}

    public static Component translate(String prefix, String suffix, Object... params) {
        String key = String.format("%s.%s.%s", prefix, SuperMultiDrills.MOD_ID, suffix);
        return new TranslatableComponent(key, params);
    }

    public static Component energy(int amount) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        return translate("misc", "energy", s1);
    }

    public static Component energyWithMax(int amount, int max) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        String s2 = String.format(ENERGY_FORMAT, max);
        return translate("misc", "energyWithMax", s1, s2);
    }

    public static void addEnergyInfo(ItemStack stack, List<Component> tooltip) {
        if (CapabilityEnergy.ENERGY == null) return;
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }
}
