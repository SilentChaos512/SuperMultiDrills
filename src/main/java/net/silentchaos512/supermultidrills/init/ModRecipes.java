package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.RegistryObject;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapelessRecipe;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;
import net.silentchaos512.supermultidrills.crafting.recipe.DrillHeadSwapRecipe;
import net.silentchaos512.supermultidrills.crafting.recipe.DrillRecipe;

import java.util.function.Supplier;

public final class ModRecipes {
    public static final RegistryObject<ChassisColorRecipe.Serializer> CHASSIS_COLOR = register("chassis_color", ChassisColorRecipe.Serializer::new);
    public static final RegistryObject<ExtendedShapelessRecipe.Serializer<DrillHeadSwapRecipe>> DRILL_HEAD_SWAP = register("drill_head_swap", () ->
            ExtendedShapelessRecipe.Serializer.basic(DrillHeadSwapRecipe::new));
    public static final RegistryObject<ExtendedShapelessRecipe.Serializer<DrillRecipe>> DRILL_CRAFTING = register("drill_crafting", () ->
            ExtendedShapelessRecipe.Serializer.basic(DrillRecipe::new));

    private ModRecipes() {}

    static void register() {
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);
    }

    private static <R extends IRecipe<?>, S extends IRecipeSerializer<R>> RegistryObject<S> register(String name, Supplier<S> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name, serializer);
    }
}
