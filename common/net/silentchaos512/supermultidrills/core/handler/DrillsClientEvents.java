package net.silentchaos512.supermultidrills.core.handler;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.client.render.DrillItemOverridesHandler;
import net.silentchaos512.supermultidrills.client.render.ModelDrill;

public class DrillsClientEvents {

  public static final String SMART_MODEL_NAME = SuperMultiDrills.MOD_ID + ":Drill";
  public static final ModelResourceLocation SMART_MODEL = new ModelResourceLocation(
      SMART_MODEL_NAME, "inventory");

  @SubscribeEvent
  public void onModelBake(ModelBakeEvent event) {

    Object object = event.getModelRegistry().getObject(SMART_MODEL);
    if (object instanceof IBakedModel) {
      IBakedModel existingModel = (IBakedModel) object;
      ModelDrill customModel = new ModelDrill(existingModel);
      event.getModelRegistry().putObject(SMART_MODEL, customModel);
      DrillItemOverridesHandler.baseModel = customModel;
    }
  }
}
