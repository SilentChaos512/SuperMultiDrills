package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.RegistryObject;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;

import java.util.function.Supplier;

public final class ModRecipes {
    public static final RegistryObject<ChassisColorRecipe.Serializer> CHASSIS_COLOR = register("color_chassis", ChassisColorRecipe.Serializer::new);

    private ModRecipes() {}

    static void register() {
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);
    }

    private static <R extends IRecipe<?>, S extends IRecipeSerializer<R>> RegistryObject<S> register(String name, Supplier<S> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name, serializer);
    }
}
