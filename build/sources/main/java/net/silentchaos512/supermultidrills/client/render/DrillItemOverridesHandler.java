package net.silentchaos512.supermultidrills.client.render;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class DrillItemOverridesHandler extends ItemOverrideList {

  public static final DrillItemOverridesHandler INSTANCE = new DrillItemOverridesHandler();
  public static IBakedModel baseModel;

  public DrillItemOverridesHandler() {

    super(ImmutableList.<ItemOverride> of());
  }

  @Override
  public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {

    if (stack == null)
      return baseModel;
    return new ModelDrill(baseModel).handleItemState(stack);
  }

}
