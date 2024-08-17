package com.agricraft.agricraft.api;

import com.agricraft.agricraft.api.codecs.AgriSoilValue;
import com.agricraft.agricraft.api.requirement.AgriSeason;
import com.agricraft.agricraft.api.stat.AgriStat;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class LangUtils {

	public static Component plantName(String plantId) {
		return again("plant.agricraft." + plantId.replace(":", ".").replace("/", "."));
	}

	public static Component plantDescription(String plantId) {
		String id = "description.agricraft." + plantId.replace(":", ".").replace("/", ".");
		MutableComponent component = Component.translatable(id);
		if (component.getString().equals(id)) {
			return null;
		}
		return component;
	}

	public static Component weedName(String weedId) {
		return again("weed.agricraft." + weedId.replace(":", ".").replace("/", "."));
	}

	public static Component seedName(String plantId) {
		return again("seed.agricraft." + plantId.replace(":", ".").replace("/", "."));
	}

	public static Component soilName(String soilId) {
		return again("soil.agricraft." + soilId.replace(":", ".").replace("/", "."));
	}

	public static Component soilPropertyName(String property, AgriSoilValue value) {
		return Component.translatable("agricraft.soil." + property + "." + value.name().toLowerCase());
	}

	public static MutableComponent statName(AgriStat stat) {
		return Component.translatable("agricraft.stat." + stat.getId().toString().replace(":", ".").replace("/", "."));
	}

	public static Component seasonName(AgriSeason season) {
		return Component.translatable("agricraft.season." + season.name().toLowerCase());
	}

	/**
	 * Create a translatable component of the given key.
	 * If the value of the key is also a translation key it will return the value of that second translation key
	 *
	 * @param key the translation key
	 * @return the translatable component
	 */
	public static Component again(String key) {
		String str = Language.getInstance().getOrDefault(key);
		if (Language.getInstance().has(str)) {
			return Component.translatable(str);
		}
		return Component.translatable(key);
	}

}
