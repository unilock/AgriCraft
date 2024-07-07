package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.stat.AgriStat;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public class GeneStat implements AgriGene<Integer> {

	private final Supplier<AgriStat> stat;

	public GeneStat(Supplier<AgriStat> stat) {
		this.stat = stat;
	}

	public AgriStat stat() {
		return stat.get();
	}

	@Override
	public Chromosome<Integer> chromosome(Integer value) {
		return new Chromosome<>(this, value);
	}

	@Override
	public Chromosome<Integer> chromosome(Integer first, Integer second) {
		return new Chromosome<>(this, first, second);
	}

	@Override
	public boolean isAlleleDominant(Integer allele, Integer otherAllele) {
		return allele >= otherAllele;
	}

	@Override
	public AgriGeneMutator<Integer> mutator() {
		return AgriApi.get().getMutationHandler().getStatMutator();
	}

	@Override
	public void addTooltip(List<Component> tooltipComponents, Integer trait) {
		this.stat.get().addTooltip(tooltipComponents::add, trait);
	}

	@Override
	public int getDominantColor() {
		return stat.get().getColor();
	}

	@Override
	public int getRecessiveColor() {
		int col = this.stat.get().getColor();
		int r = (int) ((col >> 16 & 255) * 0.6f);
		int g = (int) ((col >> 8 & 255) * 0.6f);
		int b = (int) ((col & 255) * 0.6f);
		return 0xFF << 24 | r << 16 | g << 8 | b & 0xFF;
	}

	@Override
	public <S> String encode(S value) {
		return "" + (int) value;
	}

	@Override
	public <S> S decode(String value) {
		return (S) Integer.valueOf(value);
	}

	@Override
	public String toString() {
		return "GeneStat{" +
				"stat=" + stat.get() +
				'}';
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GeneStat geneStat)) {
			return false;
		}

		return stat.get().equals(geneStat.stat.get());
	}

	@Override
	public int hashCode() {
		return stat.get().hashCode();
	}

}
