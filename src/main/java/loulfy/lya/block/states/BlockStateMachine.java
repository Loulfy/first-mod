package loulfy.lya.block.states;

import loulfy.lya.block.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockStateMachine extends BlockStateContainer
{
    public static final PropertyEnum<MachineType> typeProperty = PropertyEnum.create("type", MachineType.class);
    public static final PropertyBool activeProperty = PropertyBool.create("active");

    public BlockStateMachine(BlockMachine block)
    {
        super(block, typeProperty, activeProperty);
    }

    public enum MachineType implements IStringSerializable
    {
        HEATER_CASING("HeaterCasing", 0),
        HEATER_VALVE("HeaterValve", 1),
        HEATER_CONTROLLER("HeaterController", 2);

        public int meta;
        public String name;

        MachineType(String s, int metadata)
        {
            meta = metadata;
            name = s;
        }

        public static MachineType get(Block block, int meta)
        {
            if(block instanceof BlockMachine)
            {
                for(MachineType type : values())
                {
                    if(type.meta == meta)
                    {
                        return type;
                    }
                }
            }

            return null;
        }

        public static MachineType get(ItemStack stack)
        {
            return get(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
        }

        @Override
        public String getName()
        {
            return name().toLowerCase();
        }
    }

    public static class MachineBlockStateMapper extends StateMapperBase
    {

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state)
        {
            BlockMachine block = (BlockMachine) state.getBlock();
            MachineType type = state.getValue(block.getType());

            return new ModelResourceLocation("lya:" + type.getName(), "inventory");
        }
    }

    public static class MachineItemMeshDefinition implements ItemMeshDefinition
    {

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack)
        {
            MachineType type = MachineType.get(stack);
            return new ModelResourceLocation("lya:" + type.getName(), "inventory");
        }
    }
}
