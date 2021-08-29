package net.silentchaos512.supermultidrills.item;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.util.TextUtil;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class DrillChassisItem extends Item {
    private static final String NBT_COLOR = "Color";

    public DrillChassisItem() {
        super(new Properties().tab(SuperMultiDrills.ITEM_GROUP).stacksTo(1));
    }

    public ItemStack getStack(int color) {
        ItemStack stack = new ItemStack(this);
        setColor(stack, color);
        return stack;
    }

    public static int getColor(ItemStack stack) {
        if (stack.getOrCreateTag().contains(NBT_COLOR))
            return stack.getOrCreateTag().getInt(NBT_COLOR);
        return Color.VALUE_WHITE;
    }

    public static void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt(NBT_COLOR, color);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (!allowdedIn(group)) return;
        Arrays.stream(DyeColor.values()).forEach(dye -> items.add(getStack(dye.getFireworkColor())));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        String colorStr = Color.format(getColor(stack));
        tooltip.add(TextUtil.translate("item", "drill_chassis.color", colorStr));
        tooltip.add(TextUtil.translate("item", "drill_chassis.info"));
    }
}
