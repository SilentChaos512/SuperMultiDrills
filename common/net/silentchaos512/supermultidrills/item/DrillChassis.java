package net.silentchaos512.supermultidrills.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class DrillChassis extends ItemSMD {

  public static final int PASS_CHASSIS = 0;
  public static final int PASS_BATTERY_GAUGE = 1;
  public static final int NUM_RENDER_PASSES = 2;

  protected IIcon iconChassis;
  protected IIcon iconBatteryGauge;

  public DrillChassis() {

    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_CHASSIS);
  }

  @Override
  public void addRecipes() {

    // Basic recipe
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), " ir", "igi", "ii ", 'i',
        "ingotIron", 'r', ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1), 'g', "ingotGold"));

    // Recolor recipes
    for (int i = 0; i < 16; ++i) {
      String dye = ItemDye.field_150923_a[~i & 15];
      dye = "dye" + dye.substring(0, 1).toUpperCase() + dye.substring(1, dye.length());
      GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this, 1, i),
          new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), dye));
    }
  }

  @Override
  public int getColorFromItemStack(ItemStack stack, int pass) {

    if (pass == PASS_CHASSIS) {
      return ItemDye.field_150922_c[~stack.getItemDamage() & 15];
    } else {
      return 0xFFFFFF;
    }
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < 16; ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public IIcon getIcon(ItemStack stack, int pass) {

    if (pass == PASS_CHASSIS) {
      return itemIcon;
    } else if (pass == PASS_BATTERY_GAUGE) {
      return ModItems.drill.iconBatteryGauge[0];
    } else {
      LogHelper.debug("Unknown render pass for chassis! Pass " + pass);
      return null;
    }
  }

  @Override
  public int getRenderPasses(int meta) {

    return NUM_RENDER_PASSES;
  }

  @Override
  public boolean requiresMultipleRenderPasses() {

    return true;
  }
}
