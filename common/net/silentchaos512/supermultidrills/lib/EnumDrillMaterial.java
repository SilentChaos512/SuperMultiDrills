package net.silentchaos512.supermultidrills.lib;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.udojava.evalex.Expression;

import net.minecraft.item.ItemStack;
import net.silentchaos512.lib.util.StackHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.item.ModItems;

public enum EnumDrillMaterial {

  //@formatter:off
  // Vanilla
  IRON(EnumDrillMaterial.GROUP_VANILLA, 250, 6.0f, 2.0f, "ingotIron"),
  GOLD(EnumDrillMaterial.GROUP_VANILLA, 32, 12.0f, 0.0f, "ingotGold"),
  DIAMOND(EnumDrillMaterial.GROUP_VANILLA, 1561, 8.0f, 3.0f, "gemDiamond"),

  // Fun Ores
  COPPER(EnumDrillMaterial.GROUP_FUN_ORES, 175, 4.0f, 0.5f, "ingotCopper"),
  TIN(EnumDrillMaterial.GROUP_FUN_ORES, 240, 5.0f, 1.0f, "ingotTin"),
  SILVER(EnumDrillMaterial.GROUP_FUN_ORES, 200, 6.0f, 1.5f, "ingotSilver"),
  LEAD(EnumDrillMaterial.GROUP_FUN_ORES, 150, 5.0f, 1.0f, "ingotLead"),
  NICKEL(EnumDrillMaterial.GROUP_FUN_ORES, 300, 6.5f, 2.5f, "ingotNickel"),
  PLATINUM(EnumDrillMaterial.GROUP_FUN_ORES, 900, 12.0f, 4.0f, "ingotPlatinum"),
  ALUMINIUM(EnumDrillMaterial.GROUP_FUN_ORES, 300, 6.0f, 2.5f, "ingotAluminium"),
  ZINC(EnumDrillMaterial.GROUP_FUN_ORES, 200, 3.0f, 1.5f, "ingotZinc"),
  TITANIUM(EnumDrillMaterial.GROUP_FUN_ORES, 1600, 8.0f, 6.0f, "ingotTitanium"),
  BRONZE(EnumDrillMaterial.GROUP_FUN_ORES, 500, 6.0f, 2.0f, "ingotBronze"),
  STEEL(EnumDrillMaterial.GROUP_FUN_ORES, 1000, 8.0f, 4.0f, "ingotSteel"),
  INVAR(EnumDrillMaterial.GROUP_FUN_ORES, 450, 7.0f, 3.0f, "ingotInvar"),
  ELECTRUM(EnumDrillMaterial.GROUP_FUN_ORES, 100, 14.0f, 0.5f, "ingotElectrum"),
  ENDERIUM(EnumDrillMaterial.GROUP_FUN_ORES, 1100, 18.0f, 10.0f, "ingotEnderium"),
  PRISMARINIUM(EnumDrillMaterial.GROUP_FUN_ORES, 1400, 20.0f, 6.0f, "ingotPrismarinium"),

  // Ender IO (aside from Dark Steel, these numbers are made up).
  DARK_STEEL(EnumDrillMaterial.GROUP_ENDER_IO, 1561, 7.0f, 1.0f, "ingotDarkSteel"),
  ELECTRICAL_STEEL(EnumDrillMaterial.GROUP_ENDER_IO, 300, 6.0f, 2.0f, "ingotElectricalSteel"),
  CONDUCTIVE_IRON(EnumDrillMaterial.GROUP_ENDER_IO, 100, 8.0f, 1.5f, "ingotConductiveIron"),
  ENERGETIC_ALLOY(EnumDrillMaterial.GROUP_ENDER_IO, 150, 11.0f, 2.0f, "ingotEnergeticAlloy"),
  VIBRANT_ALLOY(EnumDrillMaterial.GROUP_ENDER_IO, 400, 14.0f, 3.0f, "ingotPhasedGold"),
  PULSATING_IRON(EnumDrillMaterial.GROUP_ENDER_IO, 850, 6.0f, 2.0f, "ingotPhasedIron"),
  SOULARIUM(EnumDrillMaterial.GROUP_ENDER_IO, 1865, 4.0f, 1.0f, "ingotSoularium"),

