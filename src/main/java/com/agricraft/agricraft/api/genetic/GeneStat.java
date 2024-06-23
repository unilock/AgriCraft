package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.stat.AgriStat;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class GeneStat implements AgriGene<Integer> {

	private final Holder<AgriStat> stat;

	public GeneStat(Holder<AgriStat> stat) {
		this.stat = stat;
	}

	@Override
	public String getId() {
		return stat.value().getId();
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
		return AgriMutationHandler.getInstance().getActiveStatMutator();
	}

	@Override
	public Codec<Integer> getCodec() {
		return Codec.INT;
	}

	@Override
	public StreamCodec<ByteBuf, Integer> getStreamCodec() {
		return ByteBufCodecs.INT;
	}

	@Override
	public void addTooltip(List<Component> tooltipComponents, Integer trait) {
		this.stat.value().addTooltip(tooltipComponents::add, trait);
	}

	@Override
	public int getDominantColor() {
		return stat.value().getColor();
	}

	@Override
	public int getRecessiveColor() {
		int col = this.stat.value().getColor();
		int r = (int) ((col >> 16 & 255) * 0.6f);
		int g = (int) ((col >> 8 & 255) * 0.6f);
		int b = (int) ((col & 255) * 0.6f);
		return 0xFF << 24 | r << 16 | g << 8 | b & 0xFF;
	}

	@Override
	public String toString() {
		return "GeneStat{" +
				"stat=" + stat +
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

		return stat.equals(geneStat.stat);
	}

	@Override
	public int hashCode() {
		return stat.hashCode();
	}

}
