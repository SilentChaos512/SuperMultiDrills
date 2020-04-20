package net.silentchaos512.supermultidrills.crafting.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.silentchaos512.gear.api.parts.PartDataList;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.util.GearData;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapelessRecipe;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.DrillHeadItem;
import net.silentchaos512.supermultidrills.item.DrillItem;

public class DrillHeadSwapRecipe extends ExtendedShapelessRecipe {
    public static final ResourceLocation NAME = SuperMultiDrills.getId("drill_head_swap");
    public static final Serializer<DrillHeadSwapRecipe> SERIALIZER = Serializer.basic(DrillHeadSwapRecipe::new);

    private DrillHeadSwapRecipe(ShapelessRecipe recipe) {
        super(recipe);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        return getBaseRecipe().matches(inv, world);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        StackList list = StackList.from(inv);
        ItemStack drill = list.uniqueOfType(DrillItem.class);
        ItemStack head = list.uniqueOfType(DrillHeadItem.class);

        if (drill.isEmpty() || head.isEmpty()) {
            return ItemStack.EMPTY;
        }

        PartDataList drillParts = GearData.getConstructionParts(drill);
        PartDataList headParts = GearData.getConstructionParts(head);

        drillParts.removeIf(p -> p.getType() == PartType.MAIN);
        drillParts.addAll(headParts);

        ItemStack result = drill.copy();
        GearData.writeConstructionParts(result, drillParts);
        GearData.recalculateStats(result, ForgeHooks.getCraftingPlayer());
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for(int i = 0; i < list.size(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            PartDataList parts = GearData.getConstructionParts(stack);

            if (isDrillHead(parts)) {
                // Remove the swapped-in head
                list.set(i, ItemStack.EMPTY);
            } else {
                // Create a "new" drill head with the main parts of this drill
                ItemStack toolHead = StackList.from(inv).uniqueMatch(s -> isDrillHead(GearData.getConstructionParts(s)));
                ItemStack copy = toolHead.copy();
                parts.removeIf(p -> p.getType() != PartType.MAIN);
                GearData.writeConstructionParts(copy, parts);
                GearData.recalculateStats(copy, ForgeHooks.getCraftingPlayer());
                list.set(i, copy);
            }
        }

        return list;
    }

    private static boolean isDrillHead(PartDataList parts) {
        return parts.stream().allMatch(p -> {
            PartType type = p.getType();
            return type == PartType.MAIN || type == PartType.TIP || type == PartType.HIGHLIGHT;
        });
    }
}
