package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.RegistryObject;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;

import java.util.function.Supplier;

public final class SmdRecipes {
    public static final RegistryObject<SpecialRecipeSerializer<ChassisColorRecipe>> CHASSIS_COLOR = register("color_chassis", () ->
            new SpecialRecipeSerializer<>(ChassisColorRecipe::new));

    private SmdRecipes() {}

    static void register() {
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);
    }

    private static <R extends IRecipe<?>, S extends IRecipeSerializer<R>> RegistryObject<S> register(String name, Supplier<S> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name, serializer);
    }
}
