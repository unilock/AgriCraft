package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.registries.AgriCraftStats;
import com.agricraft.agricraft.api.stat.AgriStat;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.STATS;

public interface ModStats {

	@ApiStatus.Internal
	static void register() {
		AgriCraftStats.GAIN = STATS.register("gain", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.GAIN_MIN), () -> getOrDefault(AgriCraftConfig.GAIN_MAX), () -> getOrDefault(AgriCraftConfig.GAIN_HIDDEN), 0xff0000ff));
		AgriCraftStats.GROWTH = STATS.register("growth", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.GROWTH_MIN), () -> getOrDefault(AgriCraftConfig.GROWTH_MAX), () -> getOrDefault(AgriCraftConfig.GROWTH_HIDDEN), 0xff00ff00));
		AgriCraftStats.STRENGTH = STATS.register("strength", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.STRENGTH_MIN), () -> getOrDefault(AgriCraftConfig.STRENGTH_MAX), () -> getOrDefault(AgriCraftConfig.STRENGTH_HIDDEN), 0xffff0000));
		AgriCraftStats.RESISTANCE = STATS.register("resistance", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.RESISTANCE_MIN), () -> getOrDefault(AgriCraftConfig.RESISTANCE_MAX), () -> getOrDefault(AgriCraftConfig.RESISTANCE_HIDDEN), 0xffffff00));
		AgriCraftStats.FERTILITY = STATS.register("fertility", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.FERTILITY_MIN), () -> getOrDefault(AgriCraftConfig.FERTILITY_MAX), () -> getOrDefault(AgriCraftConfig.FERTILITY_HIDDEN), 0xffff7f00));
		AgriCraftStats.MUTATIVITY = STATS.register("mutativity", () -> new AgriStat(() -> getOrDefault(AgriCraftConfig.MUTATIVITY_MIN), () -> getOrDefault(AgriCraftConfig.MUTATIVITY_MAX), () -> getOrDefault(AgriCraftConfig.MUTATIVITY_HIDDEN), 0xff00ffff));
	}

	static int getOrDefault(ModConfigSpec.IntValue getter) {
		if (AgriCraftConfig.SPEC.isLoaded()) {
			return getter.get();
		}
		return getter.getDefault();
	}

	static boolean getOrDefault(ModConfigSpec.BooleanValue getter) {
		if (AgriCraftConfig.SPEC.isLoaded()) {
			return getter.get();
		}
		return getter.getDefault();
	}

}
