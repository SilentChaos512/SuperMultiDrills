package net.silentchaos512.supermultidrills.client.render;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.lib.util.ModelHelper;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class ModelDrill implements IPerspectiveAwareModel {

  public static final int PASS_CHASSIS = 0;
  public static final int PASS_HEAD = 1;
  public static final int RENDER_PASS_COUNT = 2;

  private static ModelManager modelManager = null;

  private final IBakedModel baseModel;
  private ItemStack drill;

  public ModelDrill(IBakedModel baseModel) {

    this.baseModel = baseModel;
  }

  @Override
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

    if (drill == null)
      return new ArrayList<BakedQuad>();

    if (modelManager == null)
      modelManager = Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
          .getModelManager();

    List<BakedQuad> quads = Lists.newArrayList();
    ModelResourceLocation modelLocation;
    IBakedModel model;

    for (int pass = 0; pass < RENDER_PASS_COUNT; ++pass) {
      model = getModelForPass(pass);
      if (model != null && !(model instanceof ModelDrill))
        quads.addAll(model.getQuads(state, side, rand));
    }

    return quads;
  }

  protected IBakedModel getModelForPass(int pass) {

    switch (pass) {
      case PASS_CHASSIS:
        return getChassisModel();
      case PASS_HEAD:
        return getHeadModel();
      default:
        SuperMultiDrills.logHelper
            .warning("SmartModelDrill.getModelForPass: unknown render pass " + pass);
        return null;
    }
  }

  protected IBakedModel getChassisModel() {

    double charge = 1.0 - ModItems.drill.getDurabilityForDisplay(drill);
    final int levels = DrillChassis.BATTERY_GAUGE_LEVELS;
    int index = MathHelper.clamp((int) Math.round(levels * charge), 0, levels - 1);
    ModelResourceLocation modelLocation = ModItems.drillChassis.getModelForChargeLevel(index);
    return modelManager.getModel(modelLocation);
  }

  protected IBakedModel getHeadModel() {

    if (drill == null) {
      return null;
    }

    int head = ModItems.drill.getTag(drill, Drill.NBT_HEAD_COAT);
    if (head < 0) {
      head = ModItems.drill.getTag(drill, Drill.NBT_HEAD);
    }
    if (head < 0 || head >= EnumDrillMaterial.values().length) {
      head = 0;
    }

    EnumDrillMaterial material = EnumDrillMaterial.values()[head];
    ModelResourceLocation modelLocation = ModelHelper.getResource(SuperMultiDrills.MOD_ID,
        "DrillHead_" + material.toString());
    return modelManager.getModel(modelLocation);
  }

  @Override
  public boolean isAmbientOcclusion() {

    return baseModel.isAmbientOcclusion();
  }

  @Override
  public boolean isGui3d() {

    return baseModel.isGui3d();
  }

  @Override
  public boolean isBuiltInRenderer() {

    return baseModel.isBuiltInRenderer();
  }

  @Override
  public TextureAtlasSprite getParticleTexture() {

    return baseModel.getParticleTexture();
  }

  private static ItemTransformVec3f thirdPersonLeft = null;
  private static ItemTransformVec3f thirdPersonRight = null;
  private static ItemTransformVec3f firstPersonLeft = null;
  private static ItemTransformVec3f firstPersonRight = null;

  @Override
  public ItemCameraTransforms getItemCameraTransforms() {

    Vector3f rotation = new Vector3f();
    Vector3f translation = new Vector3f();
    Vector3f scale = new Vector3f(1f, 1f, 1f);

    // Third Person
    rotation = new Vector3f(0f, (float) -Math.PI / 2f, (float) Math.PI * 7f / 36f); // (0, 90, -35)
    translation = new Vector3f(0f, 5.5f, 2.5f);
    translation.scale(0.0625f);
    thirdPersonRight = new ItemTransformVec3f(rotation, translation, scale);

    rotation = new Vector3f(0f, (float) Math.PI / 2f, (float) -Math.PI * 7f / 36f); // (0, 90, -35)
    thirdPersonLeft = new ItemTransformVec3f(rotation, translation, scale);

    // First Person
    rotation = new Vector3f(0f, (float) -Math.PI * 1f / 2f, (float) Math.PI * 5f / 36f);
    translation = new Vector3f(1.13f, 3.2f, 1.13f);
    translation.scale(0.0625f);
    scale = new Vector3f(0.68f, 0.68f, 0.68f);
    firstPersonRight = new ItemTransformVec3f(rotation, translation, scale);

    rotation = new Vector3f(0f, (float) Math.PI * 1f / 2f, (float) -Math.PI * 5f / 36f);
    firstPersonLeft = new ItemTransformVec3f(rotation, translation, scale);

    // Head and GUI are default.
    return new ItemCameraTransforms(thirdPersonLeft, thirdPersonRight, firstPersonLeft,
        firstPersonRight, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT,
        ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
  }

  @Override
  public Pair<? extends IBakedModel, Matrix4f> handlePerspective(
      TransformType cameraTransformType) {

    Matrix4f matrix = new Matrix4f();
    switch (cameraTransformType) {
      case FIRST_PERSON_RIGHT_HAND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().firstperson_right);
        break;
      case FIRST_PERSON_LEFT_HAND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().firstperson_left);
        break;
      case GUI:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().gui);
        break;
      case HEAD:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().head);
        break;
      case THIRD_PERSON_RIGHT_HAND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().thirdperson_right);
        break;
      case THIRD_PERSON_LEFT_HAND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().thirdperson_left);
        break;
      case GROUND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().ground);
        matrix.setScale(matrix.getScale() * 0.5f);
        break;
      case FIXED:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().fixed);
      default:
        break;
    }

    return Pair.of((IBakedModel) this, matrix);
  }

  @Override
  public ItemOverrideList getOverrides() {

    return DrillItemOverridesHandler.INSTANCE;
  }

  public IBakedModel handleItemState(ItemStack stack) {

    drill = stack;
    return this;
  }
}
