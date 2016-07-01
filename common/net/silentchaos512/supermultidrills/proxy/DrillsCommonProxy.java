package net.silentchaos512.supermultidrills.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.compat.wit.DrillsWitHandler;
import net.silentchaos512.supermultidrills.core.handler.DrillsCommonEvents;

public class DrillsCommonProxy extends net.silentchaos512.lib.proxy.CommonProxy {

  @Override
  public void preInit(SRegistry registry) {

    super.preInit(registry);
  }

  @Override
  public void init(SRegistry registry) {

    super.init(registry);

    // Event handlers
    MinecraftForge.EVENT_BUS.register(new DrillsCommonEvents());
    if (SuperMultiDrills.instance.foundWit)
      MinecraftForge.EVENT_BUS.register(new DrillsWitHandler());
  }

  @Override
  public void postInit(SRegistry registry) {

    super.postInit(registry);
  }
}
