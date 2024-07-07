package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;

/**
 * Central class which controls the mutation logic of AgriCraft, which is controlled by three sub-classes:
 * <ul>
 *     <li>The first is the {@link AgriCrossBreedEngine}, which handles the spreading / cross breeding logic between crops in the world
 *     <li>The other two are {@link AgriGeneMutator} objects for the plant species and stats respectively
 * </ul>
 * Any of these implementations can be obtained, replaced, overridden, etc... via the API
 * The instance of this class can be obtained via {@link AgriApi#getMutationHandler()}
 */
public interface AgriMutationHandler {

	AgriCrossBreedEngine getCrossBreedEngine();

	AgriGeneMutator<String> getPlantMutator();

	AgriGeneMutator<Integer> getStatMutator();

	/**
	 * Calculates the complexity for a plant, the deeper down the mutation tree, the complexer a plant is.
	 * By default, plants with higher complexity are implemented to be dominant, from a genetics point of view,
	 * relative to plants with lower complexities.
	 *
	 * @param plant the plant
	 * @return integer value representing the complexity
	 */
	int complexity(String plant);

}
