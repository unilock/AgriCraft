package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.api.registries.AgriCraftGenes;
import com.agricraft.agricraft.api.stat.AgriStat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;

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

	public static Codec<AgriGene<?>> GENE_CODEC = ResourceLocation.CODEC.comapFlatMap(
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
	public static StreamCodec<ByteBuf, AgriGene<?>> GENE_STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(resourceLocation -> AgriApi.get().getGeneRegistry().get(resourceLocation), AgriGene::getId);

	public static Codec<AgriGenome> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.unboundedMap(GENE_CODEC, Codec.STRING.listOf()).fieldOf("chromosomes").forGetter(genome -> encode(genome.chromosomes))
	).apply(instance, (e) -> new AgriGenome(decode(e))));

	public static StreamCodec<ByteBuf, AgriGenome> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.map(HashMap::new,
					GENE_STREAM_CODEC,
					ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())
			), genome -> encode(genome.chromosomes),
			map -> new AgriGenome(decode(map))
	);


	protected final Map<AgriGene<?>, Chromosome<?>> chromosomes;

	public AgriGenome(List<Chromosome<?>> chromosomes) {
		this.chromosomes = new HashMap<>();
		chromosomes.forEach(ch -> this.chromosomes.put(ch.gene(), ch));
	}

	public AgriGenome(AgriPlant plant) {
		this.chromosomes = new HashMap<>();
		GeneSpecies geneSpecies = AgriCraftGenes.SPECIES.get();
		String id = plant.getId().map(ResourceLocation::toString).orElse("");
		chromosomes.put(geneSpecies, geneSpecies.chromosome(id));
		AgriApi.get().getStatRegistry().holders()
				.forEach(stat -> {
					// this should work, until someone change the type of the gene
					GeneStat gene = (GeneStat) AgriApi.get().getGeneRegistry().get(stat.key().location());
					if (gene != null) {
						this.chromosomes.put(gene, gene.chromosome(stat.value().getMin()));
					}
				});
	}

	public AgriGenome(Map<AgriGene<?>, Chromosome<?>> map) {
		this.chromosomes = map;
	}

	private static Map<AgriGene<?>, List<String>> encode(Map<AgriGene<?>, Chromosome<?>> chromosomes) {
		Map<AgriGene<?>, List<String>> map = new HashMap<>();
		chromosomes.forEach(((gene, chromosome) -> map.put(gene, List.of(gene.encode(chromosome.recessive()), gene.encode(chromosome.dominant())))));
		return map;
	}

	private static Map<AgriGene<?>, Chromosome<?>> decode(Map<AgriGene<?>, List<String>> chromosomes) {
		Map<AgriGene<?>, Chromosome<?>> map = new HashMap<>();
		chromosomes.forEach(((gene, chromosome) -> map.put(gene, gene.chromosome(gene.decode(chromosome.get(0)), gene.decode(chromosome.get(1))))));
		return map;
	}

	public <T> Chromosome<T> getChromosome(AgriGene<T> gene) {
		return (Chromosome<T>) this.chromosomes.get(gene);
	}

	public Chromosome<Integer> getStatChromosome(AgriStat stat) {
		return this.getChromosome(stat.getGene());
	}

	public Chromosome<String> species() {
		return this.getChromosome(AgriCraftGenes.SPECIES.value());
	}

	public Chromosome<Integer> gain() {
		return this.getChromosome(AgriCraftGenes.GAIN.get());
	}

	public Chromosome<Integer> getGrowth() {
		return this.getChromosome(AgriCraftGenes.GROWTH.get());
	}

	public Chromosome<Integer> getStrength() {
		return this.getChromosome(AgriCraftGenes.STRENGTH.get());
	}

	public Chromosome<Integer> getFertility() {
		return this.getChromosome(AgriCraftGenes.FERTILITY.get());
	}

	public Chromosome<Integer> getResistance() {
		return this.getChromosome(AgriCraftGenes.RESISTANCE.get());
	}

	public Chromosome<Integer> getMutativity() {
		return this.getChromosome(AgriCraftGenes.MUTATIVITY.get());
	}

	public Collection<Chromosome<?>> chromosomes() {
		return this.chromosomes.values();
	}

	@SuppressWarnings("unchecked")
	public Collection<Chromosome<Integer>> getStatChromosomes() {
		return AgriApi.get().getStatRegistry().stream().map(AgriStat::getGene).map(this.chromosomes::get).map(c -> (Chromosome<Integer>) c).toList();
	}

	public void appendHoverText(List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		if (isAdvanced.isAdvanced()) {
			AgriCraftGenes.SPECIES.get().addTooltip(tooltipComponents, this.species().trait());
		}
		this.getStatChromosomes().stream()
				.sorted(Comparator.comparing(chromosome -> chromosome.gene().getId()))
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
