package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.common.lootmodifier.GrassLootModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

public interface AgriGlobalLootModifiers {

	DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<GrassLootModifier>> GRASS_LOOT_MODIFIER = AgriRegistries.GLOBAL_LOOT_MODIFIERS.register("grass", () -> GrassLootModifier.CODEC);


	@ApiStatus.Internal
	static void register() {}

}
