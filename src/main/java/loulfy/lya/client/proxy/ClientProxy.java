package loulfy.lya.client.proxy;

import loulfy.lya.CommonProxy;
import loulfy.lya.LyaBlocks;
import loulfy.lya.block.states.BlockStateHeater.HeaterBlockStateMapper;
import loulfy.lya.block.states.BlockStateMachine.MachineItemMeshDefinition;
import loulfy.lya.block.states.BlockStateMachine.MachineBlockStateMapper;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    private static final IStateMapper heaterMapper = new HeaterBlockStateMapper();
    private static final IStateMapper machineMapper = new MachineBlockStateMapper();
    private static final ItemMeshDefinition machineMesher = new MachineItemMeshDefinition();

    @Override
    public void init()
    {
        super.init();

        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LyaBlocks.Cube), 0, new ModelResourceLocation("lya:cube", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LyaBlocks.Heater), 0, new ModelResourceLocation("lya:heater", "inventory"));
        //ModelLoader.setCustomStateMapper(LyaBlocks.Heater, heaterMapper);

        //ModelLoader.setCustomStateMapper(LyaBlocks.Machine, machineMapper);
        //ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(LyaBlocks.Machine), machineMesher);

        /*
        List<ModelResourceLocation> models = new ArrayList<ModelResourceLocation>();
        for(MachineType type : MachineType.values())
        {
            models.add(new ModelResourceLocation("lya:" + type.getName(), "inventory"));
            ModelLoader.registerItemVariants(Item.getItemFromBlock(LyaBlocks.Machine), models.toArray(new ModelResourceLocation[] {}));
            //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LyaBlocks.Machine), type.ordinal(), new ModelResourceLocation("lya:" + type.getName(), "inventory"));
        }*/
    }
}
