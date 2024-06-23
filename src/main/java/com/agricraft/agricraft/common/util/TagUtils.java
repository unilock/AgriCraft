package com.agricraft.agricraft.common.util;

import com.agricraft.agricraft.api.AgriApi;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.stream.Stream;

public class TagUtils {

	public static List<Item> getItemsFromLocation(ExtraCodecs.TagOrElementLocation tag) {
		if (!tag.tag()) {
			return List.of(BuiltInRegistries.ITEM.get(tag.id()));
		} else {
			return BuiltInRegistries.ITEM.getTag(TagKey.create(Registries.ITEM, tag.id()))
					.map(HolderSet.ListBacked::stream)
					.map(str -> str.map(Holder::value))
					.map(Stream::toList)
					.orElse(List.of());
		}
	}

	public static List<Block> getBlocksFromLocation(ExtraCodecs.TagOrElementLocation tag) {
		if (!tag.tag()) {
			return List.of(BuiltInRegistries.BLOCK.get(tag.id()));
		} else {
			return BuiltInRegistries.BLOCK.getTag(TagKey.create(Registries.BLOCK, tag.id()))
					.map(HolderSet.ListBacked::stream)
					.map(str -> str.map(Holder::value))
					.map(Stream::toList)
					.orElse(List.of());
		}
	}

	public static List<Fluid> getFluidsFromLocation(ExtraCodecs.TagOrElementLocation tag) {
		if (!tag.tag()) {
			return List.of(BuiltInRegistries.FLUID.get(tag.id()));
		} else {
			return BuiltInRegistries.FLUID.getTag(TagKey.create(Registries.FLUID, tag.id()))
					.map(HolderSet.ListBacked::stream)
					.map(str -> str.map(Holder::value))
					.map(Stream::toList)
					.orElse(List.of());
		}
	}

	public static Stream<ResourceLocation> getPlantIdsFromTag(ExtraCodecs.TagOrElementLocation tag) {
		if (!tag.tag()) {
			return Stream.of(tag.id());
		} else {
			return AgriApi.getPlantRegistry().flatMap(registry ->
							registry.getTag(TagKey.create(AgriApi.AGRIPLANTS, tag.id()))
									.map(named -> named.stream().map(holder -> registry.getKey(holder.value())))
					)
					.orElse(Stream.empty());
		}
	}

}
