package net.silentchaos512.supermultidrills.part;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.IPartPosition;
import net.silentchaos512.gear.api.parts.IPartSerializer;
import net.silentchaos512.gear.api.parts.IUpgradePart;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.parts.AbstractGearPart;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;
import net.silentchaos512.supermultidrills.item.DrillItem;

public class ChassisPart extends AbstractGearPart implements IUpgradePart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("chassis");
    public static final PartType TYPE = PartType.create(TYPE_ID, "SMDc", new Serializer<>(TYPE_ID, ChassisPart::new));
    public static final IPartPosition POSITION = new IPartPosition() {
        @Override
        public String getTexturePrefix() {
            return "chassis";
        }

        @Override
        public String getModelIndex() {
            return "chassis";
        }
    };

    public ChassisPart(ResourceLocation partId) {
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

    @Override
    public void onAddToGear(ItemStack gear, ItemStack part) {
        int color = DrillChassisItem.getColor(part);
        DrillItem.setChassisColor(gear, color);
    }

    @Override
    public int getColor(PartData part, ItemStack gear, int animationFrame) {
        return DrillItem.getChassisColor(gear);
    }
}
