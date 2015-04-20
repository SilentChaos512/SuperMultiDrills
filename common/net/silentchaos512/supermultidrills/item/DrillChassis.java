package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class DrillChassis extends ItemSMD {

  public DrillChassis() {

    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_CHASSIS);
  }

  @Override
  public void addRecipes() {

    GameRegistry.addShapedRecipe(new ItemStack(this), " ir", "igi", "ii ", 'i', Items.iron_ingot,
        'r', ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1), 'g', Items.gold_ingot);
    for (int i = 0; i < 16; ++i) {
      String dye = ItemDye.field_150923_a[~i & 15];
      dye = "dye" + dye.substring(0, 1).toUpperCase() + dye.substring(1, dye.length());
      GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this, 1, i), new ItemStack(this,
          1, OreDictionary.WILDCARD_VALUE), dye));
    }
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    // TODO
    super.addInformation(stack, player, list, advanced);
  }

  @Override
  public int getColorFromItemStack(ItemStack stack, int pass) {

    return ItemDye.field_150922_c[~stack.getItemDamage() & 15];
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < 16; ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public IIcon getIconFromDamage(int meta) {

    return this.itemIcon;
  }

  @Override
  public void registerIcons(IIconRegister reg) {

    this.itemIcon = reg.registerIcon(Strings.RESOURCE_PREFIX + this.itemName);
  }
}
