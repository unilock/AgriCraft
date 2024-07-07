package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.common.item.JournalItem;
import com.agricraft.agricraft.common.item.SeedBagItem;
import com.agricraft.agricraft.common.item.TrowelItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.DATA_COMPONENTS;

public interface AgriDataComponents {

	DeferredHolder<DataComponentType<?>, DataComponentType<AgriGenome>> GENOME = DATA_COMPONENTS.registerComponentType("genome", builder -> builder.persistent(AgriGenome.CODEC).networkSynchronized(AgriGenome.STREAM_CODEC));
	DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> MAGNIFYING = DATA_COMPONENTS.registerComponentType("magnifying", builder -> builder.persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
	DeferredHolder<DataComponentType<?>, DataComponentType<TrowelItem.TrowelData>> TROWEL_DATA = DATA_COMPONENTS.registerComponentType("trowel_data", builder -> builder.persistent(TrowelItem.TrowelData.CODEC).networkSynchronized(TrowelItem.TrowelData.STREAM_CODEC));
	DeferredHolder<DataComponentType<?>, DataComponentType<JournalItem.Data>> JOURNAL_DATA = DATA_COMPONENTS.registerComponentType("journal_data", builder -> builder.persistent(JournalItem.Data.CODEC).networkSynchronized(JournalItem.Data.STREAM_CODEC));
	DeferredHolder<DataComponentType<?>, DataComponentType<SeedBagItem.Data>> SEED_BAG_DATA = DATA_COMPONENTS.registerComponentType("seed_bag_data", builder -> builder.persistent(SeedBagItem.Data.CODEC).networkSynchronized(SeedBagItem.Data.STREAM_CODEC));

	@ApiStatus.Internal
	static void register() {}

}
