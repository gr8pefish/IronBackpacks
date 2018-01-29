package gr8pefish.ironbackpacks.block;


import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import gr8pefish.ironbackpacks.network.GuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
        return new TileEntityBackpack(); //TODO: BackpackInfo from the itemStack here?
    }


    //Block Overrides


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(IronBackpacks.INSTANCE, GuiHandler.OPEN_GUI_TE_ID, world, pos.getX(), pos.getY(), pos.getZ()); //ToDo: Open correct GUI
        }
        return true;
    }

    /** When breaking the block, drop the backpack stack. */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        //create new itemstack with all the info
        ItemStack pack = IronBackpacksAPI.applyPackInfo(new ItemStack(IronBackpacksAPI.BACKPACK_ITEM), BackpackInfo.fromTileEntity(this.getTileEntity(world, pos)));
        spawnAsEntity(world, pos, pack);

        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //create new itemstack with all the info
        ItemStack pack = IronBackpacksAPI.applyPackInfo(new ItemStack(IronBackpacksAPI.BACKPACK_ITEM), BackpackInfo.fromTileEntity(this.getTileEntity(world, pos)));
        drops.add(pack);

        super.getDrops(drops, world, pos, state, fortune);
    }

    //Uses custom JSON Model, which is not a full cube
    //Forge Note: You can override but not call if they are deprecated

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

    //VARIANT for the block state

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }


    // Other

    /** Correct orientation on placement */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

}

