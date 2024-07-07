package com.agricraft.agricraft.api.plant;

import com.agricraft.agricraft.api.AgriApi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface AgriPlantModifierFactory {

	ResourceKey<Registry<AgriPlantModifierFactory>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AgriApi.MOD_ID, "plant_modifier"));

	Optional<IAgriPlantModifier> construct(AgriPlantModifierInfo info);

}
