package net.silentchaos512.supermultidrills.lib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.supermultidrills.configuration.Config;

import com.udojava.evalex.Expression;

public enum EnumDrillMaterial {

  // Vanilla
  IRON(EnumDrillMaterial.GROUP_VANILLA, 250, 6.0f, 2.0f, "ingotIron"),
  GOLD(EnumDrillMaterial.GROUP_VANILLA, 32, 12.0f, 0.0f, "ingotGold"),
  DIAMOND(EnumDrillMaterial.GROUP_VANILLA, 1561, 8.0f, 3.0f, "gemDiamond"),

  // Thermal Foundation
  COPPER(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 175, 4.0f, 0.5f, "ingotCopper"),
  TIN(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 200, 4.5f, 1.0f, "ingotTin"),
  SILVER(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 200, 6.0f, 1.5f, "ingotSilver"),
  LEAD(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 150, 5.0f, 1.0f, "ingotLead"),
  FERROUS(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 300, 6.5f, 2.5f, "ingotNickel"),
  ELECTRUM(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 100, 14.0f, 0.5f, "ingotElectrum"),
  INVAR(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 450, 7.0f, 3.0f, "ingotInvar"),
  BRONZE(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 500, 6.0f, 2.0f, "ingotBronze"),
  PLATINUM(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 1700, 9.0f, 4.0f, "ingotPlatinum"), // Was Shiny

  // Silent's Gems
  RUBY(EnumDrillMaterial.GROUP_SILENT_GEMS, 1536, 8.0f, 3.0f, "gemRuby"),
  GARNET(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 8.0f, 3.0f, "gemGarnet"),
  TOPAZ(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 10.0f, 4.0f, "gemTopaz"),
  HELIODOR(EnumDrillMaterial.GROUP_SILENT_GEMS, 768, 12.0f, 5.0f, "gemHeliodor"),
  PERIDOT(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 7.0f, 4.0f, "gemPeridot"),
  BERYL(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 8.0f, 4.0f, "gemBeryl"),
  AQUAMARINE(EnumDrillMaterial.GROUP_SILENT_GEMS, 768, 10.0f, 3.0f, "gemAquamarine"),
  SAPPHIRE(EnumDrillMaterial.GROUP_SILENT_GEMS, 1536, 8.0f, 3.0f, "gemSapphire"),
  IOLITE(EnumDrillMaterial.GROUP_SILENT_GEMS, 1536, 7.0f, 2.0f, "gemIolite"),
  AMETHYST(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 8.0f, 3.0f, "gemAmethyst"),
  MORGANITE(EnumDrillMaterial.GROUP_SILENT_GEMS, 1024, 10.0f, 4.0f, "gemMorganite"),
  ONYX(EnumDrillMaterial.GROUP_SILENT_GEMS, 768, 10.0f, 6.0f, "gemOnyx"),

  // Tinker's Construct
  STEEL(EnumDrillMaterial.GROUP_TCONSTRUCT, 900, 10.0f, 4.0f, "ingotSteel"),
  ALUMITE(EnumDrillMaterial.GROUP_TCONSTRUCT, 1050, 8.0f, 3.0f, "ingotAlumite"),
  COBALT(EnumDrillMaterial.GROUP_TCONSTRUCT, 1200, 14.0f, 3.0f, "ingotCobalt"),
  ARDITE(EnumDrillMaterial.GROUP_TCONSTRUCT, 750, 8.0f, 3.0f, "ingotArdite"),
  MANYULLYN(EnumDrillMaterial.GROUP_TCONSTRUCT, 1800, 9.0f, 4.0f, "ingotManyullyn"),

  // Extra TiC
  FAIRY(EnumDrillMaterial.GROUP_TCONSTRUCT, 250, 7.5f, 1.0f, "ingotFairy"),
  POKEFENNIUM(EnumDrillMaterial.GROUP_TCONSTRUCT, 500, 8.5f, 1.0f, "ingotPokefennium"),
  REDAURUM(EnumDrillMaterial.GROUP_TCONSTRUCT, 250, 7.5f, 1.0f, "ingotPokefennium"),

  // More Thermal Foundation. Skipping Mana-Infused, that's Mithril.
  ENDERIUM(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 1100, 23.0f, 15.0f, "ingotEnderium"),
  SIGNALUM(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 800, 13.0f, 6.0f, "ingotSignalum"),
  LUMIUM(EnumDrillMaterial.GROUP_THERMAL_FOUNDATION, 1500, 3.0f, 8.0f, "ingotLumium"),

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
  DESICHALKOS(EnumDrillMaterial.GROUP_METALLURGY, 1800, 10.0f, 4.0f, "ingotDesichalkos"),

