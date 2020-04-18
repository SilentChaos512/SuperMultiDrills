package net.silentchaos512.supermultidrills.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class ChassisColorRecipe extends SpecialRecipe {
    public static final ResourceLocation NAME = SuperMultiDrills.getId("color_chassis");
    public static final Serializer SERIALIZER = new Serializer();

    public ChassisColorRecipe(ResourceLocation recipeId) {
        super(recipeId);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        StackList list = StackList.from(inv);
        ItemStack chassis = list.uniqueMatch(ChassisColorRecipe::isDrillChassis);
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());

        return !chassis.isEmpty() && !dyes.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        StackList list = StackList.from(inv);
        ItemStack chassis = list.uniqueMatch(ChassisColorRecipe::isDrillChassis).copy();
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());
        applyDyes(chassis, dyes);
        return chassis;
    }

    private static boolean isDrillChassis(ItemStack stack) {
        return stack.getItem() == ModItems.drillChassis;
    }

    // Largely copied from RecipesArmorDyes
    private static void applyDyes(ItemStack chassis, Collection<ItemStack> dyes) {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int currentColor = DrillChassisItem.getColor(chassis);
        if (currentColor != DyeColor.WHITE.getFireworkColor()) {
            float r = (float) (currentColor >> 16 & 255) / 255.0F;
            float g = (float) (currentColor >> 8 & 255) / 255.0F;
            float b = (float) (currentColor & 255) / 255.0F;
            maxColorSum = (int) ((float) maxColorSum + Math.max(r, Math.max(g, b)) * 255.0F);
            componentSums[0] = (int) ((float) componentSums[0] + r * 255.0F);
            componentSums[1] = (int) ((float) componentSums[1] + g * 255.0F);
            componentSums[2] = (int) ((float) componentSums[2] + b * 255.0F);
            ++colorCount;
        }

        for (ItemStack dye : dyes) {
            float[] componentValues = getDyeColor(dye)
                    .orElse(DyeColor.WHITE)
                    .getColorComponentValues();
            int r = (int) (componentValues[0] * 255.0F);
            int g = (int) (componentValues[1] * 255.0F);
            int b = (int) (componentValues[2] * 255.0F);
            maxColorSum += Math.max(r, Math.max(g, b));
            componentSums[0] += r;
            componentSums[1] += g;
            componentSums[2] += b;
            ++colorCount;
        }

        if (colorCount > 0) {
            int r = componentSums[0] / colorCount;
            int g = componentSums[1] / colorCount;
            int b = componentSums[2] / colorCount;
            float maxAverage = (float) maxColorSum / (float) colorCount;
            float max = (float) Math.max(r, Math.max(g, b));
            r = (int) ((float) r * maxAverage / max);
            g = (int) ((float) g * maxAverage / max);
            b = (int) ((float) b * maxAverage / max);
            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;
            DrillChassisItem.setColor(chassis, finalColor);
        }
    }

    private static Optional<DyeColor> getDyeColor(ItemStack dye) {
        return Optional.ofNullable(DyeColor.getColor(dye));
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.drillChassis);
    }

    @Override
    public boolean isDynamic() {
        // Don't show in recipe book
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ChassisColorRecipe> {
        private Serializer() {}

        @Override
        public ChassisColorRecipe read(ResourceLocation recipeId, JsonObject json) {
            return new ChassisColorRecipe(recipeId);
        }

        @Override
        public ChassisColorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            return new ChassisColorRecipe(recipeId);
        }

        @Override
        public void write(PacketBuffer buffer, ChassisColorRecipe recipe) {}
    }
}
