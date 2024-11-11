package com.agricraft.agricraft.common.forge.block.entity;

import com.agricraft.agricraft.api.config.CompatConfig;
import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.agricraft.agricraft.compat.botania.AgriHornHarvestable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaForgeCapabilities;

public class ForgeCropBlockEntity extends CropBlockEntity {

	public ForgeCropBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (ModList.get().isLoaded("botania") && CompatConfig.enableBotania) {
			if (cap == BotaniaForgeCapabilities.HORN_HARVEST) {
				return LazyOptional.of(() -> AgriHornHarvestable.INSTANCE).cast();
			}
		}
		return super.getCapability(cap, side);
	}

}
