package loulfy.lya.server.proxy;

import loulfy.lya.CommonProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy
{
    @Override
    public void init()
    {
        super.init();
    }
}
