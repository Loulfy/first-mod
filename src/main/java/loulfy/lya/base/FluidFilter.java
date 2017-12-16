package loulfy.lya.base;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class FluidFilter implements IFluidHandler
{
    private IFluidFilter tank;
    private Fluid fluid;

    public FluidFilter(IFluidFilter filter, String name)
    {
        tank = filter;
        fluid = FluidRegistry.getFluid(name);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(fluid != resource.getFluid()) return 0;
        return tank.fill(resource);
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }
}
