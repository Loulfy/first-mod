package loulfy.lya.block;

import loulfy.lya.Lya;
import loulfy.lya.block.states.BlockStateMachine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachine extends Block
{

    public BlockMachine()
    {
        super(Material.IRON);
        setHardness(5F);
        setResistance(20F);
        setCreativeTab(Lya.tabLya);
    }

    public PropertyEnum<BlockStateMachine.MachineType> getType()
    {
        return (PropertyEnum<BlockStateMachine.MachineType>) getBlockState().getProperty("type");
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateMachine(this);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(BlockStateMachine.typeProperty, BlockStateMachine.MachineType.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockStateMachine.typeProperty).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativetabs, NonNullList<ItemStack> list)
    {
        for(BlockStateMachine.MachineType ore : BlockStateMachine.MachineType.values())
        {
            list.add(new ItemStack(item, 1, ore.ordinal()));
        }
    }
}
