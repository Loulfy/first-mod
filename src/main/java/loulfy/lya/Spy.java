package loulfy.lya;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;

@Mod.EventBusSubscriber
public class Spy
{
    @SubscribeEvent
    public static void playerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        System.out.println("OUUUUUUUUUUUUUUUUUUUT");
        System.out.println(event.player.getName());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Jedis jedis = new Jedis();
        jedis.zadd("dnstech", timestamp.getTime(), event.player.getName());
        jedis.close();

    }

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        System.out.println("HEY!");
    }
}
