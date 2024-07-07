package com.agricraft.agricraft.compat.sereneseason;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.requirement.AgriSeason;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StainedGlassBlock;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;

public class SereneSeasonPlugin {

	public static String ID = "sereneseasons";

	public static void init() {
		AgriApi.get().getSeasonLogic().claim(SereneSeasonPlugin.class, (level, pos) -> {
			// Serene Seasons cave stuff
			if (ModConfig.fertility.undergroundFertilityLevel > -1
					&& pos.getY() < ModConfig.fertility.undergroundFertilityLevel) {
				return AgriSeason.ANY;
			}
			// Serene Seasons greenhouse stuff
			BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
			for (int i = 0; i < 16; ++i) {
				mutablePos.set(pos.getX(), pos.getY() + i, pos.getZ());
				if (level.getBlockState(mutablePos).getBlock() instanceof StainedGlassBlock) {
					return AgriSeason.ANY;
				}
			}
			// Fall back to default logic
			return switch (SeasonHelper.getSeasonState(level).getSeason()) {
				case SPRING -> AgriSeason.SPRING;
				case SUMMER -> AgriSeason.SUMMER;
				case AUTUMN -> AgriSeason.AUTUMN;
				case WINTER -> AgriSeason.WINTER;
			};
		});
	}

	public String description() {
		return "serene seasons compatibility";
	}

}
