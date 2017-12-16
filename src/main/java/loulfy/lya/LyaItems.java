package loulfy.lya;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder("lya")
public class LyaItems
{
    public static void register()
    {

    }

    public static Item init(Item item, String name)
    {
        return item.setUnlocalizedName(name).setRegistryName("lya:" + name);
    }
}