  // Silent's Gems
  // Overworld
  RUBY(EnumDrillMaterial.GROUP_SILENT_GEMS,       512,  8.0f, 5.0f, "gemRuby"),
  GARNET(EnumDrillMaterial.GROUP_SILENT_GEMS,     384,  7.0f, 4.0f, "gemGarnet"),
  TOPAZ(EnumDrillMaterial.GROUP_SILENT_GEMS,      384,  9.0f, 4.0f, "gemTopaz"),
  AMBER(EnumDrillMaterial.GROUP_SILENT_GEMS,      128,  5.0f, 2.0f, "gemAmber"),
  HELIODOR(EnumDrillMaterial.GROUP_SILENT_GEMS,   256, 11.0f, 4.0f, "gemHeliodor"),
  PERIDOT(EnumDrillMaterial.GROUP_SILENT_GEMS,    256,  6.0f, 4.0f, "gemPeridot"),
  BERYL(EnumDrillMaterial.GROUP_SILENT_GEMS,      384,  9.0f, 4.0f, "gemBeryl"),
  INDICOLITE(EnumDrillMaterial.GROUP_SILENT_GEMS, 384, 10.0f, 2.0f, "gemIndicolite"),
  AQUAMARINE(EnumDrillMaterial.GROUP_SILENT_GEMS, 256,  9.0f, 3.0f, "gemAquamarine"),
  SAPPHIRE(EnumDrillMaterial.GROUP_SILENT_GEMS,   512,  8.0f, 4.0f, "gemSapphire"),
  IOLITE(EnumDrillMaterial.GROUP_SILENT_GEMS,     512,  6.0f, 2.0f, "gemIolite"),
  AMETHYST(EnumDrillMaterial.GROUP_SILENT_GEMS,   256,  7.0f, 3.0f, "gemAmethyst"),
  AGATE(EnumDrillMaterial.GROUP_SILENT_GEMS,      192,  8.0f, 3.0f, "gemAgate"),
  MORGANITE(EnumDrillMaterial.GROUP_SILENT_GEMS,  256,  9.0f, 4.0f, "gemMorganite"),
  ONYX(EnumDrillMaterial.GROUP_SILENT_GEMS,       128,  8.0f, 6.0f, "gemOnyx"),
  OPAL(EnumDrillMaterial.GROUP_SILENT_GEMS,       192,  8.0f, 3.0f, "gemOpal"),
  // Nether
  CARNELIAN(EnumDrillMaterial.GROUP_SILENT_GEMS,       256,  9.0f, 2.0f, "gemCarnelian", 0x999999),
  SPINEL(EnumDrillMaterial.GROUP_SILENT_GEMS,          512,  8.0f, 4.0f, "gemSpinel", 0x999999),
  CITRINE(EnumDrillMaterial.GROUP_SILENT_GEMS,         384, 10.0f, 3.0f, "gemCitrine", 0x999999),
  JASPER(EnumDrillMaterial.GROUP_SILENT_GEMS,          256,  7.0f, 3.0f, "gemJasper", 0x999999),
  GOLDEN_BERYL(EnumDrillMaterial.GROUP_SILENT_GEMS,    384, 10.0f, 2.0f, "gemGoldenBeryl", 0x999999),
  MOLDAVITE(EnumDrillMaterial.GROUP_SILENT_GEMS,       192,  6.0f, 5.0f, "gemMoldavite", 0x999999),
  MALACHITE(EnumDrillMaterial.GROUP_SILENT_GEMS,       128,  8.0f, 4.0f, "gemMalachite", 0x999999),
  TURQUOISE(EnumDrillMaterial.GROUP_SILENT_GEMS,       256,  9.0f, 3.0f, "gemTurquoise", 0x999999),
  MOONSTONE(EnumDrillMaterial.GROUP_SILENT_GEMS,       256,  9.0f, 3.0f, "gemMoonstone", 0x999999),
  BLUE_TOPAZ(EnumDrillMaterial.GROUP_SILENT_GEMS,      512,  9.0f, 3.0f, "gemBlueTopaz", 0x999999),
  TANZANITE(EnumDrillMaterial.GROUP_SILENT_GEMS,       384,  6.0f, 3.0f, "gemTanzanite", 0x999999),
  VIOLET_SAPPHIRE(EnumDrillMaterial.GROUP_SILENT_GEMS, 512,  7.0f, 4.0f, "gemVioletSapphire", 0x999999),
  LEPIDOLITE(EnumDrillMaterial.GROUP_SILENT_GEMS,      128,  5.0f, 3.0f, "gemLepidolite", 0x999999),
  AMETRINE(EnumDrillMaterial.GROUP_SILENT_GEMS,        384,  8.0f, 4.0f, "gemAmetrine", 0x999999),
  BLACK_DIAMOND(EnumDrillMaterial.GROUP_SILENT_GEMS,   768,  9.0f, 3.0f, "gemBlackDiamond", 0x999999),
  ALEXANDRITE(EnumDrillMaterial.GROUP_SILENT_GEMS,     512,  8.0f, 3.0f, "gemAlexandrite", 0x999999),
  //Super Overworld
  RUBY_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       2048, 12.0f, 7.0f, "gemRubySuper"),
  GARNET_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,     1536, 11.0f, 6.0f, "gemGarnetSuper"),
  TOPAZ_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,      1536, 13.0f, 6.0f, "gemTopazSuper"),
  AMBER_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       512,  9.0f, 4.0f, "gemAmberSuper"),
  HELIODOR_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,   1024, 15.0f, 6.0f, "gemHeliodorSuper"),
  PERIDOT_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,    1024, 10.0f, 6.0f, "gemPeridotSuper"),
  BERYL_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,      1536, 13.0f, 6.0f, "gemBerylSuper"),
  INDICOLITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS, 1536, 14.0f, 4.0f, "gemIndicoliteSuper"),
  AQUAMARINE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 13.0f, 5.0f, "gemAquamarineSuper"),
  SAPPHIRE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,   2048, 12.0f, 6.0f, "gemSapphireSuper"),
  IOLITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,     2048, 10.0f, 4.0f, "gemIoliteSuper"),
  AMETHYST_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,   1024, 11.0f, 5.0f, "gemAmethystSuper"),
  AGATE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       768, 12.0f, 5.0f, "gemAgateSuper"),
  MORGANITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,  1024, 13.0f, 6.0f, "gemMorganiteSuper"),
  ONYX_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,        512, 12.0f, 8.0f, "gemOnyxSuper"),
  OPAL_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,        768, 12.0f, 5.0f, "gemOpalSuper"),
  // Super Nether
  CARNELIAN_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       1024, 13.0f, 2.0f, "gemCarnelianSuper", 0x999999),
  SPINEL_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,          2048, 12.0f, 4.0f, "gemSpinelSuper", 0x999999),
  CITRINE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,         1536, 14.0f, 3.0f, "gemCitrineSuper", 0x999999),
  JASPER_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,          1024, 11.0f, 3.0f, "gemJasperSuper", 0x999999),
  GOLDEN_BERYL_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,    1536, 14.0f, 2.0f, "gemGoldenBerylSuper", 0x999999),
  MOLDAVITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,        768, 10.0f, 5.0f, "gemMoldaviteSuper", 0x999999),
  MALACHITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,        512, 12.0f, 4.0f, "gemMalachiteSuper", 0x999999),
  TURQUOISE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       1024, 13.0f, 3.0f, "gemTurquoiseSuper", 0x999999),
  MOONSTONE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       1024, 13.0f, 3.0f, "gemMoonstoneSuper", 0x999999),
  BLUE_TOPAZ_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,      2048, 13.0f, 3.0f, "gemBlueTopazSuper", 0x999999),
  TANZANITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       1536, 10.0f, 3.0f, "gemTanzaniteSuper", 0x999999),
  VIOLET_SAPPHIRE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS, 2048, 11.0f, 4.0f, "gemVioletSapphireSuper", 0x999999),
  LEPIDOLITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,       512,  9.0f, 3.0f, "gemLepidoliteSuper", 0x999999),
  AMETRINE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,        1536, 12.0f, 4.0f, "gemAmetrineSuper", 0x999999),
  BLACK_DIAMOND_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,   3072, 13.0f, 3.0f, "gemBlackDiamondSuper", 0x999999),
  ALEXANDRITE_SUPER(EnumDrillMaterial.GROUP_SILENT_GEMS,     2048, 12.0f, 3.0f, "gemAlexandriteSuper", 0x999999),
  // Other
  CRYSTALLIZED_CHAOS(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 25.0f, 7.0f, "gemChaosCrystallized"),

  // Tinker's Construct
  ALUMITE(EnumDrillMaterial.GROUP_TCONSTRUCT, 1400, 8.0f, 3.0f, "ingotAlumite"),
  COBALT(EnumDrillMaterial.GROUP_TCONSTRUCT, 1600, 14.0f, 3.0f, "ingotCobalt"),
  ARDITE(EnumDrillMaterial.GROUP_TCONSTRUCT, 1000, 8.0f, 3.0f, "ingotArdite"),
  MANYULLYN(EnumDrillMaterial.GROUP_TCONSTRUCT, 2400, 9.0f, 4.0f, "ingotManyullyn"),

  // Extra TiC
  FAIRY(EnumDrillMaterial.GROUP_TCONSTRUCT, 500, 7.5f, 1.0f, "ingotFairy"),
  POKEFENNIUM(EnumDrillMaterial.GROUP_TCONSTRUCT, 1000, 8.5f, 1.0f, "ingotPokefennium"),
  REDAURUM(EnumDrillMaterial.GROUP_TCONSTRUCT, 500, 7.5f, 1.0f, "ingotPokefennium"),

  // More Thermal Foundation. Skipping Mana-Infused, that's Mithril.
  // SIGNALUM(EnumDrillMaterial.GROUP_FUN_ORES, 800, 13.0f, 10.0f, "ingotSignalum"),
  // LUMIUM(EnumDrillMaterial.GROUP_FUN_ORES, 1500, 3.0f, 8.0f, "ingotLumium"),

  // Botania
  MANASTEEL(EnumDrillMaterial.GROUP_BOTANIA, 300, 6.2f, 1.0f, "ingotManasteel"),
  TERRASTEEL(EnumDrillMaterial.GROUP_BOTANIA, 2300, 9.0f, 2.0f, "ingotTerrasteel"),
  ELEMENTIUM(EnumDrillMaterial.GROUP_BOTANIA, 720, 6.2f, 1.0f, "ingotElvenElementium"),

  // Metallurgy 4
  HEPATIZON(EnumDrillMaterial.GROUP_METALLURGY, 300, 8.0f, 1.0f, "ingotHepatizon"),
  DAMASCUS_STEEL(EnumDrillMaterial.GROUP_METALLURGY, 500, 6.0f, 2.0f, "ingotDamascusSteel"),
  ANGMALLEN(EnumDrillMaterial.GROUP_METALLURGY, 300, 8.0f, 2.0f, "ingotAngmallen"),
  BRASS(EnumDrillMaterial.GROUP_METALLURGY, 15, 10.0f, 1.0f, "ingotBrass"),
  IGNATIUS(EnumDrillMaterial.GROUP_METALLURGY, 200, 4.0f, 2.0f, "ingotIgnatius"),
  SHADOW_IRON(EnumDrillMaterial.GROUP_METALLURGY, 300, 5.0f, 2.0f, "ingotShadowIron"),
  SHADOW_STEEL(EnumDrillMaterial.GROUP_METALLURGY, 400, 6.0f, 3.0f, "ingotShadowSteel"),
  MIDASIUM(EnumDrillMaterial.GROUP_METALLURGY, 100, 10.0f, 3.0f, "ingotMidasium"),
  VYROXERES(EnumDrillMaterial.GROUP_METALLURGY, 300, 7.0f, 3.0f, "ingotVyroxeres"),
  CERUCLASE(EnumDrillMaterial.GROUP_METALLURGY, 500, 7.0f, 3.0f, "ingotCeruclase"),
  INOLASHITE(EnumDrillMaterial.GROUP_METALLURGY, 900, 8.0f, 3.0f, "ingotInolashite"),
  KALENDRITE(EnumDrillMaterial.GROUP_METALLURGY, 1000, 8.0f, 3.0f, "ingotKalendrite"),
  AMORDRINE(EnumDrillMaterial.GROUP_METALLURGY, 500, 14.0f, 2.0f, "ingotAmordrine"),
  VULCANITE(EnumDrillMaterial.GROUP_METALLURGY, 1500, 10.0f, 3.0f, "ingotVulcanite"),
  SANGUINITE(EnumDrillMaterial.GROUP_METALLURGY, 1750, 12.0f, 4.0f, "ingotSanguinite"),
  PROMETHEUM(EnumDrillMaterial.GROUP_METALLURGY, 200, 4.0f, 1.0f, "ingotPrometheum"),
  DEEP_IRON(EnumDrillMaterial.GROUP_METALLURGY, 250, 6.0f, 2.0f, "ingotDeepIron"),
  BLACK_STEEL(EnumDrillMaterial.GROUP_METALLURGY, 500, 8.0f, 2.0f, "ingotBlackSteel"),
  OURECLASE(EnumDrillMaterial.GROUP_METALLURGY, 750, 8.0f, 2.0f, "ingotOureclase"),
  ASTRAL_SILVER(EnumDrillMaterial.GROUP_METALLURGY, 35, 12.0f, 1.0f, "ingotAstralSilver"),
  CARMOT(EnumDrillMaterial.GROUP_METALLURGY, 50, 12.0f, 1.0f, "ingotCarmot"),
  MITHRIL(EnumDrillMaterial.GROUP_METALLURGY, 1000, 9.0f, 3.0f, "ingotMithril"),
  QUICKSILVER(EnumDrillMaterial.GROUP_METALLURGY, 1100, 14.0f, 3.0f, "ingotQuicksilver"),
  HADEROTH(EnumDrillMaterial.GROUP_METALLURGY, 1250, 12.0f, 3.0f, "ingotHaderoth"),
  ORICHALCUM(EnumDrillMaterial.GROUP_METALLURGY, 1350, 9.0f, 3.0f, "ingotOrichalcum"),
  CELENEGIL(EnumDrillMaterial.GROUP_METALLURGY, 1600, 14.0f, 3.0f, "ingotCelenegil"),
  ADAMANTINE(EnumDrillMaterial.GROUP_METALLURGY, 1550, 10.0f, 4.0f, "ingotAdamantine"),
  ATLARUS(EnumDrillMaterial.GROUP_METALLURGY, 1750, 10.0f, 4.0f, "ingotAtlarus"),
  TARTARITE(EnumDrillMaterial.GROUP_METALLURGY, 3000, 14.0f, 5.0f, "ingotTartarite"),
  EXIMITE(EnumDrillMaterial.GROUP_METALLURGY, 1000, 8.0f, 3.0f, "ingotEximite"),
  DESICHALKOS(EnumDrillMaterial.GROUP_METALLURGY, 1800, 10.0f, 4.0f, "ingotDesichalkos");
  //@formatter:on

  // Group for sorting in NEI, since metadata might be a bit scattered.
  public static final String GROUP_VANILLA = "Vanilla";
  public static final String GROUP_FUN_ORES = "Fun Ores";
  public static final String GROUP_ENDER_IO = "Ender IO";
  public static final String GROUP_SILENT_GEMS = "Silent's Gems";
  public static final String GROUP_TCONSTRUCT = "Tinker's Construct";
  public static final String GROUP_BOTANIA = "Botania";
  public static final String GROUP_METALLURGY = "Metallurgy";
  public static final String[] GROUPS_ORDERED = new String[] { GROUP_VANILLA, GROUP_FUN_ORES,
      GROUP_ENDER_IO, GROUP_SILENT_GEMS, GROUP_BOTANIA, GROUP_TCONSTRUCT, GROUP_METALLURGY };

  private final int meta;
  private final int durability;
  private final float efficiency;
  private final float damage;
  private final String material;
  private final String group;
  private final int tint;

  private EnumDrillMaterial(String group, int durability, float efficiency, float damage,
      String material) {

    this(group, durability, efficiency, damage, material, 0xFFFFFF);
  }

  private EnumDrillMaterial(String group, int durability, float efficiency, float damage,
      String material, int tint) {

    this.meta = SuperMultiDrills.instance.lastDrillMaterialMeta++;
    this.group = group;
    this.durability = durability;
    this.efficiency = efficiency;
    this.damage = damage;
    this.material = material;
    this.tint = tint;
  }

  public int getMeta() {

    return meta;
  }

  public int getDurability() {

    return durability;
  }

  public float getEfficiency() {

    return efficiency;
  }

  public float getDamageVsEntity() {

    return damage;
  }

  public int getTint() {

    return tint;
  }

  public String getMaterial() {

    return material;
  }

  public String getMaterialName() {

    // Strip ingot/gem/etc, leaving just the material (ie, Iron, DarkSteel, Ruby)
    for (int i = 0; i < material.length(); ++i) {
      if (material.charAt(i) < 'a') {
        return material.substring(i);
      }
    }

    return material;
  }

  public String getGroup() {

    return this.group;
  }

  public float getCostPerHardness() {

    Expression exp = Config.energyCostExpression;
    exp.setVariable("durability", BigDecimal.valueOf(this.durability));
    exp.setVariable("efficiency", BigDecimal.valueOf(0));
    exp.setVariable("silk_touch", BigDecimal.valueOf(0));
    exp.setVariable("fortune", BigDecimal.valueOf(0));
    exp.setVariable("hardness", BigDecimal.valueOf(1));
    exp.setVariable("mining_speed", BigDecimal.valueOf(this.efficiency));
    exp.setVariable("motor_boost", BigDecimal.valueOf(1));

    float result = exp.eval().floatValue();
    if (result < 0.0f) {
      result = 0.0f; // Energy cost should be non-negative!
    }
    return result;
  }

  /**
   * Determines if a drill head can be crafted, ie there is some item that matches the ore dictionary entry of the
   * material.
   * 
   * @return
   */
  public boolean canCraftHead() {

    if (this == IRON || this == GOLD || this == DIAMOND) {
      return true;
    }

    return !StackHelper.getOres(material).isEmpty();
  }

  public ItemStack getHead() {

    for (int i = 0; i < values().length; ++i) {
      if (values()[i] == this) {
        return new ItemStack(ModItems.drillHead, 1, i);
      }
    }
    return null;
  }

  /**
   * Returns a list of all drill materials in the specified group.
   * 
   * @param group
   * @return
   */
  public static ArrayList<EnumDrillMaterial> getAllInGroup(String group) {

    ArrayList<EnumDrillMaterial> list = new ArrayList<EnumDrillMaterial>();

    for (EnumDrillMaterial material : values()) {
      if (material.group.equals(group)) {
        list.add(material);
      }
    }

    return list;
  }

  @Override
  public String toString() {

    String s = name();
    // Leaves the first letter capitalized, convert others to lower case.
    return s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
  }
}
