package net.silentchaos512.supermultidrills.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.silentchaos512.supermultidrills.item.ModItems;

public class ColorHandlers {

  public static final int TINT_INDEX_CHASSIS = 0;
  public static final int TINT_INDEX_HEAD = 1;

  public static void init() {

    ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

    itemColors.registerItemColorHandler(new IItemColor() {

      @Override
      public int getColorFromItemstack(ItemStack stack, int tintIndex) {

        return ModItems.drill.getColorFromItemStack(stack, tintIndex);
      }
    }, ModItems.drill);

    itemColors.registerItemColorHandler(new IItemColor() {

      @Override
      public int getColorFromItemstack(ItemStack stack, int tintIndex) {

        return ItemDye.DYE_COLORS[~stack.getItemDamage() & 15];
      }
    }, ModItems.drillChassis);

    itemColors.registerItemColorHandler(new IItemColor() {

      @Override
      public int getColorFromItemstack(ItemStack stack, int tintIndex) {

        return tintIndex == TINT_INDEX_HEAD ? ModItems.drillHead.getDrillMaterial(stack).getTint()
            : 0xFFFFFF;
      }
    }, ModItems.drillHead);
  }
}
