package net.silentchaos512.supermultidrills.client.render;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.item.Drill;
import net.silentchaos512.supermultidrills.item.DrillChassis;
import net.silentchaos512.supermultidrills.item.ModItems;
import net.silentchaos512.supermultidrills.lib.EnumDrillMaterial;
import net.silentchaos512.supermultidrills.util.LogHelper;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class SmartModelDrill implements ISmartItemModel, IPerspectiveAwareModel {

  public static final int PASS_CHASSIS = 0;
  public static final int PASS_HEAD = 1;
  public static final int RENDER_PASS_COUNT = 2;

  private final IBakedModel baseModel;
  private ItemStack drill;
  private ModelManager modelManager = Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
      .getModelManager();

  public SmartModelDrill(IBakedModel baseModel) {

    this.baseModel = baseModel;
  }

  @Override
  public List<BakedQuad> getFaceQuads(EnumFacing face) {

    return baseModel.getFaceQuads(face);
  }

  @Override
  public List<BakedQuad> getGeneralQuads() {

    if (drill == null) {
      return new ArrayList<BakedQuad>();
    }

    List<BakedQuad> quads = Lists.newArrayList();
    ModelResourceLocation modelLocation;
    IBakedModel model;

    for (int pass = 0; pass < RENDER_PASS_COUNT; ++pass) {
      model = getModelForPass(pass);
      if (model != null && !(model instanceof SmartModelDrill)) {
        quads.addAll(model.getGeneralQuads());
      }
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
        LogHelper.warning("SmartModelDrill.getModelForPass: unknown render pass " + pass);
        return null;
    }
  }

  protected IBakedModel getChassisModel() {

    double charge = 1.0 - ModItems.drill.getDurabilityForDisplay(drill);
    final int levels = DrillChassis.BATTERY_GAUGE_LEVELS;
    int index = MathHelper.clamp_int((int) Math.round(levels * charge), 0, levels - 1);
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
    ModelResourceLocation modelLocation = new ModelResourceLocation(
        SuperMultiDrills.MOD_ID + ":DrillHead_" + material.toString(), "inventory");
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

  @Override
  public ItemCameraTransforms getItemCameraTransforms() {

    Vector3f rotation, translation, scale;

    // Third Person
    rotation = new Vector3f(0f, (float) Math.PI / 2f, (float) -Math.PI * 7f / 36f); // (0, 90, -35)
    translation = new Vector3f(0f, 1.25f, -3.5f);
    translation.scale(0.0625f);
    scale = new Vector3f(0.85f, 0.85f, 0.85f);
    ItemTransformVec3f thirdPerson = new ItemTransformVec3f(rotation, translation, scale);

    // First Person
    rotation = new Vector3f(0f, (float) -Math.PI * 3f / 4f, (float) Math.PI * 5f / 36f); // (0, -135, 25)
    translation = new Vector3f(0f, 4f, 2f);
    translation.scale(0.0625f);
    scale = new Vector3f(1.7f, 1.7f, 1.7f);
    ItemTransformVec3f firstPerson = new ItemTransformVec3f(rotation, translation, scale);

    // Head and GUI are default.
    return new ItemCameraTransforms(thirdPerson, firstPerson, ItemTransformVec3f.DEFAULT,
        ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
  }

  @Override
  public VertexFormat getFormat() {

    return DefaultVertexFormats.ITEM;
  }

  @Override
  public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(
      TransformType cameraTransformType) {

    Matrix4f matrix = new Matrix4f();
    switch (cameraTransformType) {
      case FIRST_PERSON:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().firstPerson);
        break;
      case GUI:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().gui);
        break;
      case HEAD:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().head);
        break;
      case THIRD_PERSON:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().thirdPerson);
        break;
      case GROUND:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().ground);
        break;
      case FIXED:
        matrix = ForgeHooksClient.getMatrix(getItemCameraTransforms().fixed);
      default:
        break;
    }
    return Pair.of((IFlexibleBakedModel) this, matrix);
  }

  @Override
  public IBakedModel handleItemState(ItemStack stack) {

    if (stack.getItem() instanceof Drill) {
      drill = stack;
    }
    return this;
  }

}