  // Ender IO (aside from Dark Steel, these numbers are made up).
  DARK_STEEL(EnumDrillMaterial.GROUP_ENDER_IO, 1561, 7.0f, 1.0f, "ingotDarkSteel"),
  ELECTRICAL_STEEL(EnumDrillMaterial.GROUP_ENDER_IO, 300, 6.0f, 2.0f, "ingotElectricalSteel"),
  CONDUCTIVE_IRON(EnumDrillMaterial.GROUP_ENDER_IO, 100, 8.0f, 1.5f, "ingotConductiveIron"),
  ENERGETIC_ALLOY(EnumDrillMaterial.GROUP_ENDER_IO, 150, 11.0f, 2.0f, "ingotEnergeticAlloy"),
  VIBRANT_ALLOY(EnumDrillMaterial.GROUP_ENDER_IO, 400, 14.0f, 3.0f, "ingotPhasedGold"),
  PULSATING_IRON(EnumDrillMaterial.GROUP_ENDER_IO, 850, 6.0f, 2.0f, "ingotPhasedIron"),
  SOULARIUM(EnumDrillMaterial.GROUP_ENDER_IO, 1865, 4.0f, 1.0f, "ingotSoularium");

  // Group for sorting in NEI, since metadata might be a bit scattered.
  public static final String GROUP_VANILLA = "Vanilla";
  public static final String GROUP_THERMAL_FOUNDATION = "Thermal Foundation";
  public static final String GROUP_ENDER_IO = "Ender IO";
  public static final String GROUP_SILENT_GEMS = "Silent's Gems";
  public static final String GROUP_TCONSTRUCT = "Tinker's Construct";
  public static final String GROUP_BOTANIA = "Botania";
  public static final String GROUP_METALLURGY = "Metallurgy";
  public static final String[] GROUPS_ORDERED = new String[] { GROUP_VANILLA,
      GROUP_THERMAL_FOUNDATION, GROUP_ENDER_IO, GROUP_SILENT_GEMS, GROUP_BOTANIA, GROUP_TCONSTRUCT,
      GROUP_METALLURGY };

  private final int durability;
  private final float efficiency;
  private final float damage;
  private final String material;
  private final String group;

  private EnumDrillMaterial(String group, int durability, float efficiency, float damage,
      String material) {

    this.group = group;
    this.durability = durability;
    this.efficiency = efficiency;
    this.damage = damage;
    this.material = material;
  }

  public int getDurability() {

    return this.durability;
  }

  public float getEfficiency() {

    return this.efficiency;
  }

  public float getDamageVsEntity() {

    return this.damage;
  }

  public String getMaterial() {

    return this.material;
  }

  public String getMaterialName() {

    // if (this == IRON) {
    // return "Iron";
    // } else if (this == GOLD) {
    // return "Gold";
    // } else if (this == DIAMOND) {
    // return "Diamond";
    // }

    // Strip ingot/gem/etc, leaving just the material (ie, Iron, DarkSteel, Ruby)
    for (int i = 0; i < this.material.length(); ++i) {
      if (this.material.charAt(i) < 'a') {
        return this.material.substring(i);
      }
    }

    return this.material;
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

    // Disabled in config?
    if (isDisabled()) {
      return false;
    }

    String[] ores = OreDictionary.getOreNames();
    for (String str : ores) {
      if (str.equals(this.material)) {
        // Ore Dictionary key exists (highly likely). Is there an item that matches that?
        if (OreDictionary.getOres(str).isEmpty()) {
          return false; // No item with this oredict key.
        }
        return true; // There is an item.
      }
    }

    // No matching oredict key.
    return false;
  }

  public boolean isDisabled() {

    return (Config.disableBotaniaHeads && group.equals(GROUP_BOTANIA))
        || (Config.disableEnderIOHeads && group.equals(GROUP_ENDER_IO))
        || (Config.disableMetallurgyHeads && group.equals(GROUP_METALLURGY))
        || (Config.disableSilentGemsHeads && group.equals(GROUP_SILENT_GEMS))
        || (Config.disableThermalFoundationHeads && group.equals(GROUP_THERMAL_FOUNDATION))
        || (Config.disableTinkersConstructHeads && group.equals(GROUP_TCONSTRUCT));
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
