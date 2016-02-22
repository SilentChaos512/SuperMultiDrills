//package net.silentchaos512.supermultidrills;
//
//import cofh.api.energy.EnergyStorage;
//import cofh.api.energy.IEnergyContainerItem;
//import cofh.api.energy.IEnergyHandler;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.ISidedInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.network.NetworkManager;
//import net.minecraft.network.Packet;
//import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
//import net.minecraft.tileentity.TileEntityFurnace;
//import net.minecraftforge.common.util.ForgeDirection;
//
//public class TileGenerator extends TileEntityFurnace implements ISidedInventory, IEnergyHandler {
//
//  public static final int PRODUCTION_RATE = 20;
//
//  public static final int SLOT_INPUT = 0;
//  public static final int SLOT_OUTPUT = 1;
//  public static final int SLOT_FUEL = 2;
//
//  protected ItemStack[] inventory = new ItemStack[3];
//  protected int[] slotsBottom = new int[] { SLOT_OUTPUT };
//  protected int[] slotsTop = new int[] { SLOT_INPUT };
//  protected int[] slotsSide = new int[] { SLOT_INPUT, SLOT_FUEL };
//
//  protected EnergyStorage storage = new EnergyStorage(500000, 1000, 1000); // TODO: Config?
//  protected int burnTimeRemaining;
//  protected int currentItemBurnTime;
//
//  @Override
//  public void readFromNBT(NBTTagCompound tags) {
//
//    super.readFromNBT(tags);
//    storage.readFromNBT(tags);
//    burnTimeRemaining = tags.getInteger("BurnTime");
//
//    NBTTagList tagList = tags.getTagList("Items", 10);
//    for (int i = 0; i < tagList.tagCount(); ++i) {
//      NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
//      byte slot = tagCompound.getByte("Slot");
//
//      if (slot >= 0 && slot < inventory.length) {
//        inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
//      }
//    }
//
//    currentItemBurnTime = TileEntityFurnace.getItemBurnTime(inventory[0]);
//  }
//
//  @Override
//  public void writeToNBT(NBTTagCompound tags) {
//
//    super.writeToNBT(tags);
//    storage.writeToNBT(tags);
//    tags.setInteger("BurnTime", burnTimeRemaining);
//
//    NBTTagList tagList = new NBTTagList();
//    for (int i = 0; i < inventory.length; ++i) {
//      if (inventory[i] != null) {
//        NBTTagCompound tagCompound = new NBTTagCompound();
//        tagCompound.setByte("Slot", (byte) i);
//        inventory[i].writeToNBT(tagCompound);
//        tagList.appendTag(tagCompound);
//      }
//    }
//    tags.setTag("Items", tagList);
//  }
//
//  @Override
//  public Packet getDescriptionPacket() {
//
//    NBTTagCompound tags = new NBTTagCompound();
//    tags.setInteger("BurnTime", burnTimeRemaining);
//    tags.setInteger("CurrentItemBurnTime", currentItemBurnTime);
//    return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tags);
//  }
//
//  @Override
//  public void onDataPacket(NetworkManager network, S35PacketUpdateTileEntity packet) {
//
//    burnTimeRemaining = packet.func_148857_g().getInteger("BurnTime");
//    currentItemBurnTime = packet.func_148857_g().getInteger("CurrentItemBurnTime");
//  }
//
//  @Override
//  public void updateEntity() {
//
//    if (!worldObj.isRemote) {
//      // Produce energy
//      if (burnTimeRemaining > 0) {
//        storage.receiveEnergy(PRODUCTION_RATE, false);
//      }
//      burnFuel();
//    }
//  }
//
//  private void burnFuel() {
//
//    boolean markForUpdate = false;
//
//    if (burnTimeRemaining > 0) {
//      --burnTimeRemaining;
//      markForUpdate = true; // TODO: Make less frequent?
//    }
//
//    if (burnTimeRemaining <= 0 && storage.receiveEnergy(PRODUCTION_RATE, true) > 0) {
//      ItemStack fuel = inventory[SLOT_FUEL];
//      if (fuel != null) {
//        int fuelBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
//        if (fuelBurnTime > 0) {
//          currentItemBurnTime = burnTimeRemaining = fuelBurnTime;
//          --fuel.stackSize;
//          if (fuel.stackSize == 0) {
//            fuel = null;
//          }
//          markForUpdate = true;
//        }
//      }
//    }
//
//    if (markForUpdate) {
//      worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//    }
//  }
//
//  public boolean isBurningFuel() {
//
//    return burnTimeRemaining > 0;
//  }
//
//  public int getBurnTimeRemaining() {
//
//    return burnTimeRemaining;
//  }
//
//  public int getCurrentItemBurnTime() {
//
//    return currentItemBurnTime;
//  }
//
//  public int getBurnTimeRemainingScaled(int k) {
//
//    if (currentItemBurnTime == 0) {
//      currentItemBurnTime = 200;
//    }
//
//    return burnTimeRemaining * k / currentItemBurnTime;
//  }
//
//  @Override
//  public boolean canConnectEnergy(ForgeDirection from) {
//
//    return true;
//  }
//
//  @Override
//  public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
//
//    return storage.receiveEnergy(maxReceive, simulate);
//  }
//
//  @Override
//  public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
//
//    return storage.extractEnergy(maxExtract, simulate);
//  }
//
//  @Override
//  public int getEnergyStored(ForgeDirection from) {
//
//    return storage.getEnergyStored();
//  }
//
//  @Override
//  public int getMaxEnergyStored(ForgeDirection from) {
//
//    return storage.getMaxEnergyStored();
//  }
//
//  @Override
//  public int getSizeInventory() {
//
//    return inventory.length;
//  }
//
//  @Override
//  public ItemStack getStackInSlot(int slot) {
//
//    if (slot >= 0 && slot < getSizeInventory()) {
//      return inventory[slot];
//    }
//    return null;
//  }
//
//  @Override
//  public void setInventorySlotContents(int slot, ItemStack stack) {
//
//    this.inventory[slot] = stack;
//
//    if (stack != null && stack.stackSize > getInventoryStackLimit()) {
//      stack.stackSize = getInventoryStackLimit();
//    }
//  }
//
//  @Override
//  public ItemStack decrStackSize(int slot, int count) {
//
//    if (inventory[slot] != null) {
//      ItemStack stack;
//
//      if (inventory[slot].stackSize <= count) {
//        stack = inventory[slot];
//        inventory[slot] = null;
//        return stack;
//      } else {
//        stack = inventory[slot].splitStack(count);
//
//        if (inventory[slot].stackSize == 0) {
//          inventory[slot] = null;
//        }
//
//        return stack;
//      }
//    } else {
//      return null;
//    }
//  }
//
//  @Override
//  public ItemStack getStackInSlotOnClosing(int slot) {
//
//    if (inventory[slot] != null) {
//      ItemStack stack = inventory[slot];
//      inventory[slot] = null;
//      return stack;
//    } else {
//      return null;
//    }
//  }
//
//  @Override
//  public String getInventoryName() {
//
//    return "container.supermultidrills:Generator";
//  }
//
//  @Override
//  public boolean hasCustomInventoryName() {
//
//    return false;
//  }
//
//  @Override
//  public int getInventoryStackLimit() {
//
//    return 64;
//  }
//
//  @Override
//  public boolean isUseableByPlayer(EntityPlayer player) {
//
//    return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
//        : player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D,
//            (double) zCoord + 0.5D) <= 64.0D;
//  }
//
//  @Override
//  public void openInventory() {
//
//  }
//
//  @Override
//  public void closeInventory() {
//
//  }
//
//  @Override
//  public boolean isItemValidForSlot(int slot, ItemStack stack) {
//
//    switch (slot) {
//      case SLOT_FUEL:
//        return TileEntityFurnace.isItemFuel(stack);
//      case SLOT_INPUT:
//        return stack.getItem() instanceof IEnergyContainerItem;
//      default:
//        return false;
//    }
//  }
//}
