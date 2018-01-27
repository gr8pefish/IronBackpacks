package gr8pefish.ironbackpacks.block;

import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Abstract class to bind a block to a TileEntity
 * Also extends {@link Block} to allow easy construction of the block.
 *
 * @param <TE> - {@link TileEntity}
 */
public abstract class AbstractBlockTileEntity<TE extends TileEntity> extends Block {

    /**
     * Sets up the material, unlocalized name, and adds it to the creative tab for the mod.
     *
     * @param material - The {@link Material} to set the block to
     * @param name - The unlocalized name
     */
    public AbstractBlockTileEntity(Material material, String name) {
        super(material);
        setUnlocalizedName(IronBackpacks.MODID + "."+ name);
        setCreativeTab(IronBackpacks.CREATIVE_TAB_IB);
    }

    /** Get the TileEntity's Class */
    public abstract Class<TE> getTileEntityClass();

    /** Get the specific TileEntity at a given location */
    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE)world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    /** Create a TileEntity with the given data */
    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);

}
