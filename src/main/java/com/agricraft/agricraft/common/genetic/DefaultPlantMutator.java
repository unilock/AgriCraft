package com.agricraft.agricraft.common.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.codecs.AgriMutation;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.genetic.AgriGene;
import com.agricraft.agricraft.api.genetic.AgriGeneMutator;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.api.genetic.Chromosome;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public class DefaultPlantMutator implements AgriGeneMutator<String> {

	@Override
	public Chromosome<String> pickOrMutate(AgriCrop crop, AgriGene<String> gene, String first, String second, AgriGenome parent1, AgriGenome parent2, RandomSource random) {
		// Search for matching mutations
		// order them randomly
		// fetch the first
		return AgriApi.get().getMutationsFromParents(first, second)
				// Find the first result, sorted by trigger result, but shuffled by mutation
				.min((a, b) -> this.sortAndShuffle(a, b, random))
				// map it to its child, or to nothing based on the mutation success rate
				.flatMap(m -> this.evaluate(m, random))
				// map the result to a new gene pair with either of its parents as second gene
				.map(plant -> new Chromosome<>(gene, plant, random.nextBoolean() ? first : second))
				// if no mutation was found or if the mutation was unsuccessful, return a gene pair of the parents
				.orElse(new Chromosome<>(gene, first, second));
	}


	protected int sortAndShuffle(AgriMutation a, AgriMutation b, RandomSource random) {
		// shuffle the mutations randomly
		return a == b ? 0 : random.nextBoolean() ? -1 : 1;
	}

	protected Optional<String> evaluate(AgriMutation mutation, RandomSource random) {
		// Evaluate mutation probability
		if (mutation.chance() > random.nextDouble()) {
			return Optional.of(mutation.child().toString());
		}
		// mutation failed
		return Optional.empty();
	}

}
