package net.silentchaos512.supermultidrills.data;

import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.crafting.ingredient.BlueprintIngredient;
import net.silentchaos512.gear.crafting.ingredient.GearPartIngredient;
import net.silentchaos512.gear.crafting.ingredient.PartMaterialIngredient;
import net.silentchaos512.gear.init.ModRecipes;
import net.silentchaos512.gear.init.ModTags;
import net.silentchaos512.lib.data.recipe.ExtendedShapedRecipeBuilder;
import net.silentchaos512.lib.data.recipe.ExtendedShapelessRecipeBuilder;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.init.SmdRecipes;
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
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SpecialRecipeBuilder.special(SmdRecipes.CHASSIS_COLOR.get()).save(consumer, SuperMultiDrills.getId("color_chassis").toString());

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.COMPOUND_PART.get(), SmdItems.DRILL_HEAD)
                .addIngredient(BlueprintIngredient.of(SmdItems.DRILL_BLUEPRINT.get()))
                .addIngredient(PartMaterialIngredient.of(PartType.MAIN), 5)
                .build(consumer);

        ExtendedShapelessRecipeBuilder.builder(ModRecipes.SHAPELESS_GEAR.get(), SmdItems.DRILL)
                .addIngredient(SmdItems.DRILL_HEAD)
                .addIngredient(GearPartIngredient.of(PartType.ROD))
                .addIngredient(GearPartIngredient.of(MotorPart.TYPE))
                .addIngredient(GearPartIngredient.of(BatteryPart.TYPE))
                .addIngredient(GearPartIngredient.of(ChassisPart.TYPE))
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.DRILL_BLUEPRINT)
                .key('#', ModTags.Items.BLUEPRINT_PAPER)
                .key('/', Tags.Items.RODS_WOODEN)
                .key('R', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" # ")
                .patternLine("#/#")
                .patternLine("#R#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.DRILL_CHASSIS)
                .key('#', Tags.Items.INGOTS_IRON)
                .key('G', CraftingItems.BATTERY_GAUGE)
                .patternLine("###")
                .patternLine("# #")
                .patternLine(" G ")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(CraftingItems.BATTERY_GAUGE)
                .key('r', Tags.Items.DYES_RED)
                .key('g', Tags.Items.DYES_GREEN)
                .key('b', Tags.Items.DYES_BLUE)
                .key('#', Tags.Items.GLASS_COLORLESS)
                .key('/', Tags.Items.INGOTS_GOLD)
                .patternLine("rgb")
                .patternLine("#/#")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.TATER_BATTERY)
                .key('i', Tags.Items.NUGGETS_IRON)
                .key('p', Tags.Items.CROPS_POTATO)
                .key('/', Tags.Items.STRING)
                .patternLine(" i ")
                .patternLine("p/p")
                .patternLine(" i ")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.SMALL_BATTERY)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('/', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine(" i ")
                .patternLine("r/r")
                .patternLine("iii")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.MEDIUM_BATTERY)
                .key('i', Tags.Items.INGOTS_GOLD)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('/', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine("rir")
                .patternLine("/ /")
                .patternLine("iii")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.LARGE_BATTERY)
                .key('i', ModTags.Items.INGOTS_CRIMSON_STEEL)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('/', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine("iri")
                .patternLine("/r/")
                .patternLine("iri")
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(SmdItems.EXTREME_BATTERY)
                .key('c', Items.POPPED_CHORUS_FRUIT)
                .key('i', ModTags.Items.INGOTS_AZURE_ELECTRUM)
                .key('e', Tags.Items.ENDER_PEARLS)
                .key('/', SmdTags.Items.INGOTS_REDSTONE_ALLOY)
                .patternLine("ici")
                .patternLine("/e/")
                .patternLine("iei")
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
