package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriRegistrable;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * Defines a gene from AgriCraft's genome for plants and seeds
 * Must be registered in the IAgriGeneRegistry to function correctly.
 *
 * @param <T> the type of the trait governed by this gene
 */
public interface AgriGene<T> extends AgriRegistrable {

	/**
	 * Create a chromosome for that gene, with the same value for its alleles
	 *
	 * @param value the value for both alleles
	 * @return the chromosome with the give value for both alleles
	 */
	Chromosome<T> chromosome(T value);

	/**
	 * Create a chromosome for that gene, with different values for its alleles.
	 * There are no order on the parameter, the allele dominance is computed automatically.
	 *
	 * @param first  the value for one allele
	 * @param second the value for the other allele
	 * @return the chromosome with the give value for each allele
	 */
	Chromosome<T> chromosome(T first, T second);

	/**
	 * Check if the allele of this gene is dominant to the other allele.
	 * @param allele the allele to check the dominance
	 * @param otherAllele the allele to check the dominance of the first allele against
	 * @return true if the allele is dominant to the other allele, false otherwise
	 */
	boolean isAlleleDominant(T allele, T otherAllele);

	/**
	 * @return The mutator object which controls mutations for this gene
	 */
	AgriGeneMutator<T> mutator();

	/**
	 * @return The codec for the underlying type of its alleles
	 */
	Codec<T> getCodec();
	StreamCodec<ByteBuf, T> getStreamCodec();

	/**
	 * Add components to the item tooltip
	 *
	 * @param tooltipComponents the list to add the components to
	 * @param trait             the value of the gene
	 */
	void addTooltip(List<Component> tooltipComponents, T trait);

	/**
	 * @return the dominant color as an argb int
	 */
	int getDominantColor();

	/**
	 * @return the recessive color as an argb int
	 */
	int getRecessiveColor();

}
