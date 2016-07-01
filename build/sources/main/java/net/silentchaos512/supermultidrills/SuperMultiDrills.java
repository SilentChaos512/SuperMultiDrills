package net.silentchaos512.supermultidrills;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.LogHelper;
import net.silentchaos512.supermultidrills.block.ModBlocks;
import net.silentchaos512.supermultidrills.compat.jei.SuperMultiDrillsPlugin;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.proxy.DrillsCommonProxy;

@Mod(modid = SuperMultiDrills.MOD_ID, name = SuperMultiDrills.MOD_NAME, version = SuperMultiDrills.VERSION_NUMBER)
public class SuperMultiDrills {

  public static final String MOD_ID = "SuperMultiDrills";
  public static final String MOD_NAME = "Super Multi-Drills";
  public static final String VERSION_NUMBER = "1.4.0";
  public static final String DEPENDENCIES = "required-after:Forge@[12.16.1.1904,);required-after:SilentLib;";
  public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";

  public static Random random = new Random();
  public static LogHelper logHelper = new LogHelper(MOD_NAME);
  public static LocalizationHelper localizationHelper;
  
  public static SRegistry registry = new SRegistry(MOD_ID) {

    @Override
    public Block registerBlock(Block block, String key, ItemBlock itemBlock) {

      block.setCreativeTab(creativeTab);
      return super.registerBlock(block, key, itemBlock);
    }

    @Override
    public Item registerItem(Item item, String key) {

      item.setCreativeTab(creativeTab);
      return super.registerItem(item, key);
    }
  };

  public boolean foundEnderIO = false;
  public boolean foundThermalFoundation = false;
  public boolean foundMekanism = false;
  public boolean foundFunOres = false;
  public boolean foundWit = false;
  public int lastDrillMaterialMeta = 0;

  @Instance(MOD_ID)
  public static SuperMultiDrills instance;

  @SidedProxy(clientSide = "net.silentchaos512.supermultidrills.proxy.DrillsClientProxy", serverSide = "net.silentchaos512.supermultidrills.proxy.DrillsCommonProxy")
  public static DrillsCommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    localizationHelper = new LocalizationHelper(MOD_ID).setReplaceAmpersand(true);
    Config.init(event.getSuggestedConfigurationFile());

    ModBlocks.init(registry);
    ModItems.init(registry);

    proxy.preInit(registry);

    // NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerSuperMultiDrills());
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {

    // Look for compatible mods (just for recipes).
    foundFunOres = Loader.isModLoaded("FunOres");
    foundEnderIO = Loader.isModLoaded("EnderIO");
    foundMekanism = Loader.isModLoaded("Mekanism");
    foundThermalFoundation = Loader.isModLoaded("ThermalFoundation");
    foundWit = Loader.isModLoaded("WIT");

    // Log found mods.
    if (foundFunOres)
      logHelper.info("Found Fun Ores!");
    if (foundEnderIO)
      logHelper.info("Found Ender IO!");
    if (foundMekanism)
      logHelper.info("Found Mekanism!");
    if (foundThermalFoundation)
      logHelper.info("Found Thermal Foundation!");
    if (foundWit)
      logHelper.info("Found WIT!");

    // Recipes
    ModItems.initItemRecipes(registry);

    proxy.init(registry);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

    if (event.getSide() == Side.CLIENT && Loader.isModLoaded("JustEnoughItems")) {
      SuperMultiDrillsPlugin.doItemBlacklisting();
    }
  }

  public static CreativeTabs creativeTab = new CreativeTabs("tabSuperMultiDrills") {

    @Override
    public Item getTabIconItem() {

      return ModItems.drill;
    }
  };
}
