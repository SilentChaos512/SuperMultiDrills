package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.silentchaos512.gear.api.material.MaterialLayer;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.api.stats.ItemStats;
import net.silentchaos512.gear.api.stats.StatInstance;
import net.silentchaos512.gear.data.part.PartBuilder;
import net.silentchaos512.gear.data.part.PartsProvider;
import net.silentchaos512.gear.gear.part.PartSerializers;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.supermultidrills.lib.SmdConst;
import net.silentchaos512.supermultidrills.part.BatteryPart;
import net.silentchaos512.supermultidrills.part.ChassisPart;
import net.silentchaos512.supermultidrills.part.MotorPart;
import net.silentchaos512.utils.Color;

import java.util.ArrayList;
import java.util.Collection;

public class SmdPartsProvider extends PartsProvider {
    public SmdPartsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Super Multi-Drills - Parts";
    }

    @Override
    protected Collection<PartBuilder> getParts() {
        Collection<PartBuilder> ret = new ArrayList<>();

        ret.add(drillPart("drill_head", PartType.MAIN, SmdItems.DRILL_HEAD)
                .stat(ItemStats.ENCHANTABILITY, -0.5f, StatInstance.Operation.MUL1)
                .stat(ItemStats.ATTACK_SPEED, -2.0f, StatInstance.Operation.ADD)
                .stat(ItemStats.REPAIR_EFFICIENCY, 0)
        );

        ret.add(drillPart("battery", BatteryPart.TYPE, new BatteryIngredient())
                .serializerType(BatteryPart.SERIALIZER)
                .display(DrillItem.GEAR_TYPE, BatteryPart.TYPE, new MaterialLayer(SuperMultiDrills.getId("battery_gauge"), Color.VALUE_WHITE)));
        ret.add(drillPart("chassis", ChassisPart.TYPE, SmdItems.DRILL_CHASSIS)
                .serializerType(ChassisPart.SERIALIZER)
                .display(DrillItem.GEAR_TYPE, ChassisPart.TYPE, new MaterialLayer(SuperMultiDrills.getId("chassis"), 0))); // 0 fixes part color being ignored
        ret.add(drillPart("saw", PartType.MISC_UPGRADE, CraftingItems.SAW_UPGRADE)
                .serializerType(PartSerializers.UPGRADE_PART));

        ret.add(drillPart("motor/rudimentary", MotorPart.TYPE, CraftingItems.RUDIMENTARY_MOTOR)
                .serializerType(MotorPart.SERIALIZER)
                .stat(ItemStats.DURABILITY, -0.3f, StatInstance.Operation.MUL2)
                .stat(ItemStats.HARVEST_SPEED, -0.5f, StatInstance.Operation.MUL2)
                .stat(ItemStats.MELEE_DAMAGE, -3f, StatInstance.Operation.ADD)
                .stat(ItemStats.RARITY, -10, StatInstance.Operation.ADD)
                .trait(SmdConst.Traits.WEAK_FLUX, 3)
        );
        ret.add(drillPart("motor/basic", MotorPart.TYPE, CraftingItems.BASIC_MOTOR)
                .serializerType(MotorPart.SERIALIZER)
                .stat(ItemStats.DURABILITY, -0.1f, StatInstance.Operation.MUL2)
                .stat(ItemStats.HARVEST_LEVEL, 1, StatInstance.Operation.MAX)
                .stat(ItemStats.HARVEST_SPEED, -0.2f, StatInstance.Operation.MUL2)
                .stat(ItemStats.MELEE_DAMAGE, -1f, StatInstance.Operation.ADD)
                .trait(SmdConst.Traits.WEAK_FLUX, 2)
        );
        ret.add(drillPart("motor/performant", MotorPart.TYPE, CraftingItems.PERFORMANT_MOTOR)
                .serializerType(MotorPart.SERIALIZER)
                .stat(ItemStats.DURABILITY, 0.1f, StatInstance.Operation.MUL2)
                .stat(ItemStats.HARVEST_LEVEL, 2, StatInstance.Operation.MAX)
                .stat(ItemStats.HARVEST_SPEED, 0.2f, StatInstance.Operation.MUL2)
                .stat(ItemStats.MELEE_DAMAGE, 1f, StatInstance.Operation.ADD)
                .stat(ItemStats.RARITY, 10, StatInstance.Operation.ADD)
                .trait(SmdConst.Traits.WEAK_FLUX, 1)
        );
        ret.add(drillPart("motor/enthusiast", MotorPart.TYPE, CraftingItems.ENTHUSIAST_MOTOR)
                .serializerType(MotorPart.SERIALIZER)
                .stat(ItemStats.DURABILITY, 0.2f, StatInstance.Operation.MUL2)
                .stat(ItemStats.HARVEST_LEVEL, 3, StatInstance.Operation.MAX)
                .stat(ItemStats.HARVEST_SPEED, 0.4f, StatInstance.Operation.MUL2)
                .stat(ItemStats.MELEE_DAMAGE, 3f, StatInstance.Operation.ADD)
                .stat(ItemStats.RARITY, 20, StatInstance.Operation.ADD)
                .trait(SmdConst.Traits.STRONG_FLUX, 2)
        );
        ret.add(drillPart("motor/elite", MotorPart.TYPE, CraftingItems.ELITE_MOTOR)
                .serializerType(MotorPart.SERIALIZER)
                .stat(ItemStats.DURABILITY, 0.42f, StatInstance.Operation.MUL2)
                .stat(ItemStats.HARVEST_LEVEL, 4, StatInstance.Operation.MAX)
                .stat(ItemStats.HARVEST_SPEED, 0.66f, StatInstance.Operation.MUL2)
                .stat(ItemStats.MELEE_DAMAGE, 5f, StatInstance.Operation.ADD)
                .stat(ItemStats.RARITY, 20, StatInstance.Operation.ADD)
                .trait(SmdConst.Traits.STRONG_FLUX, 4)
        );

        return ret;
    }

    private static PartBuilder drillPart(String name, PartType partType, IItemProvider item) {
        return drillPart(name, partType, Ingredient.fromItems(item));
    }

    private static PartBuilder drillPart(String name, PartType partType, Ingredient ingredient) {
        return new PartBuilder(SuperMultiDrills.getId(name), DrillItem.GEAR_TYPE, partType, ingredient)
                .name(new TranslationTextComponent("part.supermultidrills." + name.replace('/', '.')));
    }
}
