package com.agricraft.agricraft.api.stat;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.config.AgriCraftConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AgriStats {

	public static ResourceKey<Registry<AgriStat>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("agricraft", "stat"));
	public static DeferredRegister<AgriStat> STATS = DeferredRegister.create(REGISTRY_KEY, AgriApi.MOD_ID);
	public static Registry<AgriStat> STAT_REGISTRY = STATS.makeRegistry(builder -> {});

	public static DeferredHolder<AgriStat, AgriStat> GAIN = STATS.register("gain", () -> new AgriStat("gain", AgriCraftConfig.GAIN_MIN::get, AgriCraftConfig.GAIN_MAX::get, AgriCraftConfig.GAIN_HIDDEN::get, 0xff0000ff));
	public static DeferredHolder<AgriStat, AgriStat> GROWTH = STATS.register("growth", () -> new AgriStat("growth", AgriCraftConfig.GROWTH_MIN::get, AgriCraftConfig.GROWTH_MAX::get, AgriCraftConfig.GROWTH_HIDDEN::get, 0xff00ff00));
	public static DeferredHolder<AgriStat, AgriStat> STRENGTH = STATS.register("strength", () -> new AgriStat("strength", AgriCraftConfig.STRENGTH_MIN::get, AgriCraftConfig.STRENGTH_MAX::get, AgriCraftConfig.STRENGTH_HIDDEN::get, 0xffff0000));
	public static DeferredHolder<AgriStat, AgriStat> RESISTANCE = STATS.register("resistance", () -> new AgriStat("resistance", AgriCraftConfig.RESISTANCE_MIN::get, AgriCraftConfig.RESISTANCE_MAX::get, AgriCraftConfig.RESISTANCE_HIDDEN::get, 0xffffff00));
	public static DeferredHolder<AgriStat, AgriStat> FERTILITY = STATS.register("fertility", () -> new AgriStat("fertility", AgriCraftConfig.FERTILITY_MIN::get, AgriCraftConfig.FERTILITY_MAX::get, AgriCraftConfig.FERTILITY_HIDDEN::get, 0xffff7f00));
	public static DeferredHolder<AgriStat, AgriStat> MUTATIVITY = STATS.register("mutativity", () -> new AgriStat("mutativity", AgriCraftConfig.MUTATIVITY_MIN::get, AgriCraftConfig.MUTATIVITY_MAX::get, AgriCraftConfig.MUTATIVITY_HIDDEN::get, 0xff00ffff));

}
