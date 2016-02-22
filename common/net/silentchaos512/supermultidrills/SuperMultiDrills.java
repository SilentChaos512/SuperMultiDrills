package net.silentchaos512.supermultidrills;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.supermultidrills.block.ModBlocks;
import net.silentchaos512.supermultidrills.compat.jei.SuperMultiDrillsPlugin;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.core.handler.DrillsEventHandler;
import net.silentchaos512.supermultidrills.core.handler.DrillsForgeEventHandler;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.proxy.CommonProxy;
import net.silentchaos512.supermultidrills.registry.SRegistry;
import net.silentchaos512.supermultidrills.util.LogHelper;

@Mod(modid = SuperMultiDrills.MOD_ID, name = SuperMultiDrills.MOD_NAME, version = SuperMultiDrills.VERSION_NUMBER)
public class SuperMultiDrills {

  public static final String MOD_ID = "SuperMultiDrills";
  public static final String MOD_NAME = "Super Multi-Drills";
  public static final String VERSION_NUMBER = "@VERSION@";
  
  public Random random = new Random();
  public static Logger logger = LogManager.getLogger(MOD_NAME);
  
  public boolean foundEnderIO = false;
  public boolean foundThermalFoundation = false;
  public boolean foundMekanism = false;
  public boolean foundFunOres = false;

  @Instance(MOD_ID)
  public static SuperMultiDrills instance;

  @SidedProxy(clientSide = "net.silentchaos512.supermultidrills.proxy.ClientProxy", serverSide = "net.silentchaos512.supermultidrills.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    
    Config.init(event.getSuggestedConfigurationFile());
    
    ModBlocks.init();
    ModItems.init();

    proxy.preInit();
    
//    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerSuperMultiDrills());
  }
  
  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    // Look for compatible mods (just for recipes).
    foundFunOres = Loader.isModLoaded("FunOres");
    foundEnderIO = Loader.isModLoaded("EnderIO");
    foundMekanism = Loader.isModLoaded("Mekanism");
    foundThermalFoundation = Loader.isModLoaded("ThermalFoundation");
    
    // Log found mods.
    if (foundFunOres) {
      LogHelper.info("Found Fun Ores!");
    }
    if (foundEnderIO) {
      LogHelper.info("Found Ender IO!");
    }
    if (foundMekanism) {
      LogHelper.info("Found Mekanism!");
    }
    if (foundThermalFoundation) {
      LogHelper.info("Found Thermal Foundation!");
    }
    
    // Recipes
    SRegistry.addRecipesAndOreDictEntries();
    ModItems.initItemRecipes();

    proxy.init();
    
    // Event handlers
    FMLCommonHandler.instance().bus().register(new DrillsEventHandler());
    MinecraftForge.EVENT_BUS.register(new DrillsForgeEventHandler());
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

    SuperMultiDrillsPlugin.doItemBlacklisting();
  }
  
  public static CreativeTabs creativeTab = new CreativeTabs("tabSuperMultiDrills") {
    
    @Override
    public Item getTabIconItem() {
      return ModItems.drill;
    }
  };
}
