package net.silentchaos512.supermultidrills.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.registry.IRegisterModels;
import net.silentchaos512.supermultidrills.util.LogHelper;

public class DrillChassis extends ItemSMD implements IRegisterModels {

  public static final int PASS_CHASSIS = 0;
  public static final int PASS_BATTERY_GAUGE = 1;
  public static final int NUM_RENDER_PASSES = 2;
  public static final int BATTERY_GAUGE_LEVELS = 4;
  public static final int SUB_TYPE_COUNT = EnumDyeColor.values().length;

  private ModelResourceLocation[] models = new ModelResourceLocation[BATTERY_GAUGE_LEVELS];

  public DrillChassis() {

    super(SUB_TYPE_COUNT);
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
    for (int i = 0; i < SUB_TYPE_COUNT; ++i) {
      String dye = EnumDyeColor.values()[i & 15].getUnlocalizedName();
      dye = "dye" + dye.substring(0, 1).toUpperCase() + dye.substring(1, dye.length());
      GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this, 1, i),
          new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), dye));
    }
  }

  @Override
  public int getColorFromItemStack(ItemStack stack, int pass) {

    if (pass == PASS_CHASSIS) {
      return ItemDye.dyeColors[~stack.getItemDamage() & 15];
    } else {
      return 0xFFFFFF;
    }
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < SUB_TYPE_COUNT; ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[BATTERY_GAUGE_LEVELS];
    for (int i = 0; i < BATTERY_GAUGE_LEVELS; ++i) {
      result[i] = getFullName() + i;
    }
    return result;
  }

  @Override
  public void registerModels() {

    int i;
    String[] variants = getVariantNames();
    ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

    for (i = 0; i < models.length; ++i) {
      models[i] = new ModelResourceLocation(variants[i], "inventory");
    }
    // Register for actual items.
    for (i = 0; i < SUB_TYPE_COUNT; ++i) {
      mesher.register(this, i, models[0]);
    }
    // Register fakes (I feel like there's a better way?)
    for (; i < SUB_TYPE_COUNT + models.length; ++i) {
      mesher.register(this, i, models[i - SUB_TYPE_COUNT]);
    }
  }

  @Override
  public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {

    return models[0];
  }

  public ModelResourceLocation getModelForChargeLevel(int level) {

    return models[level];
  }
}
