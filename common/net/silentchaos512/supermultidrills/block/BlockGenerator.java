package net.silentchaos512.supermultidrills.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.TileGenerator;
import net.silentchaos512.supermultidrills.gui.GuiHandlerSuperMultiDrills;
import net.silentchaos512.supermultidrills.lib.Names;
import net.silentchaos512.supermultidrills.lib.Strings;
import net.silentchaos512.supermultidrills.registry.IAddRecipe;

public class BlockGenerator extends BlockContainer implements IAddRecipe {

  private IIcon iconTopBottom;
  private IIcon iconSide;
  private IIcon iconFront;

  public BlockGenerator() {

    super(Material.iron);
    setHardness(6.0f);
    setResistance(60.0f);
    setStepSound(Block.soundTypeMetal);
    setCreativeTab(SuperMultiDrills.creativeTab);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int par2) {

    return new TileGenerator();
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public void addRecipes() {

    // TODO
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side,
      float hitX, float hitY, float hitZ) {

    if (world.isRemote) {
      return true;
    }

    TileEntity tile = world.getTileEntity(x, y, z);

    if (tile instanceof TileGenerator) {
      player.openGui(SuperMultiDrills.instance, GuiHandlerSuperMultiDrills.ID_GENERATOR, world, x,
          y, z);
    }

    return true;
  }

  @Override
  public String getUnlocalizedName() {

    return "tile." + Names.GENERATOR;
  }

  @Override
  public void registerBlockIcons(IIconRegister reg) {

    String prefix = Strings.RESOURCE_PREFIX + Names.GENERATOR + "_";
    iconTopBottom = reg.registerIcon(prefix + "TopBottom");
    iconSide = reg.registerIcon(prefix + "Side");
    iconFront = reg.registerIcon(prefix + "Front");
  }

  @Override
  public IIcon getIcon(int side, int meta) {

    // TODO: What is front?
    switch (side) {
      case 0:
      case 1:
        return iconTopBottom;
      default:
        return iconSide;
    }
  }
  
  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int par6) {

    TileGenerator tileGenerator = (TileGenerator) world.getTileEntity(x, y, z);

    if (tileGenerator != null) {
      for (int i = 0; i < tileGenerator.getSizeInventory(); ++i) {
        ItemStack stack = tileGenerator.getStackInSlot(i);

        if (stack != null) {
          float f = SuperMultiDrills.instance.random.nextFloat() * 0.8F + 0.1F;
          float f1 = SuperMultiDrills.instance.random.nextFloat() * 0.8F + 0.1F;
          float f2 = SuperMultiDrills.instance.random.nextFloat() * 0.8F + 0.1F;

          while (stack.stackSize > 0) {
            int j1 = SuperMultiDrills.instance.random.nextInt(21) + 10;

            if (j1 > stack.stackSize) {
              j1 = stack.stackSize;
            }

            stack.stackSize -= j1;
            EntityItem entityitem = new EntityItem(world, (double) ((float) x + f),
                (double) ((float) y + f1), (double) ((float) z + f2),
                new ItemStack(stack.getItem(), j1, stack.getItemDamage()));

            if (stack.hasTagCompound()) {
              entityitem.getEntityItem()
                  .setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }

            float f3 = 0.05F;
            entityitem.motionX = (double) ((float) SuperMultiDrills.instance.random.nextGaussian() * f3);
            entityitem.motionY = (double) ((float) SuperMultiDrills.instance.random.nextGaussian() * f3
                + 0.2F);
            entityitem.motionZ = (double) ((float) SuperMultiDrills.instance.random.nextGaussian() * f3);
            world.spawnEntityInWorld(entityitem);
          }
        }
      }
    }
  }
}
