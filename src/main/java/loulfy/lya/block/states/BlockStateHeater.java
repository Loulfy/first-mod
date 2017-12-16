package loulfy.lya.block.states;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class BlockStateHeater extends BlockStateContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockStateHeater(Block block)
    {
        super(block, FACING, ACTIVE);
    }

    public static class HeaterBlockStateMapper extends StateMapperBase
    {

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state)
        {
            EnumFacing facing = state.getValue(BlockStateHeater.FACING);
            boolean active = state.getValue(BlockStateHeater.ACTIVE);

            ResourceLocation location = new ResourceLocation("lyx", "heater");

            return new ModelResourceLocation(new ResourceLocation("lya:heater"), "active="+(active ? "true" : "false")+",facing="+facing.getName());
        }
    }
}
