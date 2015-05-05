package net.silentchaos512.supermultidrills;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.core.handler.DrillsEventHandler;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.proxy.CommonProxy;
import net.silentchaos512.supermultidrills.registry.SRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SuperMultiDrills.MOD_ID, name = SuperMultiDrills.MOD_NAME, version = SuperMultiDrills.VERSION_NUMBER)
public class SuperMultiDrills {

  public static final String MOD_ID = "SuperMultiDrills";
  public static final String MOD_NAME = "Super Multi-Drills";
  public static final String VERSION_NUMBER = "@VERSION@";
  
  public Random random = new Random();
  public boolean foundEnderIO = false;
  public boolean foundThermalExpansion = false;
  public boolean foundThermalFoundation = false;

  @Instance(MOD_ID)
  public static SuperMultiDrills instance;

  @SidedProxy(clientSide = "net.silentchaos512.supermultidrills.proxy.ClientProxy", serverSide = "net.silentchaos512.supermultidrills.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    
    Config.init(event.getSuggestedConfigurationFile());
    
    ModItems.init();
  }
  
  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    foundEnderIO = Loader.isModLoaded("EnderIO");
    foundThermalExpansion = Loader.isModLoaded("ThermalExpansion");
    foundThermalFoundation = Loader.isModLoaded("ThermalFoundation");
    
    SRegistry.addRecipesAndOreDictEntries();
    ModItems.initItemRecipes();
    
    MinecraftForge.EVENT_BUS.register(new DrillsEventHandler());
    FMLCommonHandler.instance().bus().register(new DrillsEventHandler());
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    
  }
  
  public static CreativeTabs creativeTab = new CreativeTabs("tabSuperMultiDrills") {
    
    @Override
    public Item getTabIconItem() {
      return Items.baked_potato;
    }
  };
}
