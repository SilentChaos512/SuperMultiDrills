package net.silentchaos512.supermultidrills.part;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.part.IPartSerializer;
import net.silentchaos512.gear.api.part.IUpgradePart;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.part.AbstractGearPart;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.capability.EnergyStorageItemImpl;
import net.silentchaos512.supermultidrills.init.Registration;
import net.silentchaos512.supermultidrills.item.DrillBatteryItem;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;
import java.util.List;

public class BatteryPart extends AbstractGearPart implements IUpgradePart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("battery");
    public static final IPartSerializer<BatteryPart> SERIALIZER = new Serializer<>(TYPE_ID, BatteryPart::new);
    public static final PartType TYPE = PartType.create(PartType.Builder.builder(TYPE_ID));

    public BatteryPart(ResourceLocation partId) {
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
    public void onRemoveFromGear(ItemStack gear, PartData part) {
        // Reset energy value (since that isn't updated in the parts list)
        gear.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
            part.getCraftingItem().getCapability(CapabilityEnergy.ENERGY).ifPresent(partEnergy -> {
                if (partEnergy instanceof EnergyStorageItemImpl) {
                    ((EnergyStorageItemImpl) partEnergy).setEnergyStored(e.getEnergyStored());
                }
            });
        });
    }

    @Override
    public PartData randomizeData(GearType gearType, int tier) {
        List<DrillBatteryItem> batteries = Registration.getItems(DrillBatteryItem.class);
        int batteriesCount = batteries.size();
        int index = MathHelper.clamp(1 + SuperMultiDrills.random.nextInt(batteriesCount), tier, batteriesCount - 1);
        ItemStack stack = new ItemStack(batteries.get(index));
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
            if (e instanceof EnergyStorageItemImpl) {
                ((EnergyStorageItemImpl) e).setEnergyStored(e.getMaxEnergyStored());
            }
        });
        return PartData.of(this, stack);
    }

    @Override
    public boolean canAddToGear(ItemStack gear, PartData part) {
        return gear.getItem() instanceof DrillItem;
    }
}
