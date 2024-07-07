package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.crop.AgriCrop;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.stream.Stream;

/**
 * AgriCraft's cross breeding logic is executed by this class.
 *
 * It's active / default implementations can be obtained from the AgriCrossBreedEngine instance
 * Overriding implementations can be activated with the AgriCrossBreedEngine instance as well
 */
public interface AgriCrossBreedEngine {

	ParentSelector getSelector();

	CloneLogic getCloner();

	CombineLogic getCombiner();

	/**
	 * Handles a growth tick resulting in cross breeding, is only fired for cross crops.
	 * Any results from the success or failure of cross breeding, or clone, such as setting of the plant and genome must be fired from within this method as well.
	 * <p>
	 * The default {@link AgriCrossBreedEngine} allows its behaviour to be overridden:
	 * <ul>
	 *  <li>The selector represented by {@link ParentSelector} filters out valid crops which can contribute to cross breeding / cloning, and rolls for their probability to contribute
	 *  <li>The clone logic represented by {@link CloneLogic} handles the cloning of a genome (offspring from one single parent)
	 *  <li>The combine logic represented by {@link CombineLogic} handles the combining of two parent genomes (offspring from two different parents)
	 * </ul>
	 *
	 * @param crop       the crop for which the cross breeding tick has been fired
	 * @param neighbours A stream of the crop's neighbouring crops (this includes all crops, regardless if these contain plants, weeds, are fertile, or mature)
	 * @param random     pseudo-random generator to take decisions
	 * @return true if the cross breeding / spread succeeded, false if it failed
	 */
	boolean handleCrossBreedTick(AgriCrop crop, Stream<AgriCrop> neighbours, RandomSource random);
	/**
	 * Functional interface for selection logic
	 */
	@FunctionalInterface
	interface ParentSelector {

		/**
		 * Selects potential parent candidates from all neighbouring crops.
		 * <p>
		 * In case an empty list is returned, nothing will happen.
		 * In case a list with 1 crop is returned, a cloning event will be attempted
		 * In case a list with 2 or more crops is returned, a combining event will be attempted with the first two items in the list
		 *
		 * @param candidates all candidates
		 * @param random     pseudo-random generator for decision making
		 * @return ordered list of remaining candidates
		 */
		List<AgriCrop> selectAndOrder(Stream<AgriCrop> candidates, RandomSource random);

	}

	/**
	 * Functional interface for cloning logic
	 */
	@FunctionalInterface
	interface CloneLogic {

		/**
		 * Clones the parent genome to a new genome.
		 * <p>
		 * Note that in the default implementation this will be an exact copy.
		 * This, however, need not forcibly be the case, one could implement cloning logic which deteriorates the genes for instance
		 *
		 * @param crop   the crop onto which the plant is cloned
		 * @param parent the parent genome
		 * @param random pseudo-random generator for decision making
		 * @return the child genome
		 */
		AgriGenome clone(AgriCrop crop, AgriGenome parent, RandomSource random);

	}

	/**
	 * Functional interface for combining logic
	 */
	@FunctionalInterface
	interface CombineLogic {

		/**
		 * Combines the genomes of two parents into a child genome
		 *
		 * @param crop    the crop onto which the plant is crossbred
		 * @param parent1 the genome of the first parent
		 * @param parent2 the genome of the second parent
		 * @param random  pseudo-random generator for decision making
		 * @return the child genome
		 */
		AgriGenome combine(AgriCrop crop, AgriGenome parent1, AgriGenome parent2, RandomSource random);

	}

}
