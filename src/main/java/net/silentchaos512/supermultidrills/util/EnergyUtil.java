package net.silentchaos512.supermultidrills.util;

import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyUtil {
    private EnergyUtil() {throw new IllegalAccessError("Utility class");}

    public static void drainEnergy(IEnergyStorage energy, int amount) {
        int amountLeft = amount;
        int amountDrained = -1;
        while (amountLeft > 0 && amountDrained != 0) {
            amountDrained = energy.extractEnergy(amountLeft, false);
            amountLeft -= amountDrained;
        }
    }
}
