package loulfy.lya.tile;

import loulfy.lya.LyaBlocks;
import loulfy.lya.block.BlockHeater;
import loulfy.lya.block.states.BlockStateHeater;
import loulfy.lya.capabilities.Capabilities;
import mekanism.api.Coord4D;
import mekanism.api.IHeatTransfer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

@Optional.Interface(iface = "mekanism.api.IHeatTransfer", modid = "mekanism")
public class TileEntityHeater extends TileEntity implements ITickable, IFluidHandler, IHeatTransfer
{
    private EnumFacing facing;
    private boolean active;
    private FluidTank tank;
    private Fluid fluid;

    public TileEntityHeater()
    {
        facing = EnumFacing.NORTH;
        active = false;
        tank = new FluidTank(10000);
        fluid = FluidRegistry.getFluid("biodiesel");
    }

    public void setFacing(EnumFacing side)
    {
        this.facing = side;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void toggle()
    {
        this.active = !this.active;
        System.out.println("toggle:"+ (active ? "true" : "false"));
    }

    public boolean isActive() {
        return active;
    }

    private void handleSync(boolean renderUpdate)
    {
        // some tile entity field changed that the client needs to know about
        // if on server, sync TE data to client; if on client, possibly mark the TE for re-render
        if (!world.isRemote) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos)
                    .withProperty(BlockStateHeater.FACING, facing)
                    .withProperty(BlockStateHeater.ACTIVE, active), 3);
            world.notifyLightSet(pos);
        } else if (world.isRemote && renderUpdate) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        System.out.println(compound.toString());
        if (compound.hasKey("facing"))
        {
            facing = EnumFacing.getFront(compound.getInteger("facing"));
            active = compound.getBoolean("active");
            tank.readFromNBT(compound);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("facing", facing.getIndex());
        compound.setBoolean("active", active);
        tank.writeToNBT(compound);
        compound = super.writeToNBT(compound);
        //System.out.println("WRITE:" + (world.isRemote ? "CLIENT" : "SERVER"));
        //System.out.println(compound.toString());
        return compound;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        //System.out.println((world.isRemote ? "CLIENT" : "SERVER") + " SEND PACKET:"+facing.getName());
        nbt.setInteger("facing", facing.getIndex());
        nbt.setBoolean("active", active);
        return new SPacketUpdateTileEntity(pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        if(pkt.getTileEntityType() == 0)
        {
            NBTTagCompound nbt = pkt.getNbtCompound();
            facing = EnumFacing.getFront(nbt.getInteger("facing"));
            active = nbt.getBoolean("active");
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public void update() {
        handleSync(true);
        if(!world.isRemote)
        {
            //IBlockState state = world.getBlockState(pos);
            //world.notifyBlockUpdate(pos, state, state, 3);
            //world.markBlockRangeForRenderUpdate(pos, pos);
            FluidStack s = tank.drain(10, false);
            if(s != null && s.amount == 10)
            {
                tank.drain(10, true);
                active = true;
                for(EnumFacing side : EnumFacing.values())
                {
                    IHeatTransfer tile = getAdjacent(side);
                    if(tile != null && side != facing) tile.transferHeatTo(10);
                }
            }
            else
            {
                active = false;
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.facing != facing && (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || capability == Capabilities.HEAT || super.hasCapability(capability, facing));
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this;
        if(capability == Capabilities.HEAT) return (T) this;
        return super.getCapability(capability, facing);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(fluid != resource.getFluid()) return 0;
        return tank.fill(resource, doFill);
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

    @Override
    public double getTemp() {
        return 0;
    }

    @Override
    public double getInverseConductionCoefficient() {
        return 0;
    }

    @Override
    public double getInsulationCoefficient(EnumFacing side) {
        return 0;
    }

    @Override
    public void transferHeatTo(double heat) {

    }

    @Override
    public double[] simulateHeat() {
        return new double[0];
    }

    @Override
    public double applyTemperatureChange() {
        return 0;
    }

    @Override
    public boolean canConnectHeat(EnumFacing side) {
        return side != facing;
    }

    @Override
    public IHeatTransfer getAdjacent(EnumFacing side)
    {
        TileEntity adj = Coord4D.get(this).offset(side).getTileEntity(world);
        if(CapabilityUtils.hasCapability(adj, Capabilities.HEAT, side.getOpposite()))
        {
            return CapabilityUtils.getCapability(adj, Capabilities.HEAT, side.getOpposite());
        }
        return null;
    }
}
