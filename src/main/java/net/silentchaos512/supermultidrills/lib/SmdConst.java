package net.silentchaos512.supermultidrills.lib;

import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.part.IGearPart;
import net.silentchaos512.gear.api.traits.ITrait;
import net.silentchaos512.gear.util.DataResource;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public final class SmdConst {
    private SmdConst() {}

    // FIXME: Add configs
//    public static final Expression DRILL_ENERGY_EXPR = new Expression("25 + e^(7 - 0.003 * durability) * (1 + 0.06 * efficiency) * hardness");

    public static final class Materials {
        public static final DataResource<IMaterial> HEAVY_IRON = DataResource.material(SuperMultiDrills.getId("heavy_iron"));

        private Materials() {}
    }

    public static final class Parts {
        public static final DataResource<IGearPart> SAW_UPGRADE = DataResource.part(SuperMultiDrills.getId("saw"));

        private Parts() {}
    }

    public static final class Traits {
        public static final DataResource<ITrait> STRONG_FLUX = DataResource.trait(SuperMultiDrills.getId("strong_flux"));
        public static final DataResource<ITrait> WEAK_FLUX = DataResource.trait(SuperMultiDrills.getId("weak_flux"));

        private Traits() {}
    }
}
