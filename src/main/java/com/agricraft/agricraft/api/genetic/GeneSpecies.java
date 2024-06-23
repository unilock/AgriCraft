package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class GeneSpecies implements AgriGene<String> {

	public static final String ID = "species";

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Chromosome<String> chromosome(String value) {
		return new Chromosome<>(this, value);
	}

	@Override
	public Chromosome<String> chromosome(String first, String second) {
		return new Chromosome<>(this, first, second);
	}

	@Override
	public boolean isAlleleDominant(String allele, String otherAllele) {
		if (allele.equals(otherAllele)) {
			return true;
		}
		// Fetch complexity of both plants
		int a = AgriApi.getMutationHandler().complexity(allele);
		int b = AgriApi.getMutationHandler().complexity(otherAllele);
		if(a == b) {
			// Equal complexity, therefore we use an arbitrary definition for dominance, which we will base on the plant id
			return allele.compareTo(otherAllele) < 0;
		}
		// Having more difficult obtain plants be dominant will be more challenging to deal with than having them recessive
		return a > b;
	}

	@Override
	public AgriGeneMutator<String> mutator() {
		return AgriMutationHandler.getInstance().getActivePlantMutator();
	}

	@Override
	public Codec<String> getCodec() {
		return Codec.STRING;
	}

	@Override
	public StreamCodec<ByteBuf, String> getStreamCodec() {
		return ByteBufCodecs.STRING_UTF8;
	}

	@Override
	public void addTooltip(List<Component> tooltipComponents, String trait) {
		tooltipComponents.add(Component.translatable("agricraft.gene.species").append(": " + trait).withStyle(ChatFormatting.DARK_GRAY));
	}

	@Override
	public int getDominantColor() {
		return 0xffbf007f;
	}

	@Override
	public int getRecessiveColor() {
		return 0xff7f00bf;
	}

	@Override
	public String toString() {
		return "GeneSpecies{}";
	}

}
