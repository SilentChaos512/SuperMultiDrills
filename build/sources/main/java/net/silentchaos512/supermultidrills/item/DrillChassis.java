package net.silentchaos512.supermultidrills.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;
import scala.reflect.internal.Trees.Super;

public class DrillChassis extends ItemSL {

  public static final int PASS_CHASSIS = 0;
  public static final int PASS_BATTERY_GAUGE = 1;
  public static final int NUM_RENDER_PASSES = 2;
  public static final int BATTERY_GAUGE_LEVELS = 4;
  public static final int SUB_TYPE_COUNT = EnumDyeColor.values().length;

  private ModelResourceLocation[] models;

  public DrillChassis() {

    super(SUB_TYPE_COUNT, SuperMultiDrills.MOD_ID, Names.DRILL_CHASSIS);
  }

  @Override
  public void addRecipes() {

    final boolean funOres = SuperMultiDrills.instance.foundFunOres;

    // Basic recipe
    //@formatter:off
    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), "bir", "igi", "ii ",
        'i', funOres ? "plateIron" : "ingotIron",
        'g', funOres ? "plateGold" : "ingotGold",
        'r', ModItems.craftingItem.getStack(Names.HEAVY_MAGNETIC_ROD),
        'b', ModItems.craftingItem.getStack(Names.BATTERY_GAUGE)));
    //@formatter:on

    // Recolor recipes
    for (int i = 0; i < SUB_TYPE_COUNT; ++i) {
      String dye = EnumDyeColor.values()[i & 15].getUnlocalizedName();
      dye = "dye" + dye.substring(0, 1).toUpperCase() + dye.substring(1, dye.length());
      GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this, 1, i),
          new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), dye));
    }
  }

//  @Override
//  public int getColorFromItemStack(ItemStack stack, int pass) {
//
//    if (pass == PASS_CHASSIS) {
//      return ItemDye.dyeColors[~stack.getItemDamage() & 15];
//    } else {
//      return 0xFFFFFF;
//    }
//  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < SUB_TYPE_COUNT; ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> result = Lists.newArrayList();
    for (int i = 0; i < BATTERY_GAUGE_LEVELS; ++i)
      result.add(new ModelResourceLocation(getFullName() + i, "inventory"));
    return result;
  }

  @Override
  public boolean registerModels() {

    models = new ModelResourceLocation[BATTERY_GAUGE_LEVELS];

    int i;
    List<ModelResourceLocation> variants = getVariants();
    ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

    for (i = 0; i < models.length; ++i) {
      models[i] = variants.get(i);
    }
    // Register for actual items.
    for (i = 0; i < SUB_TYPE_COUNT; ++i) {
      mesher.register(this, i, models[0]);
    }
    // Register fakes (I feel like there's a better way?)
    for (; i < SUB_TYPE_COUNT + models.length; ++i) {
      mesher.register(this, i, models[i - SUB_TYPE_COUNT]);
    }

    return true;
  }

//  @Override
//  public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
//
//    return models[0];
//  }

  public ModelResourceLocation getModelForChargeLevel(int level) {

    return models[level];
  }
}
