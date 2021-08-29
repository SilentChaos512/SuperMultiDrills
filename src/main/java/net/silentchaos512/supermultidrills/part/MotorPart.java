package net.silentchaos512.supermultidrills.part;

import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.silentchaos512.gear.api.part.IPartSerializer;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.part.AbstractGearPart;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;

import net.silentchaos512.gear.gear.part.AbstractGearPart.Serializer;

public class MotorPart extends AbstractGearPart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("motor");
    public static final IPartSerializer<MotorPart> SERIALIZER = new Serializer<>(TYPE_ID, MotorPart::new);
    public static final PartType TYPE = PartType.create(PartType.Builder.builder(TYPE_ID));

    public MotorPart(ResourceLocation partId) {
        super(partId);
    }

    @Override
    public PartType getType() {
        return TYPE;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public int getColor(PartData part, ItemStack gear, int layer, int animationFrame) {
        return Color.VALUE_WHITE;
    }

    @Override
    public Component getDisplayNamePrefix(@Nullable PartData part, ItemStack gear) {
        return super.getDisplayNamePrefix(part, gear);
    }

    @Override
    public boolean canAddToGear(ItemStack gear, PartData part) {
        return gear.getItem() instanceof DrillItem;
    }
}
