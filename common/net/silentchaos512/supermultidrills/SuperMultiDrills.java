package net.silentchaos512.supermultidrills;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.lib.creativetab.CreativeTabSL;
import net.silentchaos512.lib.registry.MC10IdRemapper;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.LogHelper;
import net.silentchaos512.supermultidrills.block.ModBlocks;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.proxy.DrillsCommonProxy;
import net.silentchaos512.supermultidrills.recipe.ModRecipes;

@Mod(modid = SuperMultiDrills.MOD_ID, name = SuperMultiDrills.MOD_NAME, version = SuperMultiDrills.VERSION_NUMBER, acceptedMinecraftVersions = SuperMultiDrills.ACCEPTED_MC_VERSIONS, dependencies = SuperMultiDrills.DEPENDENCIES)
public class SuperMultiDrills {

  public static final String MOD_ID = "supermultidrills";
  public static final String MOD_NAME = "Super Multi-Drills";
  public static final int BUILD_NUM = 0;
  public static final String VERSION_NUMBER = "@VERSION@";
  public static final String VERSION_SILENTLIB = "SL_VERSION";
  public static final String DEPENDENCIES = "required-after:silentlib@[" + VERSION_SILENTLIB + ",);";
  public static final String ACCEPTED_MC_VERSIONS = "[1.10.2,1.11.2]";
  public static final String RESOURCE_PREFIX = MOD_ID + ":";

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
    foundFunOres = Loader.isModLoaded("funores") || Loader.isModLoaded("FunOres");
    foundEnderIO = Loader.isModLoaded("enderio") || Loader.isModLoaded("EnderIO");
    foundMekanism = Loader.isModLoaded("mekanism") || Loader.isModLoaded("Mekanism");
    foundWit = Loader.isModLoaded("WIT");

    // Log found mods.
    if (foundFunOres)
      logHelper.info("Found Fun Ores!");
    if (foundEnderIO)
      logHelper.info("Found Ender IO!");
    if (foundMekanism)
      logHelper.info("Found Mekanism!");
    if (foundWit)
      logHelper.info("Found WIT!");

    // Recipes
    ModRecipes.init(registry);

    proxy.init(registry);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

  }

  @EventHandler
  public void onMissingMapping(FMLMissingMappingsEvent event) {

    for (FMLMissingMappingsEvent.MissingMapping mismap : event.get()) {
      MC10IdRemapper.remap(mismap);
    }
  }

  public static CreativeTabSL creativeTab = new CreativeTabSL("tabSuperMultiDrills") {

    @Override
    protected ItemStack getStack() {

      return new ItemStack(ModItems.drill);
    }
  };
}
