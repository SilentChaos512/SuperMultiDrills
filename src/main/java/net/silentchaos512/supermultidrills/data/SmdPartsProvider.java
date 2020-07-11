package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.silentchaos512.gear.data.part.PartBuilder;
import net.silentchaos512.gear.data.part.PartsProvider;

import java.util.ArrayList;
import java.util.Collection;

public class SmdPartsProvider extends PartsProvider {
    public SmdPartsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected Collection<PartBuilder> getParts() {
        Collection<PartBuilder> ret = new ArrayList<>();

        // TODO: Can't actually build parts here because it requires PartPositions enum

        return ret;
    }
}
