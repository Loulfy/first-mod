package loulfy.lya.tile;

import mekanism.api.Coord4D;
import mekanism.api.IHeatTransfer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;

final class CapabilityUtils {
    public static boolean hasCapability(ICapabilityProvider provider, Capability<?> cap, EnumFacing side) {
        if (provider == null || cap == null) return false;

        return provider.hasCapability(cap, side);
    }

    public static <T> T getCapability(ICapabilityProvider provider, Capability<T> cap, EnumFacing side) {
        if (provider == null || cap == null) return null;

        return provider.getCapability(cap, side);
    }
}

public class TileEntityCube extends TileEntity implements IHeatTransfer, ITickable
{
    public EnergyStorage power;

    @CapabilityInject(IHeatTransfer.class)
    private static Capability<IHeatTransfer> HEAT_TRANSFER_CAPABILITY = null;

    public TileEntityCube()
    {
        power = new EnergyStorage(10000, 100);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("energy"))
        {
            power.receiveEnergy(compound.getInteger("energy"), false);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", power.getEnergyStored());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) return true;
        if(capability == HEAT_TRANSFER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) return (T) power;
        if(capability == HEAT_TRANSFER_CAPABILITY) return (T) this;
        return super.getCapability(capability, facing);
    }

    @Override
    public double getTemp() {
        return 10000;
    }

    @Override
    public double getInverseConductionCoefficient() {
        return 5;
    }

    @Override
    public double getInsulationCoefficient(EnumFacing side) {
        return 1000;
    }

    @Override
    public void transferHeatTo(double heat) { }

    @Override
    public double[] simulateHeat() {
        return new double[]{1000};
    }

    @Override
    public double applyTemperatureChange() {
        return 1000;
    }

    @Override
    public boolean canConnectHeat(EnumFacing side) {
        return true;
    }

    @Override
    public IHeatTransfer getAdjacent(EnumFacing side) {
        TileEntity adj = Coord4D.get(this).offset(side).getTileEntity(world);

        if(CapabilityUtils.hasCapability(adj, HEAT_TRANSFER_CAPABILITY, side.getOpposite()))
        {
            return CapabilityUtils.getCapability(adj, HEAT_TRANSFER_CAPABILITY, side.getOpposite());
        }

        return null;
    }

    @Override
    public void update() {
        if(!world.isRemote)
        {
            //TileEntity adj = Coord4D.get(this).offset(EnumFacing.SOUTH).getTileEntity(world);

            /*
            if(CapabilityUtils.hasCapability(adj, CapabilityEnergy.ENERGY, EnumFacing.SOUTH.getOpposite()))
            {
                IEnergyStorage tile = CapabilityUtils.getCapability(adj, CapabilityEnergy.ENERGY, EnumFacing.SOUTH.getOpposite());
                int ep = power.extractEnergy(40, false);
                int ea = tile.receiveEnergy(ep, false);
                power.receiveEnergy(ea, false);
            }*/

            IHeatTransfer tile = getAdjacent(EnumFacing.UP);
            if(tile != null) tile.transferHeatTo(100);
        }
    }
}
