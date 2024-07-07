package com.agricraft.agricraft.common.genetic;

import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.genetic.AgriGene;
import com.agricraft.agricraft.api.genetic.AgriGeneMutator;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.api.genetic.Chromosome;
import com.agricraft.agricraft.api.genetic.GeneStat;
import com.agricraft.agricraft.api.registries.AgriCraftStats;
import com.agricraft.agricraft.api.stat.AgriStat;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class DefaultStatMutator implements AgriGeneMutator<Integer> {

	@Override
	public Chromosome<Integer> pickOrMutate(AgriCrop crop, AgriGene<Integer> gene, Integer first, Integer second, AgriGenome parent1, AgriGenome parent2, RandomSource random) {
		// return new gene pair with or without mutations, based on mutativity stat
		AgriStat mutativity = AgriCraftStats.MUTATIVITY.get();
		AgriStat stat = ((GeneStat) gene).stat();
		return new Chromosome<>(
				gene,
				this.rollAndExecuteMutation(first, mutativity, parent1.getMutativity().trait(), stat.getMin(), stat.getMax(), random),
				this.rollAndExecuteMutation(second, mutativity, parent2.getMutativity().trait(), stat.getMin(), stat.getMax(), random)
		);
	}

	protected Integer rollAndExecuteMutation(Integer allele, AgriStat mutativity, int statValue, int minValue, int maxValue, RandomSource random) {
		// Mutativity stat of 1 results in 30.25/45/24.75 probability of positive/no/negative mutation
		// Mutativity stat of 10 results in 100/0/0 probability of positive/no/negative mutation
		int max = mutativity.getMax();
		if (random.nextFloat() >= (1.0 - (double) statValue / max) / 2.0) {
			int delta = random.nextInt(max) < (max + statValue) / 2 ? 1 : -1;
			return Mth.clamp(allele + delta, minValue, maxValue);
		} else {
			return allele;
		}
	}

}
