package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Defines a gene from AgriCraft's genome for plants and seeds
 * Must be registered in the IAgriGeneRegistry to function correctly.
 *
 * @param <T> the type of the trait governed by this gene
 */
public interface AgriGene<T> {

	ResourceKey<Registry<AgriGene<?>>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("agricraft", "gene"));

	Codec<AgriGene<?>> CODEC = ResourceLocation.CODEC.comapFlatMap(
			s -> {
				AgriGene<?> gene = AgriApi.get().getGeneRegistry().get(s);
				if (gene == null) {
					return DataResult.error(() -> "The gene " + s + " is not present in the gene registry");
				} else {
					return DataResult.success(gene);
				}
			},
			AgriGene::getId
	);

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
	 *
	 * @param allele      the allele to check the dominance
	 * @param otherAllele the allele to check the dominance of the first allele against
	 * @return true if the allele is dominant to the other allele, false otherwise
	 */
	boolean isAlleleDominant(T allele, T otherAllele);

	/**
	 * @return The mutator object which controls mutations for this gene
	 */
	AgriGeneMutator<T> mutator();

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

	/**
	 * @return its id in the gene registry
	 */
	default ResourceLocation getId() {
		return AgriApi.get().getGeneRegistry().getKey(this);
	}

	/**
	 * Encode it's underlying value as a string for use in codecs
	 * @param value the value to convert to string
	 * @return the string representing the value
	 * @param <S> the type of the value
	 */
	<S> String encode(S value);

	/**
	 * Decode it's underlying value from a string for use in codecs
	 * @param value the string value to decode from
	 * @return the decoded value
	 * @param <S> the type of the decoded value
	 */
	<S> S decode(String value);

}
