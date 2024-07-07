package com.agricraft.agricraft.api.requirement;

import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.mojang.datafixers.util.Function3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.function.BiFunction;

public class BaseGrowthCondition<T> implements AgriGrowthCondition<T> {

	private final Function3<AgriPlant, Integer, T, AgriGrowthResponse> response;
	private final BiFunction<Level, BlockPos, T> getter;

	public BaseGrowthCondition(Function3<AgriPlant, Integer, T, AgriGrowthResponse> response, BiFunction<Level, BlockPos, T> getter) {
		this.response = response;
		this.getter = getter;
	}

	@Override
	public AgriGrowthResponse check(AgriCrop crop, Level level, BlockPos pos, int strength) {
		return this.response.apply(crop.getPlant(), strength, this.getter.apply(level, pos));
	}

	@Override
	public AgriGrowthResponse apply(AgriPlant plant, int strength, T value) {
		return this.response.apply(plant, strength, value);
	}

}
