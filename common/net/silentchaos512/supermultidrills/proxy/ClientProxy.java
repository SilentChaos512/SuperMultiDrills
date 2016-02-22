package net.silentchaos512.supermultidrills.proxy;

import net.silentchaos512.supermultidrills.registry.SRegistry;

public class ClientProxy extends CommonProxy {

  @Override
  public void preInit() {

    super.preInit();
    SRegistry.clientPreInit();
  }

  @Override
  public void init() {

    super.init();
    SRegistry.clientInit();
  }
}
