package net.silentchaos512.supermultidrills.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.silentchaos512.supermultidrills.TileGenerator;
import net.silentchaos512.supermultidrills.inventory.ContainerGenerator;

public class GuiGenerator extends GuiContainer {

  private static final ResourceLocation guiTextures = new ResourceLocation(
      "supermultidrills:textures/gui/Generator.png");
  private final InventoryPlayer playerInventory;
  private TileGenerator tileGenerator;

  public GuiGenerator(InventoryPlayer playerInventory, TileGenerator generator) {

    super(new ContainerGenerator(playerInventory, generator));
    this.playerInventory = playerInventory;
    this.tileGenerator = generator;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {

    String str = "%d / %d";
    str = String.format(str, tileGenerator.getEnergyStored(ForgeDirection.DOWN),
        tileGenerator.getMaxEnergyStored(ForgeDirection.DOWN));
    this.fontRendererObj.drawString(str, 8, 6, 0x404040);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(guiTextures);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
  }
}
