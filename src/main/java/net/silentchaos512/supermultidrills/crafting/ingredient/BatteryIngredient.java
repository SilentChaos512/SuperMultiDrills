package net.silentchaos512.supermultidrills.crafting.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.lib.util.TagUtils;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdTags;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public final class BatteryIngredient extends Ingredient {
    public static final ResourceLocation NAME = SuperMultiDrills.getId("battery");
    public static final Serializer SERIALIZER = new Serializer();

    private static ItemStack[] MATCHING_STACKS;

    public BatteryIngredient() {
        super(Stream.of());
    }

    @Override
    public ItemStack[] getItems() {
        determineMatchingStacks();
        //noinspection AssignmentOrReturnOfFieldWithMutableType
        return MATCHING_STACKS;
    }

    private static void determineMatchingStacks() {
        if (MATCHING_STACKS == null) {
            MATCHING_STACKS = ForgeRegistries.ITEMS.getValues().stream()
                    .map(ItemStack::new)
                    .filter(BatteryIngredient::isDrillBattery)
                    .toArray(ItemStack[]::new);
        }
    }

    private static boolean isDrillBattery(ItemStack stack) {
        return TagUtils.contains(SmdTags.Items.DRILL_BATTERIES, stack) && stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && isDrillBattery(stack);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", NAME.toString());
        return json;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return SERIALIZER;
    }

    public static final class Serializer implements IIngredientSerializer<BatteryIngredient> {
        @Override
        public BatteryIngredient parse(FriendlyByteBuf buffer) {
            return new BatteryIngredient();
        }

        @Override
        public BatteryIngredient parse(JsonObject json) {
            return new BatteryIngredient();
        }

        @Override
        public void write(FriendlyByteBuf buffer, BatteryIngredient ingredient) {
        }
    }
}
