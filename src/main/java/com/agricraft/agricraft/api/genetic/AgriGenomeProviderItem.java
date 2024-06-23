package com.agricraft.agricraft.api.genetic;

// TODO: @Ketheroth this api class uses non api classes
import com.agricraft.agricraft.common.registry.ModDataComponentTypes;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface AgriGenomeProviderItem {

	/**
	 * Change the genome of the plant.
	 * @param genome the new genome of the crop
	 */
	default void setGenome(ItemStack stack, AgriGenome genome) {
		stack.set(ModDataComponentTypes.GENOME, genome);
	}

	default Optional<AgriGenome> getGenome(ItemStack stack) {
		return Optional.ofNullable(stack.get(ModDataComponentTypes.GENOME));
	}

}
