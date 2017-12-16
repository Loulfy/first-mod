package loulfy.lya.item;

import loulfy.lya.block.states.BlockStateMachine.MachineType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachine extends ItemBlock
{
    public Block metaBlock;

    public ItemBlockMachine(Block block)
    {
        super(block);
        metaBlock = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(MachineType.get(stack) != null)
        {
            return getUnlocalizedName() + "." + MachineType.get(stack).name;
        }

        return "null";
    }
}
