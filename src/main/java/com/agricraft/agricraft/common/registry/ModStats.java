package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.registries.AgriCraftStats;
import com.agricraft.agricraft.api.stat.AgriStat;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.STATS;

public interface ModStats {

	@ApiStatus.Internal
	static void register() {
		AgriCraftStats.GAIN = STATS.register("gain", () -> new AgriStat(AgriCraftConfig.GAIN_MIN::get, AgriCraftConfig.GAIN_MAX::get, AgriCraftConfig.GAIN_HIDDEN::get, 0xff0000ff));
		AgriCraftStats.GROWTH = STATS.register("growth", () -> new AgriStat(AgriCraftConfig.GROWTH_MIN::get, AgriCraftConfig.GROWTH_MAX::get, AgriCraftConfig.GROWTH_HIDDEN::get, 0xff00ff00));
		AgriCraftStats.STRENGTH = STATS.register("strength", () -> new AgriStat(AgriCraftConfig.STRENGTH_MIN::get, AgriCraftConfig.STRENGTH_MAX::get, AgriCraftConfig.STRENGTH_HIDDEN::get, 0xffff0000));
		AgriCraftStats.RESISTANCE = STATS.register("resistance", () -> new AgriStat(AgriCraftConfig.RESISTANCE_MIN::get, AgriCraftConfig.RESISTANCE_MAX::get, AgriCraftConfig.RESISTANCE_HIDDEN::get, 0xffffff00));
		AgriCraftStats.FERTILITY = STATS.register("fertility", () -> new AgriStat(AgriCraftConfig.FERTILITY_MIN::get, AgriCraftConfig.FERTILITY_MAX::get, AgriCraftConfig.FERTILITY_HIDDEN::get, 0xffff7f00));
		AgriCraftStats.MUTATIVITY = STATS.register("mutativity", () -> new AgriStat(AgriCraftConfig.MUTATIVITY_MIN::get, AgriCraftConfig.MUTATIVITY_MAX::get, AgriCraftConfig.MUTATIVITY_HIDDEN::get, 0xff00ffff));
	}

}
