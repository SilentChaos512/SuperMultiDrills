package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.silentchaos512.gear.data.trait.TraitBuilder;
import net.silentchaos512.gear.data.trait.TraitsProvider;
import net.silentchaos512.supermultidrills.lib.SmdConst;

import java.util.ArrayList;
import java.util.Collection;

public class SmdTraitsProvider extends TraitsProvider {
    public SmdTraitsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Super Multi-Drills - Traits";
    }

    @Override
    protected Collection<TraitBuilder> getTraits() {
        Collection<TraitBuilder> ret = new ArrayList<>();

        ret.add(TraitBuilder.simple(SmdConst.Traits.STRONG_FLUX, 5)
                .cancelsWith(SmdConst.Traits.WEAK_FLUX));
        ret.add(TraitBuilder.simple(SmdConst.Traits.WEAK_FLUX, 5)
                .cancelsWith(SmdConst.Traits.STRONG_FLUX));

        return ret;
    }
}
