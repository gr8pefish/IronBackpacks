package gr8pefish.ironbackpacks.block;


import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.network.GuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Block with a TileEntity
 *
 * {@link TileEntityBackpack}
 */
public class BlockBackpack extends AbstractBlockTileEntity<TileEntityBackpack> {

    /** Field for use in unlocalized and registry naming*/
    public static final String BACKPACK_NAME = "backpack_block";

    /** Field for holding backpack variant data */
    public BackpackVariant backpackVariant;

    // No longer needed, see the field above
//    public static final IProperty<BackpackVariantEnum> BACKPACK_VARIANT = PropertyEnum.create("backpack_variant", BackpackVariantEnum.class);

    /** Field for the IProperty */ //TODO: Move to API
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    /** Constructor */
    public BlockBackpack(BackpackVariant backpackVariant) {
        super(Material.ROCK, BACKPACK_NAME+"_"+backpackVariant.toString().toLowerCase());
        this.backpackVariant = backpackVariant;
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); //Default facing arbitrary
    }

    //Getter

    public BackpackVariant getBackpackVariant() {
        return backpackVariant;
    }

    //TileEntity Methods

    @Override
    public Class<TileEntityBackpack> getTileEntityClass() {
        return TileEntityBackpack.class;
    }

    @Nullable
    @Override
    public TileEntityBackpack createTileEntity(World world, IBlockState state) {
        return new TileEntityBackpack(placeholder); //TODO: BackpackInfo from the itemStack
    }


    //Block Overrides


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(IronBackpacks.INSTANCE, GuiHandler.OPEN_GUI_TE_ID, world, pos.getX(), pos.getY(), pos.getZ()); //ToDo: Open correct GUI
            getTileEntity(world, pos).markDirty(); //necessary?
        }
        return true;
    }

    /** When breaking the block, include the contents of the inventory. */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityBackpack tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH); //ToDo: Custom BackpackInfo
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                spawnAsEntity(world, pos, stack);
            }
        }
        //Also drop itself
        this.dropBlockAsItem(world, pos, state, 0);
        //necessary?
        super.breakBlock(world, pos, state);
    }

    //Uses custom JSON Model, which is not a full cube

    //You can override but not call if they are deprecated
    @Override
    @Deprecated
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    //Add bounding box, with sidedness depending on placement

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return new AxisAlignedBB(0.9D, 0.0D, 0.235D, 0.1, 1.0D, 0.635D); //x = length, y = height, z = width
            case EAST:
                return new AxisAlignedBB(0.36D, 0.0D, 0.1D, 0.76, 1.0D, 0.9D); //x = width, y = height, z = length
            case SOUTH:
                return new AxisAlignedBB(0.9D, 0.0D, 0.76D, 0.1, 1.0D, 0.36D); //x = length, y = height, z = width
            case WEST:
                return new AxisAlignedBB(0.635D, 0.0D, 0.1D, 0.235, 1.0D, 0.9D); //x = width, y = height, z = length
        }

        //North default (should be unreachable)
        return new AxisAlignedBB(0.635D, 0.0D, 0.1D, 0.235, 1.0D, 0.9D); //x1 = width end, x2 = width start, y2= height

    }

    //VARIANT block state

    //TODO: Test, as removed BACKPACK_VARIANT IProperty and haven't tested this updated code yet
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.Plane.HORIZONTAL.facings()[meta & 0b11]; //first 2 bits for facing, 4 possibilities
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).ordinal() - EnumFacing.Plane.VERTICAL.facings().length; //0-3, ignore Up and Down
        return facing; //first 2 bits for facing
    }


    // Other


    //TODO: Test
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        //create new itemstack with all the info
        ItemStack pack = IronBackpacksAPI.applyPackInfo(new ItemStack(IronBackpacksAPI.BACKPACK_ITEM), BackpackInfo.fromTileEntity(this.getTileEntity(world, pos)));
        drops.add(pack);

        super.getDrops(drops, world, pos, state, fortune);
    }



    //ToDo: Go through placement logic and determine what is necessary


    //stolen from vanilla furnace
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    //stolen from vanilla furnace
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }


    //TODO: Plan
    //r-click itemStack
        //set one instance of the block
            //set TE data dynamically

}

