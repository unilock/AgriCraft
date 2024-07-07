package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.common.item.crafting.MagnifyingHelmetRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.RECIPE_SERIALIZERS;

public interface AgriRecipeSerializers {

	DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<MagnifyingHelmetRecipe>> MAGNIFYING_HELMET = RECIPE_SERIALIZERS.register("magnifying_helmet", () -> new SimpleCraftingRecipeSerializer<>(MagnifyingHelmetRecipe::new));

	@ApiStatus.Internal
	static void register() {}

}
