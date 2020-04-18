package net.silentchaos512.supermultidrills.crafting.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapelessRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.item.DrillHeadItem;
import net.silentchaos512.supermultidrills.item.DrillItem;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrillRecipe extends ExtendedShapelessRecipe {
    public static final ResourceLocation NAME = SuperMultiDrills.getId("drill_crafting");
    public static final Serializer<DrillRecipe> SERIALIZER = Serializer.basic(DrillRecipe::new);

    private DrillRecipe(ShapelessRecipe recipe) {
        super(recipe);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return this.getBaseRecipe().matches(inv, worldIn)
                && getParts(inv).stream().allMatch(part -> part.getPart().isCraftingAllowed(DrillItem.GEAR_TYPE));
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return ModItems.drill.construct(getParts(inv));
    }

    private static Collection<PartData> getParts(IInventory inv) {
        return StackList.from(inv).stream()
                .flatMap(stack -> {
                    if (stack.getItem() instanceof DrillHeadItem) {
                        return GearData.getConstructionParts(stack).stream();
                    }
                    return Stream.of(PartData.from(stack));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
