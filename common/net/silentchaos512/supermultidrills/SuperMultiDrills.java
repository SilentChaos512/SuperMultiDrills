package net.silentchaos512.supermultidrills;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.supermultidrills.block.ModBlocks;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.core.handler.DrillsEventHandler;
import net.silentchaos512.supermultidrills.core.handler.DrillsForgeEventHandler;
import net.silentchaos512.supermultidrills.gui.GuiHandlerSuperMultiDrills;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.proxy.CommonProxy;
import net.silentchaos512.supermultidrills.registry.SRegistry;
import net.silentchaos512.supermultidrills.util.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

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

  @Instance(MOD_ID)
  public static SuperMultiDrills instance;

  @SidedProxy(clientSide = "net.silentchaos512.supermultidrills.proxy.ClientProxy", serverSide = "net.silentchaos512.supermultidrills.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    
    Config.init(event.getSuggestedConfigurationFile());
    
    ModBlocks.init();
    ModItems.init();
    
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerSuperMultiDrills());
  }
  
  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    // Look for compatible mods (just for recipes).
    foundEnderIO = Loader.isModLoaded("EnderIO");
    foundMekanism = Loader.isModLoaded("Mekanism");
    foundThermalFoundation = Loader.isModLoaded("ThermalFoundation");
    
    // Log found mods.
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
    
    // Event handlers
    FMLCommonHandler.instance().bus().register(new DrillsEventHandler());
    MinecraftForge.EVENT_BUS.register(new DrillsForgeEventHandler());
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

  }
  
  public static CreativeTabs creativeTab = new CreativeTabs("tabSuperMultiDrills") {
    
    @Override
    public Item getTabIconItem() {
      return ModItems.drill;
    }
  };
}
