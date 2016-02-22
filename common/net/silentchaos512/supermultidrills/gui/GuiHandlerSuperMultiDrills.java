//package net.silentchaos512.supermultidrills.gui;
//
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.network.IGuiHandler;
//import net.silentchaos512.supermultidrills.TileGenerator;
//import net.silentchaos512.supermultidrills.inventory.ContainerGenerator;
//import net.silentchaos512.supermultidrills.util.LogHelper;
//
//public class GuiHandlerSuperMultiDrills implements IGuiHandler {
//
//  public static final int ID_GENERATOR = 0;
//
//  @Override
//  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//
//    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
//
//    switch (ID) {
//      case ID_GENERATOR:
//        if (tile instanceof TileGenerator) {
//          TileGenerator tileGenerator = (TileGenerator) tile;
//          return new ContainerGenerator(player.inventory, tileGenerator);
//        }
//        return null;
//      default:
//        LogHelper.warning("No GUI with ID " + ID + "!");
//        return null;
//    }
//  }
//
//  @Override
//  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//
//    TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
//
//    switch (ID) {
//      case ID_GENERATOR:
//        if (tile instanceof TileGenerator) {
//          TileGenerator tileGenerator = (TileGenerator) tile;
//          return new GuiGenerator(player.inventory, tileGenerator);
//        }
//        return null;
//      default:
//        LogHelper.warning("No GUI with ID " + ID + "!");
//        return null;
//    }
//  }
//
//}
