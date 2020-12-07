package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.gear.data.client.CompoundModelsProvider;
import net.silentchaos512.gear.init.Registration;
import net.silentchaos512.gear.item.CompoundPartItem;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class SmdCompoundModelsProvider extends CompoundModelsProvider {
    public SmdCompoundModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Super Multi-Drills - Compound Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        Registration.getItems(CompoundPartItem.class).stream()
                .filter(item -> NameUtils.from(item).getNamespace().equalsIgnoreCase(SuperMultiDrills.MOD_ID))
                .forEach(item -> partBuilder(item).parent(itemGenerated));

//        gearBuilder(SmdItems.DRILL.get()).parent(itemHandheld);
    }
}
