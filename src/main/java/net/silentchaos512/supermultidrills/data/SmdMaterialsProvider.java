package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.api.stats.StatInstance;
import net.silentchaos512.gear.data.material.MaterialBuilder;
import net.silentchaos512.gear.data.material.MaterialsProvider;
import net.silentchaos512.gear.gear.part.PartTextureSet;
import net.silentchaos512.gear.util.Const;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdTags;
import net.silentchaos512.supermultidrills.lib.SmdConst;
import net.silentchaos512.utils.Color;

import java.util.ArrayList;
import java.util.Collection;

public class SmdMaterialsProvider extends MaterialsProvider {
    public SmdMaterialsProvider(DataGenerator generator) {
        super(generator, SuperMultiDrills.MOD_ID);
    }

    @Override
    public String getName() {
        return "Super Multi-Drills - Materials";
    }

    @Override
    protected Collection<MaterialBuilder> getMaterials() {
        Collection<MaterialBuilder> ret = new ArrayList<>();

        ret.add(new MaterialBuilder(SmdConst.Materials.HEAVY_IRON.getId(), 2, Ingredient.EMPTY)
                .partSubstitute(PartType.ROD, SmdTags.Items.RODS_HEAVY_IRON)
                .stat(PartType.ROD, ItemStats.DURABILITY, 0.25f, StatInstance.Operation.MUL2)
                .stat(PartType.ROD, ItemStats.ENCHANTABILITY, -5, StatInstance.Operation.ADD)
                .stat(PartType.ROD, ItemStats.RARITY, 25)
                .trait(PartType.ROD, Const.Traits.MALLEABLE, 2)
                .trait(PartType.ROD, Const.Traits.MAGNETIC, 3)
                .display(PartType.ROD, PartTextureSet.HIGH_CONTRAST, Color.VALUE_WHITE)
        );

        return ret;
    }
}
