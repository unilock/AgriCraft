package com.infinityraider.agricraft.tiles.irrigation;

// Imports would go here, if there were any.
import com.agricraft.agricore.config.AgriConfigCategory;
import com.agricraft.agricore.config.AgriConfigurable;
import com.agricraft.agricore.core.AgriCore;
import com.infinityraider.agricraft.api.v1.misc.IAgriFluidComponent;
import com.infinityraider.agricraft.reference.AgriCraftConfig;
import com.infinityraider.infinitylib.utility.WorldHelper;
import java.util.function.Consumer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityTank extends TileEntityChannel implements IFluidHandler {

    public static final int TANK_FLUID_CAPACITY = 16_000;
    public static final int TANK_FLUID_HEIGHT_MIN = 0_000;
    public static final int TANK_FLUID_HEIGHT_MAX = 1_000;
    public static final int TANK_FLUID_SYNC_THRESHOLD = 250;
    public static final long TANK_FLUID_SYNC_TIMEOUT = 1000;

    // Just a basic delegating constructor.
    public TileEntityTank() {
        this(TANK_FLUID_CAPACITY, TANK_FLUID_HEIGHT_MIN, TANK_FLUID_HEIGHT_MAX, TANK_FLUID_SYNC_THRESHOLD, TANK_FLUID_SYNC_TIMEOUT);
    }

    public TileEntityTank(int fluidCapacity, int fluidHeightMin, int fluidHeightMax, int fluidSyncThreshold, long fluidSyncTimeout) {
        super(fluidCapacity, fluidHeightMin, fluidHeightMax, fluidSyncThreshold, fluidSyncTimeout);
    }

    @Override
    public void update() {
        // Fill from rainfall.
        if (AgriCraftConfig.fillFromRainfall) {
            if (this.world.isRainingAt(this.pos.up())) {
                this.fluidAmount = this.fluidAmount + 1;
            }
        }

        // Fill from flowing water.
        if (AgriCraftConfig.fillFromFlowingWater) {
            final Block block = this.world.getBlockState(this.pos.up()).getBlock();
            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
                this.fluidAmount = this.fluidAmount + 1;
            }
        }

        // Call super function.
        super.update();
    }

    @Override
    protected byte classifyConnection(EnumFacing side) {
        final IAgriFluidComponent component = WorldHelper.getTile(world, pos.offset(side), IAgriFluidComponent.class).orElse(null);
        if (component == null) {
            return 0;
        } else if (component instanceof TileEntityTank) {
            return 2;
        } else if (side.getAxis().isHorizontal()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{};
    }

    @Override
    public int fill(FluidStack fs, boolean doFill) {
        if (fs.getFluid() == FluidRegistry.WATER) {
            return fs.amount - this.acceptFluid(1_000, fs.amount, true, !doFill);
        } else {
            return 0;
        }
    }

    @Override
    public FluidStack drain(FluidStack fs, boolean doDrain) {
        if (fs.getFluid() == FluidRegistry.WATER) {
            return drain(fs.amount, doDrain);
        } else {
            return new FluidStack(fs.getFluid(), 0);
        }
    }

    @Override
    public FluidStack drain(int amount, boolean doDrain) {
        // The amount consumed.
        final int drainedAmount;

        // Determine amount consumed.
        if (this.fluidAmount > amount) {
            drainedAmount = amount;
        } else {
            drainedAmount = this.fluidAmount;
        }

        // If the remainder doesn't equal input, and we are not simulating, then we need to update.
        if (doDrain && drainedAmount > 0) {
            // Update the fluid amount.
            this.fluidAmount = this.fluidAmount - drainedAmount;
            // Mark the component as dirty as it changed.
            this.world.markChunkDirty(pos, this);
        }

        // Return a new fluid stack with consumed amount.
        return new FluidStack(FluidRegistry.WATER, drainedAmount);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
                || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

}
