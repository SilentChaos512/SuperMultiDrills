package net.silentchaos512.supermultidrills.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.lib.item.ItemNamedSubtypesSorted;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;

public class DrillUpgrade extends ItemNamedSubtypesSorted {

  public static final String[] NAMES = { Names.UPGRADE_SAW, Names.UPGRADE_SPEED, Names.UPGRADE_SILK,
      Names.UPGRADE_FORTUNE, Names.UPGRADE_SHARPNESS, Names.UPGRADE_AREA_MINER,
      Names.UPGRADE_GRAVITON_GENERATOR };

  public ItemStack saw = getStack(Names.UPGRADE_SAW);
  public ItemStack speed = getStack(Names.UPGRADE_SPEED);
  public ItemStack silk = getStack(Names.UPGRADE_SILK);
  public ItemStack fortune = getStack(Names.UPGRADE_FORTUNE);
  public ItemStack sharpness = getStack(Names.UPGRADE_SHARPNESS);
  public ItemStack areaMiner = getStack(Names.UPGRADE_AREA_MINER);
  public ItemStack gravitonGenerator = getStack(Names.UPGRADE_GRAVITON_GENERATOR);

  public DrillUpgrade() {

    super(NAMES, NAMES, SuperMultiDrills.MOD_ID, Names.DRILL_UPGRADE);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    if (stack.getItemDamage() < 0 || stack.getItemDamage() >= NAMES.length) {
      return;
    }

    LocalizationHelper loc = SuperMultiDrills.localizationHelper;
    String upgradeName = NAMES[stack.getItemDamage()];

    for (String line : loc.getItemDescriptionLines(upgradeName))
      list.add(TextFormatting.GREEN + line);
    list.add(TextFormatting.DARK_GRAY + loc.getItemSubText(Names.DRILL_UPGRADE, "desc"));
  }

  @Override
  public void addRecipes() {

    ItemStack rod = ModItems.craftingItem.getStack(Names.MAGNETIC_ROD, 1);
    String iron = "ingotIron";
    String gold = "ingotGold";
    String diamond = "gemDiamond";
    String emerald = "gemEmerald";
    String glass = "blockGlass";

    // Saw
    ItemStack saw = new ItemStack(this, 1, this.getMetaFor(Names.UPGRADE_SAW));
    GameRegistry.addRecipe(new ShapedOreRecipe(saw, " i ", "imi", "mi ", 'i', iron, 'm', rod));

    // Speed
    ItemStack speed = new ItemStack(this, 1, this.getMetaFor(Names.UPGRADE_SPEED));
    GameRegistry.addRecipe(
        new ShapedOreRecipe(speed, "rrr", "mgm", 'r', "blockRedstone", 'm', rod, 'g', gold));

    // Silk
    ItemStack silk = new ItemStack(this, 1, this.getMetaFor(Names.UPGRADE_SILK));
    GameRegistry
        .addRecipe(new ShapedOreRecipe(silk, "eee", "rgr", 'e', emerald, 'r', rod, 'g', gold));

    // Fortune
    ItemStack fortune = new ItemStack(this, 1, this.getMetaFor(Names.UPGRADE_FORTUNE));
    GameRegistry
        .addRecipe(new ShapedOreRecipe(fortune, "ddd", "rgr", 'd', diamond, 'r', rod, 'g', gold));

    // Sharpness
    ItemStack sharpness = new ItemStack(this, 1, getMetaFor(Names.UPGRADE_SHARPNESS));
    GameRegistry.addRecipe(
        new ShapedOreRecipe(sharpness, "qqq", "rgr", 'q', "blockQuartz", 'r', rod, 'g', gold));

    // Area Miner
    ItemStack areaMiner = new ItemStack(this, 1, getMetaFor(Names.UPGRADE_AREA_MINER));
    GameRegistry.addRecipe(new ShapedOreRecipe(areaMiner, "oto", "oto", "rgr", 'o', "obsidian", 't',
        Blocks.TNT, 'r', rod, 'g', gold));

    // Graviton Generator
    ItemStack gravitonGenerator = new ItemStack(this, 1,
        getMetaFor(Names.UPGRADE_GRAVITON_GENERATOR));
    GameRegistry.addRecipe(new ShapedOreRecipe(gravitonGenerator, "ele", "nln", "rgr", 'e', emerald,
        'l', glass, 'n', Items.NETHER_STAR, 'r', rod, 'g', gold));
  }
}
