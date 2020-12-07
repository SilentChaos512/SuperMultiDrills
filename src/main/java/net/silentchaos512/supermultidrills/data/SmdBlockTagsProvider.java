package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdBlockTagsProvider extends BlockTagsProvider {
    public SmdBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, SuperMultiDrills.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
    }
}
