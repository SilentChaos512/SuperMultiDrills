package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.silentchaos512.supermultidrills.data.recipe.SmdRecipeProvider;

public final class DataGenerators {
    private DataGenerators() {}

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(new SmdItemTagsProvider(gen));
        gen.addProvider(new SmdRecipeProvider(gen));
    }
}
