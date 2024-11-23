package com.agricraft.agricraft.api.requirement;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.plant.AgriPlant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public interface AgriGrowthCondition<T> {

	ResourceKey<Registry<AgriGrowthCondition<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AgriApi.MOD_ID, "growth_condition"));

	/**
	 * Checks the condition of the crop at the given position in the given world.
	 *
	 * @param crop     the crop for which to check this value
	 * @param level    the level the crop is in
	 * @param pos      the position in the world
	 * @param strength the strength stat of the crop
	 * @return the response.
	 */
	AgriGrowthResponse check(AgriCrop crop, Level level, BlockPos pos, int strength);

	/**
	 * Check the condition for a specific value
	 *
	 * @param plant    the plant to check the condition
	 * @param strength the strength of the crop
	 * @param value    the value to check the condition
	 * @return the response
	 */
	AgriGrowthResponse apply(AgriPlant plant, int strength, T value);

	// TODO: @Ketheroth void notMetDescription(Consumer<Component> consumer);

}
