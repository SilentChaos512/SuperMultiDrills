package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;
import net.silentchaos512.supermultidrills.crafting.recipe.DrillHeadSwapRecipe;
import net.silentchaos512.supermultidrills.crafting.recipe.DrillRecipe;

public final class ModRecipeStuff {
    private ModRecipeStuff() {}

    public static void init() {
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        register(ChassisColorRecipe.NAME, ChassisColorRecipe.SERIALIZER);
        register(DrillHeadSwapRecipe.NAME, DrillHeadSwapRecipe.SERIALIZER);
        register(DrillRecipe.NAME, DrillRecipe.SERIALIZER);
    }

    private static void register(ResourceLocation id, IRecipeSerializer<?> serializer) {
        serializer.setRegistryName(id);
        ForgeRegistries.RECIPE_SERIALIZERS.register(serializer);
    }
}
