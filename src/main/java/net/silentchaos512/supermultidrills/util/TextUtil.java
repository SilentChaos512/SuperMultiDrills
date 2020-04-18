package net.silentchaos512.supermultidrills.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

import java.util.List;

public final class TextUtil {
    private static final String ENERGY_FORMAT = "%,d";

    private TextUtil() {throw new IllegalArgumentException("Utility class");}

    public static ITextComponent translate(String prefix, String suffix, Object... params) {
        String key = String.format("%s.%s.%s", prefix, SuperMultiDrills.MOD_ID, suffix);
        return new TranslationTextComponent(key, params);
    }

    public static ITextComponent energy(int amount) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        return translate("misc", "energy", s1);
    }

    public static ITextComponent energyWithMax(int amount, int max) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        String s2 = String.format(ENERGY_FORMAT, max);
        return translate("misc", "energyWithMax", s1, s2);
    }

    public static void addEnergyInfo(ItemStack stack, List<ITextComponent> tooltip) {
        if (CapabilityEnergy.ENERGY == null) return;
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }
}
