package com.agricraft.agricraft.api.registries;

import com.agricraft.agricraft.api.codecs.AgriSoilCondition;
import com.agricraft.agricraft.api.requirement.AgriGrowthCondition;
import com.agricraft.agricraft.api.requirement.AgriSeason;
import com.agricraft.agricraft.api.requirement.BaseGrowthCondition;
import com.agricraft.agricraft.api.requirement.SoilGrowthCondition;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AgriCraftGrowthConditions {

	public static DeferredHolder<AgriGrowthCondition<?>, SoilGrowthCondition<AgriSoilCondition.Humidity>> HUMIDITY;
	public static DeferredHolder<AgriGrowthCondition<?>, SoilGrowthCondition<AgriSoilCondition.Acidity>> ACIDITY;
	public static DeferredHolder<AgriGrowthCondition<?>, SoilGrowthCondition<AgriSoilCondition.Nutrients>> NUTRIENTS;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<Integer>> LIGHT;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<BlockState>> BLOCK;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<Holder<Biome>>> BIOME;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<ResourceKey<Level>>> DIMENSION;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<AgriSeason>> SEASON;
	public static DeferredHolder<AgriGrowthCondition<?>, BaseGrowthCondition<FluidState>> FLUID;

}
