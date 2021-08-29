package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public final class SmdDataGenerators {
    private SmdDataGenerators() {}

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        SmdBlockTagsProvider blockTagsProvider = new SmdBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTagsProvider);
        gen.addProvider(new SmdItemTagsProvider(gen, blockTagsProvider, existingFileHelper));
        gen.addProvider(new SmdRecipeProvider(gen));

        gen.addProvider(new SmdMaterialsProvider(gen));
        gen.addProvider(new SmdPartsProvider(gen));
        gen.addProvider(new SmdTraitsProvider(gen));

        gen.addProvider(new SmdCompoundModelsProvider(gen, existingFileHelper));
    }
}
