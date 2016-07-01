package net.silentchaos512.supermultidrills.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.supermultidrills.core.handler.DrillsClientEvents;
import net.silentchaos512.supermultidrills.lib.ColorHandlers;

public class DrillsClientProxy extends DrillsCommonProxy {

  @Override
  public void preInit(SRegistry registry) {

    super.preInit(registry);
    registry.clientPreInit();
  }

  @Override
  public void init(SRegistry registry) {

    super.init(registry);
    registry.clientInit();

    MinecraftForge.EVENT_BUS.register(new DrillsClientEvents());
    ColorHandlers.init();
  }

  @Override
  public void postInit(SRegistry registry) {

    super.postInit(registry);
    registry.clientPostInit();
  }
}
