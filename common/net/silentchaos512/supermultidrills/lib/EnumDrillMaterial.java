package net.silentchaos512.supermultidrills.lib;

import java.math.BigDecimal;

import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.supermultidrills.configuration.Config;
import net.silentchaos512.supermultidrills.util.LogHelper;

import com.udojava.evalex.Expression;

public enum EnumDrillMaterial {

  // Vanilla
  IRON(250, 6.0f, 2.0f, ""),
  GOLD(32, 12.0f, 0.0f, ""),
  DIAMOND(1561, 8.0f, 3.0f, ""),

  // Thermal Foundation
  COPPER(175, 4.0f, 0.5f, "ingotCopper"),
  TIN(200, 4.5f, 1.0f, "ingotTin"),
  SILVER(200, 6.0f, 1.5f, "ingotSilver"),
  LEAD(150, 5.0f, 1.0f, "ingotLead"),
  FERROUS(300, 6.5f, 2.5f, "ingotNickel"),
  ELECTRUM(100, 14.0f, 0.5f, "ingotElectrum"),
  INVAR(450, 7.0f, 3.0f, "ingotInvar"),
  BRONZE(500, 6.0f, 2.0f, "ingotBronze"),
  SHINY(1700, 9.0f, 4.0f, "ingotPlatinum"),

  // Silent's Gems
  RUBY(1536, 8.0f, 3.0f, "gemRuby"),
  GARNET(1024, 8.0f, 3.0f, "gemGarnet"),
  TOPAZ(1024, 10.0f, 4.0f, "gemTopaz"),
  HELIODOR(768, 12.0f, 5.0f, "gemHeliodor"),
  PERIDOT(1024, 7.0f, 4.0f, "gemPeridot"),
  BERYL(1024, 8.0f, 4.0f, "gemBeryl"),
  AQUAMARINE(768, 10.0f, 3.0f, "gemAquamarine"),
  SAPPHIRE(1536, 8.0f, 3.0f, "gemSapphire"),
  IOLITE(1536, 7.0f, 2.0f, "gemIolite"),
  AMETHYST(1024, 8.0f, 3.0f, "gemAmethyst"),
  MORGANITE(1024, 10.0f, 4.0f, "gemMorganite"),
  ONYX(768, 10.0f, 6.0f, "gemOnyx"),

  // Misc
  STEEL(900, 7.0f, 2.0f, "ingotSteel");

  private final int durability;
  private final float efficiency;
  private final float damage;
  private final String material;

  private EnumDrillMaterial(int durability, float efficiency, float damage, String material) {

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

    if (this == IRON) {
      return "Iron";
    } else if (this == GOLD) {
      return "Gold";
    } else if (this == DIAMOND) {
      return "Diamond";
    }

    for (int i = 0; i < this.material.length(); ++i) {
      if (this.material.charAt(i) < 'a') {
        return this.material.substring(i);
      }
    }

    return this.material;
  }

  public float getCostPerHardness() {

    Expression exp = Config.energyCostExpression;
    exp.setVariable("durability", BigDecimal.valueOf(this.durability));
    exp.setVariable("efficiency", BigDecimal.valueOf(0));
    exp.setVariable("hardness", BigDecimal.valueOf(1));
    exp.setVariable("mining_speed", BigDecimal.valueOf(this.efficiency));
    return exp.eval().floatValue();
  }

  /**
   * Determines if a drill head can be crafted, ie there is some item that matches the ore
   * dictionary entry of the material.
   * 
   * @return
   */
  public boolean canCraftHead() {

    if (this == IRON || this == GOLD || this == DIAMOND) {
      return true;
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

  @Override
  public String toString() {

    String s = name();
    // Leaves the first letter capitalized, convert others to lower case.
    return s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
  }
}
