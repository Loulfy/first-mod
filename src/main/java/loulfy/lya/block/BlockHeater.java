package loulfy.lya.block;

import loulfy.lya.Lya;
import loulfy.lya.block.states.BlockStateHeater;
import loulfy.lya.tile.TileEntityHeater;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public class BlockHeater extends Block
{
    public BlockHeater()
    {
        super(Material.IRON);
        setHardness(5F);
        setResistance(20F);
        setCreativeTab(Lya.tabLya);
        setLightOpacity(1);
        setLightLevel(0.f);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(BlockStateHeater.FACING, EnumFacing.NORTH)
                .withProperty(BlockStateHeater.ACTIVE, false)
        );
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateHeater(this);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tile = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        if(tile instanceof TileEntityHeater)
        {
            TileEntityHeater heater = (TileEntityHeater) tile;
            state = state.withProperty(BlockStateHeater.FACING, heater.getFacing());
            state = state.withProperty(BlockStateHeater.ACTIVE, heater.isActive());
        }
        return state;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityHeater();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileEntityHeater)
        {
            TileEntityHeater heater = (TileEntityHeater) tile;
            heater.setFacing(placer.getHorizontalFacing().getOpposite());
        }
    }

    /*
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
        {
            TileEntity tile = worldIn.getTileEntity(pos);
            if(tile instanceof TileEntityHeater)
            {
                TileEntityHeater heater = (TileEntityHeater) tile;
                //heater.toggle();
            }
        }
        return true;
    }*/
}
