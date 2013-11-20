package pixlepix.particlephysics.common.tile;

import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;

import java.lang.reflect.Constructor;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import thermalexpansion.api.item.IChargeableItem;
import universalelectricity.compatibility.Compatibility;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.electricity.ElectricityHelper;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.grid.IElectricityNetwork;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.tile.TileEntityElectrical;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cpw.mods.fml.common.Loader;

/**
 * This was taken from Universal Electricity and adapted for improvements
 * allowed by ASM.
 * 
 * @author micdoodle8, Calclavia
 * 
 * Code 'borrowed' from galacticraft
 * 
 */
public abstract class GCCoreTileEntityUniversalElectrical extends TileEntityElectrical
{
    protected boolean isAddedToEnergyNet;
    public Object bcPowerHandler;
    public float maxInputEnergy = 100;

    /**
     * Recharges electric item.
     */
    @Override
    public void recharge(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof IItemElectric)
            {
                super.recharge(itemStack);
            }
            else if (Compatibility.isIndustrialCraft2Loaded() && itemStack.getItem() instanceof ISpecialElectricItem)
            {
                ISpecialElectricItem electricItem = (ISpecialElectricItem) itemStack.getItem();
                IElectricItemManager manager = electricItem.getManager(itemStack);
                float energy = Math.max(this.getProvide(ForgeDirection.UNKNOWN) * Compatibility.IC2_RATIO, 0);
                energy = manager.charge(itemStack, (int) (energy * Compatibility.TO_IC2_RATIO), 0, false, false) * Compatibility.IC2_RATIO;
                this.provideElectricity(energy, true);
            }
            else if (Loader.isModLoaded("ThermalExpansion") && itemStack.getItem() instanceof IChargeableItem)
            {
                float accepted = ((IChargeableItem) itemStack.getItem()).receiveEnergy(itemStack, this.getProvide(ForgeDirection.UNKNOWN) * Compatibility.BC3_RATIO, true);
                this.provideElectricity(accepted, true);
            }
        }
    }

    @Override
    public boolean produceUE(ForgeDirection outputDirection)
    {
        if (!this.worldObj.isRemote && outputDirection != null && outputDirection != ForgeDirection.UNKNOWN)
        {
            float provide = this.getProvide(outputDirection);

            if (provide > 0)
            {
                TileEntity outputTile = VectorHelper.getConnectorFromSide(this.worldObj, new Vector3(this), outputDirection);
                IElectricityNetwork outputNetwork = ElectricityHelper.getNetworkFromTileEntity(outputTile, outputDirection);
                if (outputNetwork != null)
                {
                    ElectricityPack powerRequest = outputNetwork.getRequest(this);

                    if (powerRequest.getWatts() > 0)
                    {
                        ElectricityPack sendPack = ElectricityPack.min(ElectricityPack.getFromWatts(this.getEnergyStored(), this.getVoltage()), ElectricityPack.getFromWatts(provide, this.getVoltage()));
                        float rejectedPower = outputNetwork.produce(sendPack, this);
                        this.provideElectricity(sendPack.getWatts() - rejectedPower, true);
                        return true;
                    }
                }
                else if (outputTile instanceof IElectrical)
                {
                    float requestedEnergy = ((IElectrical) outputTile).getRequest(outputDirection.getOpposite());

                    if (requestedEnergy > 0)
                    {
                        ElectricityPack sendPack = ElectricityPack.min(ElectricityPack.getFromWatts(this.getEnergyStored(), this.getVoltage()), ElectricityPack.getFromWatts(provide, this.getVoltage()));
                        float acceptedEnergy = ((IElectrical) outputTile).receiveElectricity(outputDirection.getOpposite(), sendPack, true);
                        this.setEnergyStored(this.getEnergyStored() - acceptedEnergy);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Discharges electric item.
     */
    @Override
    public void discharge(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof IItemElectric)
            {
                super.discharge(itemStack);
            }
            else if (Compatibility.isIndustrialCraft2Loaded() && itemStack.getItem() instanceof ISpecialElectricItem)
            {
                ISpecialElectricItem electricItem = (ISpecialElectricItem) itemStack.getItem();

                if (electricItem.canProvideEnergy(itemStack))
                {
                    IElectricItemManager manager = electricItem.getManager(itemStack);
                    float energy = Math.max(this.getRequest(ForgeDirection.UNKNOWN) * Compatibility.IC2_RATIO, 0);
                    energy = manager.discharge(itemStack, (int) (energy * Compatibility.TO_IC2_RATIO), 0, false, false);
                    this.receiveElectricity(energy, true);
                }
            }
            else if (Loader.isModLoaded("ThermalExpansion") && itemStack.getItem() instanceof IChargeableItem)
            {
                float given = ((IChargeableItem) itemStack.getItem()).transferEnergy(itemStack, this.getRequest(ForgeDirection.UNKNOWN) * Compatibility.BC3_RATIO, true);
                this.receiveElectricity(given, true);
            }
        }
    }

    @Override
    public void initiate()
    {
        super.initiate();
        this.initBuildCraft();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        // Register to the IC2 Network
        if (!this.worldObj.isRemote)
        {
            if (!this.isAddedToEnergyNet)
            {
                this.initIC();
            }

            if (this.bcPowerHandler == null)
            {
                this.initBuildCraft();
            }

            if (Compatibility.isBuildcraftLoaded())
            {
                PowerHandler handler = (PowerHandler) this.bcPowerHandler;

                if (handler.getEnergyStored() > 0)
                {
                    /**
                     * Cheat BuildCraft powerHandler and always empty energy
                     * inside of it.
                     */
                    this.receiveElectricity(handler.getEnergyStored() * Compatibility.BC3_RATIO, true);
                    handler.setEnergy(0);
                }
            }
        }
    }

    @Override
    public void produce()
    {
        if (!this.worldObj.isRemote)
        {
            for (ForgeDirection outputDirection : this.getOutputDirections())
            {
                if (outputDirection != ForgeDirection.UNKNOWN)
                {
                    if (!this.produceUE(outputDirection))
                    {
                        this.produceBuildCraft(outputDirection);
                    }
                }
            }
        }
    }

    public boolean produceBuildCraft(ForgeDirection outputDirection)
    {
        if (!this.worldObj.isRemote && outputDirection != null && outputDirection != ForgeDirection.UNKNOWN)
        {
            float provide = this.getProvide(outputDirection);

            if (this.getEnergyStored() >= provide && provide > 0)
            {
                if (Compatibility.isBuildcraftLoaded())
                {
                    TileEntity tileEntity = new Vector3(this).modifyPositionFromSide(outputDirection).getTileEntity(this.worldObj);

                    if (tileEntity instanceof IPowerReceptor)
                    {
                        PowerReceiver receiver = ((IPowerReceptor) tileEntity).getPowerReceiver(outputDirection.getOpposite());

                        if (receiver != null)
                        {
                            if (receiver.powerRequest() > 0)
                            {
                                float bc3Provide = provide * Compatibility.TO_BC_RATIO;
                                float energyUsed = Math.min(receiver.receiveEnergy(Type.MACHINE, bc3Provide, outputDirection.getOpposite()), bc3Provide);
                                this.provideElectricity(energyUsed * Compatibility.TO_BC_RATIO, true);
                            }
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * IC2 Methods
     */
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
    {
        return this.getInputDirections().contains(direction);
    }

   
    public double getOfferedEnergy()
    {
        return this.getProvide(ForgeDirection.UNKNOWN) * Compatibility.TO_IC2_RATIO;
    }

    public void drawEnergy(double amount)
    {
        this.provideElectricity((float) amount * Compatibility.IC2_RATIO, true);
    }

    @Override
    public void invalidate()
    {
        this.unloadTileIC2();
        super.invalidate();
    }

    @Override
    public void onChunkUnload()
    {
        this.unloadTileIC2();
        super.onChunkUnload();
    }

    protected void initIC()
    {
        if (Compatibility.isIndustrialCraft2Loaded() && !this.worldObj.isRemote)
        {
            try
            {
                Class<?> tileLoadEvent = Class.forName("ic2.api.energy.event.EnergyTileLoadEvent");
                Class<?> energyTile = Class.forName("ic2.api.energy.tile.IEnergyTile");
                Constructor<?> constr = tileLoadEvent.getConstructor(energyTile);
                Object o = constr.newInstance(this);

                if (o != null && o instanceof Event)
                {
                    MinecraftForge.EVENT_BUS.post((Event) o);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        this.isAddedToEnergyNet = true;
    }

    private void unloadTileIC2()
    {
        if (this.isAddedToEnergyNet && this.worldObj != null)
        {
            if (Compatibility.isIndustrialCraft2Loaded() && !this.worldObj.isRemote)
            {
                try
                {
                    Class<?> tileLoadEvent = Class.forName("ic2.api.energy.event.EnergyTileUnloadEvent");
                    Class<?> energyTile = Class.forName("ic2.api.energy.tile.IEnergyTile");
                    Constructor<?> constr = tileLoadEvent.getConstructor(energyTile);
                    Object o = constr.newInstance(this);

                    if (o != null && o instanceof Event)
                    {
                        MinecraftForge.EVENT_BUS.post((Event) o);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            this.isAddedToEnergyNet = false;
        }
    }

    public double demandedEnergyUnits()
    {
        return Math.ceil(this.getRequest(ForgeDirection.UNKNOWN) * Compatibility.TO_IC2_RATIO);
    }

    public double injectEnergyUnits(ForgeDirection direction, double amount)
    {
        if (this.getInputDirections().contains(direction))
        {
            float convertedEnergy = (float) (amount * Compatibility.IC2_RATIO);
            ElectricityPack toSend = ElectricityPack.getFromWatts(convertedEnergy, this.getVoltage());
            float receive = this.receiveElectricity(direction, toSend, true);

            // Return the difference, since injectEnergy returns left over
            // energy, and
            // receiveElectricity returns energy used.
            return Math.round(amount - receive * Compatibility.TO_IC2_RATIO);
        }

        return amount;
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction)
    {
        return receiver instanceof IEnergyTile && this.getOutputDirections().contains(direction);
    }

    public int getMaxSafeInput()
    {
        return Integer.MAX_VALUE;
    }

    /**
     * BuildCraft power support
     */
    public void initBuildCraft()
    {
        if (!Compatibility.isBuildcraftLoaded())
        {
            return;
        }

        if (this.bcPowerHandler == null)
        {
            this.bcPowerHandler = new PowerHandler((IPowerReceptor) this, Type.MACHINE);
        }
        ((PowerHandler) this.bcPowerHandler).configure(0, this.maxInputEnergy, 0, (int) Math.ceil(this.getMaxEnergyStored() * Compatibility.BC3_RATIO));
    }

    public PowerReceiver getPowerReceiver(ForgeDirection side)
    {
        this.initBuildCraft();
        return ((PowerHandler) this.bcPowerHandler).getPowerReceiver();
    }

    public void doWork(PowerHandler workProvider)
    {

    }

    public World getWorld()
    {
        return this.getWorldObj();
    }
}