package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public final class SmdDataGenerators {
    private SmdDataGenerators() {}

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(new SmdItemTagsProvider(gen));
        gen.addProvider(new SmdRecipeProvider(gen));

        gen.addProvider(new SmdPartsProvider(gen));
    }
}
