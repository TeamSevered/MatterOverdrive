package matteroverdrive.container;

import matteroverdrive.container.matter_network.ContainerTaskQueueMachine;
import matteroverdrive.machines.replicator.TileEntityMachineReplicator;
import matteroverdrive.util.MOContainerHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Simeon on 12/27/2015.
 */
public class ContainerReplicator extends ContainerTaskQueueMachine<TileEntityMachineReplicator>
{
    private int patternReplicateCount;

    public ContainerReplicator(InventoryPlayer inventory, TileEntityMachineReplicator machine)
    {
        super(inventory, machine);
    }

    @Override
    public void init(InventoryPlayer inventory)
    {
        addAllSlotsFromInventory(machine.getInventoryContainer());
        MOContainerHelper.AddPlayerSlots(inventory, this, 45, 89, true, true);
    }

    @Override
    public void onCraftGuiOpened(ICrafting icrafting)
    {
        super.onCraftGuiOpened(icrafting);
        icrafting.sendProgressBarUpdate(this, 1, this.machine.getTaskReplicateCount());
        icrafting.sendProgressBarUpdate(this,2,this.machine.getEnergyDrainPerTick());
        icrafting.sendProgressBarUpdate(this,3,this.machine.getEnergyDrainMax());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.patternReplicateCount != this.machine.getTaskReplicateCount()) {
                icrafting.sendProgressBarUpdate(this, 1, this.machine.getTaskReplicateCount());
            }
        }

        this.patternReplicateCount = this.machine.getTaskReplicateCount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot,int newValue)
    {
        super.updateProgressBar(slot,newValue);
        switch (slot)
        {
            case 1:
                patternReplicateCount = newValue;
                break;
        }
    }

    public int getPatternReplicateCount()
    {
        return patternReplicateCount;
    }
}