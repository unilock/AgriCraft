package com.agricraft.agricraft.compat.jei;


import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.codecs.AgriMutation;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.common.item.crafting.MagnifyingHelmetRecipe;
import com.agricraft.agricraft.common.registry.AgriDataComponents;
import com.agricraft.agricraft.common.registry.AgriItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@JeiPlugin
public class AgriCraftJeiPlugin implements IModPlugin {

	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AgriApi.MOD_ID, "compat_jei");

	@Override
	@NotNull
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		// Register all The Seeds.
		registration.registerSubtypeInterpreter(AgriItems.SEED.get(), (stack, context) -> {
			AgriGenome genome = stack.get(AgriDataComponents.GENOME.get());
			if (genome != null) {
				return genome.species().trait();
			}
			return "agricraft:unknown";
		});
	}

	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
		registration.getCraftingCategory().addExtension(MagnifyingHelmetRecipe.class, new MagnifyingHelmetExtension());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new CropProduceCategory());
		registration.addRecipeCategories(new CropClippingCategory());
		registration.addRecipeCategories(new CropMutationCategory());
		registration.addRecipeCategories(new CropRequirementCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		AgriApi.get().getPlantRegistry().ifPresent(registry -> registration.addRecipes(CropProduceCategory.TYPE, registry.stream().toList()));
		AgriApi.get().getPlantRegistry().ifPresent(registry -> registration.addRecipes(CropClippingCategory.TYPE, registry.stream().filter(plant -> {
			ArrayList<ItemStack> cliProducts = new ArrayList<>();
			plant.getAllPossibleClipProducts(cliProducts::add);
			return !cliProducts.isEmpty();
		}).toList()));
		AgriApi.get().getPlantRegistry().ifPresent(registry -> registration.addRecipes(CropRequirementCategory.TYPE, registry.stream().map(CropRequirementCategory.Recipe::new).toList()));
		AgriApi.get().getMutationRegistry().ifPresent(registry -> registration.addRecipes(CropMutationCategory.TYPE, registry.stream().filter(AgriMutation::isValid).toList()));
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registration) {
		AgriApi.get().getPlantRegistry().ifPresent(registry -> registration.register(PlantIngredient.TYPE, registry.stream().toList(), PlantIngredient.HELPER, PlantIngredient.RENDERER));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(AgriItems.CLIPPER.get().getDefaultInstance(), CropClippingCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.WOODEN_CROP_STICKS.get().getDefaultInstance(), CropProduceCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.WOODEN_CROP_STICKS.get().getDefaultInstance(), CropClippingCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.WOODEN_CROP_STICKS.get().getDefaultInstance(), CropMutationCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.WOODEN_CROP_STICKS.get().getDefaultInstance(), CropRequirementCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.IRON_CROP_STICKS.get().getDefaultInstance(), CropProduceCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.IRON_CROP_STICKS.get().getDefaultInstance(), CropClippingCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.IRON_CROP_STICKS.get().getDefaultInstance(), CropMutationCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.IRON_CROP_STICKS.get().getDefaultInstance(), CropRequirementCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.OBSIDIAN_CROP_STICKS.get().getDefaultInstance(), CropProduceCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.OBSIDIAN_CROP_STICKS.get().getDefaultInstance(), CropClippingCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.OBSIDIAN_CROP_STICKS.get().getDefaultInstance(), CropMutationCategory.TYPE);
		registration.addRecipeCatalyst(AgriItems.OBSIDIAN_CROP_STICKS.get().getDefaultInstance(), CropRequirementCategory.TYPE);
	}


}