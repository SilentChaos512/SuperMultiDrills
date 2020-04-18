package net.silentchaos512.supermultidrills.part;

import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.IPartPosition;
import net.silentchaos512.gear.api.parts.IPartSerializer;
import net.silentchaos512.gear.api.parts.IUpgradePart;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.parts.AbstractGearPart;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.DrillItem;

public class MotorPart extends AbstractGearPart implements IUpgradePart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("motor");
    public static final PartType TYPE = PartType.create(TYPE_ID, "SMDm", new Serializer<>(TYPE_ID, MotorPart::new));
    public static final IPartPosition POSITION = new IPartPosition() {
        @Override
        public String getTexturePrefix() {
            return "motor";
        }

        @Override
        public String getModelIndex() {
            return "motor";
        }
    };

    public MotorPart(ResourceLocation partId) {
        super(partId);
    }

    @Override
    public PartType getType() {
        return TYPE;
    }

    @Override
    public IPartPosition getPartPosition() {
        return POSITION;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return TYPE.getSerializer();
    }

    @Override
    public boolean isValidFor(ICoreItem gearItem) {
        return gearItem instanceof DrillItem;
    }

    @Override
    public boolean replacesExisting() {
        return true;
    }
}
