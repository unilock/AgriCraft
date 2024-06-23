package com.agricraft.agricraft.api.genetic;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.stat.AgriStat;
import com.agricraft.agricraft.api.stat.AgriStats;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class AgriGenes {

	public static ResourceKey<Registry<AgriGene<?>>> GENE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("agricraft", "gene"));
	public static DeferredRegister<AgriGene<?>> GENES = DeferredRegister.create(GENE_REGISTRY_KEY, AgriApi.MOD_ID);
	public static Registry<AgriGene<?>> GENE_REGISTRY = GENES.makeRegistry(builder -> builder.sync(true));

	public static DeferredHolder<AgriGene<?>, GeneSpecies> SPECIES = GENES.register("species", GeneSpecies::new);
	public static DeferredHolder<AgriGene<?>, GeneStat> GAIN = GENES.register("gain", () -> new GeneStat(AgriStats.GAIN));
	public static DeferredHolder<AgriGene<?>, GeneStat> GROWTH = GENES.register("growth", () -> new GeneStat(AgriStats.GROWTH));
	public static DeferredHolder<AgriGene<?>, GeneStat> STRENGTH = GENES.register("strength", () -> new GeneStat(AgriStats.STRENGTH));
	public static DeferredHolder<AgriGene<?>, GeneStat> RESISTANCE = GENES.register("resistance", () -> new GeneStat(AgriStats.RESISTANCE));
	public static DeferredHolder<AgriGene<?>, GeneStat> FERTILITY = GENES.register("fertility", () -> new GeneStat(AgriStats.FERTILITY));
	public static DeferredHolder<AgriGene<?>, GeneStat> MUTATIVITY = GENES.register("mutativity", () -> new GeneStat(AgriStats.MUTATIVITY));

	public static Optional<GeneStat> getStatGene(AgriStat stat) {
		// we are assuming that the name of the gene is the same as the name of the stat
		ResourceLocation key = AgriStats.STATS.getRegistry().get().getKey(stat);
		return Optional.ofNullable((GeneStat) GENES.getRegistry().get().get(key));
	}

}
