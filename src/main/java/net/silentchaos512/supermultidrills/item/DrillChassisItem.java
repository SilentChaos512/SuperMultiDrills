package net.silentchaos512.supermultidrills.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.util.TextUtil;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class DrillChassisItem extends Item {
    private static final String NBT_COLOR = "Color";

    public DrillChassisItem() {
        super(new Properties().group(SuperMultiDrills.ITEM_GROUP).maxStackSize(1));
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
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (!isInGroup(group)) return;
        Arrays.stream(DyeColor.values()).forEach(dye -> items.add(getStack(dye.getFireworkColor())));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String colorStr = Color.format(getColor(stack));
        tooltip.add(TextUtil.translate("item", "drill_chassis.color", colorStr));
    }
}
