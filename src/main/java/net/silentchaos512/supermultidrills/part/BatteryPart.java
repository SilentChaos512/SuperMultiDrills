package net.silentchaos512.supermultidrills.part;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.IPartPosition;
import net.silentchaos512.gear.api.parts.IPartSerializer;
import net.silentchaos512.gear.api.parts.IUpgradePart;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.parts.AbstractGearPart;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.capability.EnergyStorageItemImpl;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.item.DrillItem;

import javax.annotation.Nullable;

public class BatteryPart extends AbstractGearPart implements IUpgradePart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("battery");
    public static final PartType TYPE = PartType.create(TYPE_ID, new Serializer<>(TYPE_ID, BatteryPart::new));
    public static final IPartPosition POSITION = new IPartPosition() {
        @Override
        public String getTexturePrefix() {
            return "battery";
        }

        @Override
        public String getModelIndex() {
            return "battery";
        }
    };

    public BatteryPart(ResourceLocation partId) {
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
    public ITextComponent getDisplayName(@Nullable PartData part, ItemStack gear) {
        if (part != null) {
            return part.getCraftingItem().getDisplayName();
        }
        return super.getDisplayName(null, gear);
    }

    @Override
    public void onAddToGear(ItemStack gear, ItemStack part) {
        if (!(gear.getItem() instanceof DrillItem)) return;

        // Get the battery part's energy cap, set drill's capacity
        IEnergyStorage partEnergy = part.getCapability(CapabilityEnergy.ENERGY)
                .orElse(new EnergyStorageItemImpl(part, 1_000_000, 1_000, 1_000));
        DrillItem.setBatteryCapacity(gear, partEnergy.getMaxEnergyStored());

        // Set drill energy to match the battery's
        LazyOptional<IEnergyStorage> gearOptional = gear.getCapability(CapabilityEnergy.ENERGY);
        if (gearOptional.isPresent()) {
            IEnergyStorage gearEnergy = gearOptional.orElseThrow(IllegalStateException::new);
            if (gearEnergy instanceof EnergyStorageItemImpl) {
                EnergyStorageItemImpl gearEnergyImpl = (EnergyStorageItemImpl) gearEnergy;
                gearEnergyImpl.setEnergyStored(partEnergy.getEnergyStored());
            }
        } else {
            SuperMultiDrills.LOGGER.catching(new NullPointerException("Either drill or battery is missing its energy capability"));
        }
    }

    @Override
    public PartData randomizeData() {
        int batteriesCount = ModItems.BATTERIES.size();
        int index = MathHelper.clamp(1 + SuperMultiDrills.random.nextInt(batteriesCount), 2, batteriesCount - 1);
        ItemStack stack = new ItemStack(ModItems.BATTERIES.get(index));
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
            if (e instanceof EnergyStorageItemImpl) {
                ((EnergyStorageItemImpl) e).setEnergyStored(e.getMaxEnergyStored());
            }
        });
        return PartData.of(this, stack);
    }
}
