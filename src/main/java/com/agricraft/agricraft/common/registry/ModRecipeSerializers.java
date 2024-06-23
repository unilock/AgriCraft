package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.common.item.crafting.MagnifyingHelmetRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AgriApi.MOD_ID);

	public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<MagnifyingHelmetRecipe>> MAGNIFYING_HELMET = RECIPE_SERIALIZERS.register("magnifyinghelmet", () -> new SimpleCraftingRecipeSerializer<>(MagnifyingHelmetRecipe::new));

}
