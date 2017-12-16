package loulfy.lya;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "lya", version = "0.1")
public class Lya
{
    @SidedProxy(serverSide = "loulfy.lya.server.proxy.ServerProxy", clientSide = "loulfy.lya.client.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static CreativeTabLya tabLya = new CreativeTabLya();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.tiles();
    }
}
