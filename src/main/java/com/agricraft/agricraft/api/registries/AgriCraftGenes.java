package com.agricraft.agricraft.api.registries;

import com.agricraft.agricraft.api.genetic.AgriGene;
import com.agricraft.agricraft.api.genetic.GeneSpecies;
import com.agricraft.agricraft.api.genetic.GeneStat;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AgriCraftGenes {

	public static DeferredHolder<AgriGene<?>, GeneSpecies> SPECIES;
	public static DeferredHolder<AgriGene<?>, GeneStat> GAIN;
	public static DeferredHolder<AgriGene<?>, GeneStat> GROWTH;
	public static DeferredHolder<AgriGene<?>, GeneStat> STRENGTH;
	public static DeferredHolder<AgriGene<?>, GeneStat> RESISTANCE;
	public static DeferredHolder<AgriGene<?>, GeneStat> FERTILITY;
	public static DeferredHolder<AgriGene<?>, GeneStat> MUTATIVITY;

}
