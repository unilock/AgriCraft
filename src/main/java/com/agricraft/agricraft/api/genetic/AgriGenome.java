package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.api.stat.AgriStat;
import com.agricraft.agricraft.api.stat.AgriStats;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A genome is a list of chromosome. There is one chromosome per gene.
 * Each chromosome has two alleles. The alleles are a form of the gene.
 */
public class AgriGenome {

	public static Codec<Chromosome<?>> CHROMOSOME_CODEC = AgriGenes.GENE_REGISTRY.byNameCodec().dispatch(Chromosome::gene, AgriGenome::createTypedCodec);
	public static StreamCodec<RegistryFriendlyByteBuf, Chromosome<?>> CHROMOSOME_STREAM_CODEC = ByteBufCodecs.registry(AgriGenes.GENE_REGISTRY_KEY).dispatch(Chromosome::gene, AgriGenome::createTypedStreamCodec);

	public static Codec<AgriGenome> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			CHROMOSOME_CODEC.listOf().fieldOf("chromosomes").forGetter(agriGenome -> agriGenome.chromosomes.values().stream().toList())
	).apply(instance, AgriGenome::new));
	public static StreamCodec<RegistryFriendlyByteBuf, AgriGenome> STREAM_CODEC = StreamCodec.composite(
			CHROMOSOME_STREAM_CODEC.apply(ByteBufCodecs.list()), genome -> genome.chromosomes.values().stream().toList(),
			AgriGenome::new
	);

	protected final Map<AgriGene<?>, Chromosome<?>> chromosomes;

	public AgriGenome(List<Chromosome<?>> chromosomes) {
		this.chromosomes = new HashMap<>();
		chromosomes.forEach(ch -> this.chromosomes.put(ch.gene(), ch));
	}

	public AgriGenome(AgriPlant plant) {
		this.chromosomes = new HashMap<>();
		GeneSpecies geneSpecies = AgriGenes.SPECIES.get();
		String id = AgriApi.getPlantId(plant).map(ResourceLocation::toString).orElse("");
		chromosomes.put(geneSpecies, geneSpecies.chromosome(id));
		for (DeferredHolder<AgriStat, ? extends AgriStat> entry : AgriStats.STATS.getEntries()) {
			AgriGenes.getStatGene(entry.get()).ifPresent(gene -> this.chromosomes.put(gene, gene.chromosome(entry.get().getMin())));
		}
	}

	private static <T> MapCodec<Chromosome<T>> createTypedCodec(AgriGene<T> gene) {
		return RecordCodecBuilder.mapCodec(instance -> instance.group(
				gene.getCodec().fieldOf("r").forGetter(Chromosome::recessive),
				gene.getCodec().fieldOf("d").forGetter(Chromosome::dominant)
		).apply(instance, (r, d) -> new Chromosome<>(gene, r, d)));
	}
	private static <T> StreamCodec<RegistryFriendlyByteBuf, Chromosome<T>> createTypedStreamCodec(AgriGene<T> gene) {
		return StreamCodec.composite(
				gene.getStreamCodec(), Chromosome::recessive,
				gene.getStreamCodec(), Chromosome::dominant,
				gene::chromosome
		);
	}

	public <T> Chromosome<T> getChromosome(AgriGene<T> gene) {
		return (Chromosome<T>) this.chromosomes.get(gene);
	}

	public Chromosome<Integer> getStatGene(AgriStat stat) {
		return this.getChromosome(AgriGenes.getStatGene(stat).get());
	}

	public Chromosome<String> species() {
		return this.getChromosome(AgriGenes.SPECIES.get());
	}

	public Chromosome<Integer> gain() {
		return this.getChromosome(AgriGenes.GAIN.get());
	}

	public Chromosome<Integer> getGrowth() {
		return this.getChromosome(AgriGenes.GROWTH.get());
	}

	public Chromosome<Integer> getStrength() {
		return this.getChromosome(AgriGenes.STRENGTH.get());
	}

	public Chromosome<Integer> getFertility() {
		return this.getChromosome(AgriGenes.FERTILITY.get());
	}

	public Chromosome<Integer> getResistance() {
		return this.getChromosome(AgriGenes.RESISTANCE.get());
	}

	public Chromosome<Integer> getMutativity() {
		return this.getChromosome(AgriGenes.MUTATIVITY.get());
	}

	public Collection<Chromosome<?>> chromosomes() {
		return this.chromosomes.values();
	}

	@SuppressWarnings("unchecked")
	public Collection<Chromosome<Integer>> getStatChromosomes() {
		// TODO: @Ketheroth remove the unchecked optional unwrap
		return AgriStats.STATS.getEntries().stream().map(stat -> AgriGenes.getStatGene(stat.get()).get()).map(this.chromosomes::get).map(c -> (Chromosome<Integer>) c).toList();
	}

	public void appendHoverText(List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		if (isAdvanced.isAdvanced()) {
			this.species().gene().addTooltip(tooltipComponents, this.species().trait());
		}
		this.getStatChromosomes().stream()
				.sorted(Comparator.comparing(pair -> pair.gene().getId()))
				.forEach(pair -> pair.gene().addTooltip(tooltipComponents, pair.trait()));
	}

	@Override
	public String toString() {
		return "AgriGenome{" +
				"chromosomes=" + chromosomes +
				'}';
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AgriGenome that)) {
			return false;
		}

		return chromosomes.equals(that.chromosomes);
	}

	@Override
	public int hashCode() {
		return chromosomes.hashCode();
	}

}
