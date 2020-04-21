package net.silentchaos512.supermultidrills.lib;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

public class Constants {
    // Part ID's
    public static final ResourceLocation SAW_UPGRADE = SuperMultiDrills.getId("saw");

    // Tags
    public static final Tag<Item> DRILL_BATTERIES_TAG = new ItemTags.Wrapper(SuperMultiDrills.getId("drill_batteries"));

    // FIXME: Add configs
//    public static final Expression DRILL_ENERGY_EXPR = new Expression("25 + e^(7 - 0.003 * durability) * (1 + 0.06 * efficiency) * hardness");
}
