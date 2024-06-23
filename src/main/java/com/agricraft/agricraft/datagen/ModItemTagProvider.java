package com.agricraft.agricraft.datagen;

import com.agricraft.agricraft.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
		super(output, lookupProvider, blockTags);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(Tags.Items.SEEDS).add(ModItems.SEED.get());
		this.tag(tag("nuggets/coal")).add(ModItems.COAL_PEBBLE.get());
		this.tag(tag("nuggets/copper")).add(ModItems.COPPER_NUGGET.get());
		this.tag(tag("nuggets/diamond")).add(ModItems.DIAMOND_SHARD.get());
		this.tag(tag("nuggets/emerald")).add(ModItems.EMERALD_SHARD.get());
		this.tag(tag("nuggets/quartz")).add(ModItems.QUARTZ_SHARD.get());
		this.tag(Tags.Items.NUGGETS).addTags(tag("nuggets/coal"), tag("nuggets/copper"), tag("nuggets/diamond"), tag("nuggets/emerald"), tag("nuggets/quartz"));
		this.tag(ItemTags.CHICKEN_FOOD).add(ModItems.SEED.get());
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
	}

}
