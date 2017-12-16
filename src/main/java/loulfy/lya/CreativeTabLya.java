package loulfy.lya;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CreativeTabLya extends CreativeTabs
{
    public CreativeTabLya()
    {
        super("tabLya");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(LyaBlocks.Heater);
    }
}
