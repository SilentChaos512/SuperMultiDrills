package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.silentchaos512.supermultidrills.crafting.ingredient.BatteryIngredient;
import net.silentchaos512.supermultidrills.crafting.recipe.ChassisColorRecipe;
import net.silentchaos512.supermultidrills.crafting.recipe.DrillRecipe;

public final class ModRecipeStuff {
    private ModRecipeStuff() {}

    public static void init() {
        // Ingredients
        CraftingHelper.register(BatteryIngredient.NAME, BatteryIngredient.SERIALIZER);

        // Recipe Types
        IRecipeSerializer.register(ChassisColorRecipe.NAME.toString(), ChassisColorRecipe.SERIALIZER);
        IRecipeSerializer.register(DrillRecipe.NAME.toString(), DrillRecipe.SERIALIZER);
    }
}
