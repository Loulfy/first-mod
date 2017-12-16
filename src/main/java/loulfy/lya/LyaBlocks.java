package loulfy.lya;

import loulfy.lya.block.BlockCube;
import loulfy.lya.block.BlockHeater;
import loulfy.lya.block.BlockMachine;

import loulfy.lya.item.ItemBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder("lya")
public class LyaBlocks
{
    //public static Block Cube = new BlockCube();
    //public static Block Machine = new BlockMachine();
    public static Block Heater = new BlockHeater();

    public static void register()
    {
        //GameRegistry.register(init(Cube, "cube"));
        //GameRegistry.register(init(Machine, "machine"));
        GameRegistry.register(init(Heater, "heater"));

        //GameRegistry.register(LyaItems.init(new ItemBlock(Cube), "cube"));
        //GameRegistry.register(LyaItems.init(new ItemBlockMachine(Machine), "machine"));
        GameRegistry.register(LyaItems.init(new ItemBlock(Heater), "heater"));
    }

    public static Block init(Block block, String name)
    {
        return block.setUnlocalizedName(name).setRegistryName("lya:" + name);
    }
}
