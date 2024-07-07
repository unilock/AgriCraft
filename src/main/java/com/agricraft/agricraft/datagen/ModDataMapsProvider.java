package com.agricraft.agricraft.datagen;

import com.agricraft.agricraft.common.registry.AgriItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapsProvider extends DataMapProvider {


	protected ModDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather() {
		builder(NeoForgeDataMaps.COMPOSTABLES).add(AgriItems.SEED, new Compostable(0.3f), false);
	}

}
