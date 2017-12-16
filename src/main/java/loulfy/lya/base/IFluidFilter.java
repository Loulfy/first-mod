package loulfy.lya.base;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public interface IFluidFilter
{
    int fill(FluidStack stack);

    IFluidTankProperties[] getTankProperties();
}
