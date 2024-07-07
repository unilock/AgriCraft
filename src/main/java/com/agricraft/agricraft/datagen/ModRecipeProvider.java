package com.agricraft.agricraft.datagen;

import com.agricraft.agricraft.common.registry.AgriItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;


public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.WOODEN_CROP_STICKS.get(), 8))
				.pattern("##")
				.pattern("##")
				.define('#', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.IRON_CROP_STICKS.get(), 8))
				.pattern("#")
				.pattern("#")
				.define('#', Tags.Items.NUGGETS_IRON)
				.unlockedBy("has_nugget", has(Tags.Items.NUGGETS_IRON))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.OBSIDIAN_CROP_STICKS.get(), 8))
				.pattern("#")
				.pattern("#")
				.define('#', Tags.Items.OBSIDIANS)
				.unlockedBy("has_obsidian", has(Tags.Items.OBSIDIANS))
				.save(output);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, new ItemStack(AgriItems.JOURNAL.get()))
				.requires(Items.WRITABLE_BOOK)
				.requires(Tags.Items.SEEDS)
				.unlockedBy("has_seed", has(Tags.Items.SEEDS))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.SEED_ANALYZER.get()))
				.pattern("sgs")
				.pattern(" bs")
				.pattern("pwp")
				.define('s', Tags.Items.RODS_WOODEN)
				.define('g', Tags.Items.GLASS_BLOCKS_COLORLESS)
				.define('b', Items.STONE_SLAB)
				.define('p', ItemTags.PLANKS)
				.define('w', ItemTags.WOODEN_SLABS)
				.unlockedBy("has_seed", has(Tags.Items.SEEDS))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.CLIPPER.get()))
				.pattern(" i ")
				.pattern("sr ")
				.pattern(" s ")
				.define('i', Tags.Items.INGOTS_IRON)
				.define('r', Items.SHEARS)
				.define('s', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.MAGNIFYING_GLASS.get()))
				.pattern("sgs")
				.pattern(" s ")
				.pattern(" s ")
				.define('g', TagKey.create(Registries.ITEM, ResourceLocation.parse("c:glass_panes/colorless")))
				.define('s', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.WOODEN_RAKE.get()))
				.pattern("f")
				.pattern("s")
				.define('f', TagKey.create(Registries.ITEM, ResourceLocation.parse("c:fences/wooden")))
				.define('s', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.IRON_RAKE.get()))
				.pattern("b")
				.pattern("s")
				.define('b', Items.IRON_BARS)
				.define('s', Tags.Items.RODS_WOODEN)
				.unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.TROWEL.get()))
				.pattern("  s")
				.pattern("ii ")
				.define('s', Tags.Items.RODS_WOODEN)
				.define('i', Tags.Items.INGOTS_IRON)
				.unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
				.save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, new ItemStack(AgriItems.SEED_BAG.get()))
				.pattern(" s ")
				.pattern("l l")
				.pattern(" l ")
				.define('s', Tags.Items.STRINGS)
				.define('l', Tags.Items.LEATHERS)
				.unlockedBy("has_seed", has(Tags.Items.SEEDS))
				.save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.AMETHYST_SHARD))
				.pattern("ppp")
				.pattern("ppp")
				.pattern("ppp")
				.define('p', AgriItems.AMATHYLLIS_PETAL.get())
				.unlockedBy("has_petal", has(AgriItems.AMATHYLLIS_PETAL.get()))
				.save(output, ResourceLocation.parse("agricraft:amethyst_shard"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.COAL))
				.pattern("ppp")
				.pattern("ppp")
				.pattern("ppp")
				.define('p', AgriItems.COAL_PEBBLE.get())
				.unlockedBy("has_pebble", has(AgriItems.COAL_PEBBLE.get()))
				.save(output, ResourceLocation.parse("agricraft:coal"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.COPPER_INGOT))
				.pattern("nnn")
				.pattern("nnn")
				.pattern("nnn")
				.define('n', AgriItems.COPPER_NUGGET.get())
				.unlockedBy("has_nugget", has(AgriItems.COPPER_NUGGET.get()))
				.save(output, ResourceLocation.parse("agricraft:copper_ingot"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.DIAMOND))
				.pattern("sss")
				.pattern("sss")
				.pattern("sss")
				.unlockedBy("has_shard", has(AgriItems.DIAMOND_SHARD.get()))
				.define('s', AgriItems.DIAMOND_SHARD.get())
				.save(output, ResourceLocation.parse("agricraft:diamond"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.EMERALD))
				.pattern("sss")
				.pattern("sss")
				.pattern("sss")
				.unlockedBy("has_shard", has(AgriItems.EMERALD_SHARD.get()))
				.define('s', AgriItems.EMERALD_SHARD.get())
				.save(output, ResourceLocation.parse("agricraft:emerald"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.NETHERITE_SCRAP))
				.pattern("sss")
				.pattern("sss")
				.pattern("sss")
				.unlockedBy("has_sliver", has(AgriItems.NETHERITE_SLIVER.get()))
				.define('s', AgriItems.NETHERITE_SLIVER.get())
				.save(output, ResourceLocation.parse("agricraft:netherite_scrap"));
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(Items.QUARTZ))
				.pattern("sss")
				.pattern("sss")
				.pattern("sss")
				.unlockedBy("has_shard", has(AgriItems.QUARTZ_SHARD.get()))
				.define('s', AgriItems.QUARTZ_SHARD.get())
				.save(output, ResourceLocation.parse("agricraft:quartz"));
	}

}
