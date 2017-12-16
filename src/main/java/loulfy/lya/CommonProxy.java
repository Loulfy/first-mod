package loulfy.lya;

import loulfy.lya.tile.TileEntityCube;
import loulfy.lya.tile.TileEntityHeater;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void init()
    {
        LyaBlocks.register();
        LyaItems.register();
    }

    public void tiles()
    {
        //GameRegistry.registerTileEntity(TileEntityCube.class, "Cube");
        GameRegistry.registerTileEntity(TileEntityHeater.class, "heater");
    }
}
