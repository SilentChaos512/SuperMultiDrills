package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.crafting.recipe.ShapelessCompoundPartRecipe;
import net.silentchaos512.gear.crafting.recipe.ShapelessGearRecipe;
import net.silentchaos512.gear.init.ModTags;
import net.silentchaos512.lib.data.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.init.SmdTags;
import net.silentchaos512.supermultidrills.item.CraftingItems;
import net.silentchaos512.supermultidrills.part.BatteryPart;
import net.silentchaos512.supermultidrills.part.ChassisPart;
import net.silentchaos512.supermultidrills.part.MotorPart;

import java.util.function.Consumer;

public class SmdRecipeProvider extends RecipeProvider {
    public SmdRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Super Multi-Drills - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ExtendedShapelessRecipeBuilder.builder(ShapelessCompoundPartRecipe.SERIALIZER, ModItems.DRILL_HEAD)
                .addIngredient(ModItems.DRILL_BLUEPRINT.get().getItemTag())
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN), 5)
                .build(consumer);

        ExtendedShapelessRecipeBuilder.builder(ShapelessGearRecipe.SERIALIZER, ModItems.DRILL)
                .addIngredient(ModItems.DRILL_HEAD)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(MotorPart.TYPE))
                .addIngredient(GearPartIngredient.of(BatteryPart.TYPE))
                .addIngredient(GearPartIngredient.of(ChassisPart.TYPE))
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.DRILL_BLUEPRINT)
                .key('#', ModTags.Items.PAPER_BLUEPRINT)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" # ")
                .patternLine("#/#")
                .patternLine("#R#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.DRILL_CHASSIS)
                .key('#', Tags.Items.INGOTS_IRON)
                .key('G', CraftingItems.BATTERY_GAUGE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine(" G ")
                .build(consumer);

        //region CraftingItems

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.HEAVY_IRON_ROD)
                .key('/', Tags.Items.INGOTS_IRON)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine("/ ")
                .patternLine("/R")
                .patternLine("/ ")
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(CraftingItems.REDSTONE_ALLOY_INGOT)
                .addIngredient(Tags.Items.INGOTS_IRON)
                .addIngredient(Tags.Items.DUSTS_REDSTONE, 5)
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.RUDIMENTARY_MOTOR)
                .key('#', ItemTags.PLANKS)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .patternLine(" ##")
                .patternLine("/R/")
                .patternLine(" ##")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.BASIC_MOTOR)
                .key('#', Tags.Items.COBBLESTONE)
                .key('/', Tags.Items.INGOTS_IRON)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" ##")
                .patternLine("/R/")
                .patternLine(" ##")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.PERFORMANT_MOTOR)
                .key('#', Tags.Items.INGOTS_IRON)
                .key('/', Tags.Items.NUGGETS_GOLD)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" ##")
                .patternLine("/R/")
                .patternLine(" ##")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.ENTHUSIAST_MOTOR)
                .key('#', Tags.Items.INGOTS_GOLD)
                .key('/', Tags.Items.RODS_BLAZE)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .key('E', Tags.Items.ENDER_PEARLS)
                .patternLine(" ##")
                .patternLine("/R/")
                .patternLine("E##")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.ELITE_MOTOR)
                .key('#', Tags.Items.GEMS_DIAMOND)
                .key('/', Items.ENDER_EYE)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .key('E', Tags.Items.GEMS_PRISMARINE)
                .patternLine(" ##")
                .patternLine("/R/")
                .patternLine("E##")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.SAW_UPGRADE)
                .key('#', Tags.Items.INGOTS_IRON)
                .key('/', CraftingItems.HEAVY_IRON_ROD)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" # ")
                .patternLine("#/#")
                .patternLine("R# ")
                .build(consumer);

        //endregion
    }
}
