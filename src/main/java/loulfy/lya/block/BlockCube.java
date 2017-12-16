package loulfy.lya.block;

import loulfy.lya.Lya;
import loulfy.lya.tile.TileEntityCube;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCube extends Block
{
    public BlockCube()
    {
        super(Material.IRON);
        this.setCreativeTab(Lya.tabLya);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntityCube tile = (TileEntityCube) worldIn.getTileEntity(pos);
            playerIn.sendMessage(new TextComponentString("power : " + tile.power.getEnergyStored()));
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityCube();
    }
}
