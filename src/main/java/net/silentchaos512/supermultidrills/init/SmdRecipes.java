package net.silentchaos512.supermultidrills.init;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;

import java.util.function.Supplier;

public final class SmdRecipes {
    public static final RegistryObject<SimpleRecipeSerializer<ChassisColorRecipe>> CHASSIS_COLOR = register("color_chassis", () ->
            new SimpleRecipeSerializer<>(ChassisColorRecipe::new));

    private SmdRecipes() {}

    static void register() {
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);
    }

    private static <R extends Recipe<?>, S extends RecipeSerializer<R>> RegistryObject<S> register(String name, Supplier<S> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name, serializer);
    }
}
