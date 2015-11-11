package net.silentchaos512.supermultidrills.item;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.util.LocalizationHelper;

public class DrillUpgrade extends ItemSMD {

  public static final String[] NAMES = { Names.UPGRADE_SAW, Names.UPGRADE_SPEED, Names.UPGRADE_SILK,
      Names.UPGRADE_FORTUNE, Names.UPGRADE_SHARPNESS, Names.UPGRADE_AREA_MINER };

  public DrillUpgrade() {

    icons = new IIcon[NAMES.length];
    this.setMaxDamage(0);
    this.setMaxStackSize(64);
    this.setHasSubtypes(true);
    this.setUnlocalizedName(Names.DRILL_UPGRADE);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    if (stack.getItemDamage() < 0 || stack.getItemDamage() >= NAMES.length) {
      return;
    }

    String itemName = NAMES[stack.getItemDamage()];
    int i = 1;
    String s = LocalizationHelper.getItemDescription(itemName, i);
    while (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, i)) && i < 8) {
      list.add(EnumChatFormatting.GREEN + s);
      s = LocalizationHelper.getItemDescription(itemName, ++i);
    }

    if (i == 1) {
      s = LocalizationHelper.getItemDescription(itemName, 0);
      if (!s.equals(LocalizationHelper.getItemDescriptionKey(itemName, 0))) {
        list.add(EnumChatFormatting.GREEN + LocalizationHelper.getItemDescription(itemName, 0));
      }
    }

    list.add(EnumChatFormatting.DARK_GRAY
        + LocalizationHelper.getItemDescription(Names.DRILL_UPGRADE, 0));
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);

    // Saw
    ItemStack saw = new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SAW));
    GameRegistry
        .addRecipe(new ShapedOreRecipe(saw, " i ", "imi", "mi ", 'i', "ingotIron", 'm', rod));

    // Speed
    ItemStack speed = new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SPEED));
    // GameRegistry.addRecipe(new ShapedOreRecipe(speed, " m ", "grg", " m ", 'm', rod, 'g',
    // "ingotGold", 'r', "dustRedstone"));
    GameRegistry.addRecipe(
        new ShapedOreRecipe(speed, "rrr", "mgm", 'r', "blockRedstone", 'm', rod, 'g', "ingotGold"));

    // Silk
    ItemStack silk = new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_SILK));
    GameRegistry.addRecipe(
        new ShapedOreRecipe(silk, "eee", "rgr", 'e', "gemEmerald", 'r', rod, 'g', "ingotGold"));

    // Fortune
    ItemStack fortune = new ItemStack(this, 1, this.getMetaForName(Names.UPGRADE_FORTUNE));
    GameRegistry.addRecipe(
        new ShapedOreRecipe(fortune, "ddd", "rgr", 'd', "gemDiamond", 'r', rod, 'g', "ingotGold"));

    // Sharpness
    ItemStack sharpness = new ItemStack(this, 1, getMetaForName(Names.UPGRADE_SHARPNESS));
    GameRegistry.addRecipe(new ShapedOreRecipe(sharpness, "qqq", "rgr", 'q', "blockQuartz", 'r',
        rod, 'g', "ingotGold"));
    
    // Area Miner
    // TODO
  }

  public int getMetaForName(String name) {

    for (int i = 0; i < NAMES.length; ++i) {
      if (NAMES[i].equals(name)) {
        return i;
      }
    }
    return -1;
  }

  public ItemStack getStack(String name, int count) {

    for (int i = 0; i < NAMES.length; ++i) {
      if (NAMES[i].equals(name)) {
        return new ItemStack(this, count, i);
      }
    }
    return null;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    int meta = stack.getItemDamage();
    if (meta >= NAMES.length) {
      return super.getUnlocalizedName(stack);
    }
    return getUnlocalizedName(NAMES[meta]);
  }

  @Override
  public void registerIcons(IIconRegister iconRegister) {

    for (int i = 0; i < NAMES.length; ++i) {
      icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + NAMES[i]);
    }
  }
}
