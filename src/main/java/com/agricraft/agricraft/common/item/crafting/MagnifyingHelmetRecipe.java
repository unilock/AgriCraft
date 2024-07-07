package com.agricraft.agricraft.common.item.crafting;

import com.agricraft.agricraft.common.registry.AgriDataComponents;
import com.agricraft.agricraft.common.registry.AgriItems;
import com.agricraft.agricraft.common.registry.AgriRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Custom recipe to add the magnifying property to a helmet, crafted from the helmet and a magnifying glass.
 * An item is considered as a helmet if it is an instance of {@link  ArmorItem} and his equipment slot is {@link  EquipmentSlot#HEAD}
 */
public class MagnifyingHelmetRecipe extends CustomRecipe {

	public MagnifyingHelmetRecipe(CraftingBookCategory craftingBookCategory) {
		super(craftingBookCategory);
	}

	@Override
	public boolean matches(CraftingInput container, Level level) {
		boolean helmet = false;
		boolean glass = false;
		for (int i = 0; i < container.size(); i++) {
			ItemStack itemStack = container.getItem(i);
			if (itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.HEAD && !itemStack.has(AgriDataComponents.MAGNIFYING)) {
				if (helmet) {
					return false;
				} else {
					helmet = true;
				}
			} else if (itemStack.is(AgriItems.MAGNIFYING_GLASS.get())) {
				if (glass) {
					return false;
				} else {
					glass = true;
				}
			}
		}
		return helmet && glass;
	}

	@Override
	public ItemStack assemble(CraftingInput container, HolderLookup.Provider provider) {
		ItemStack helmet = null;
		ItemStack glass = null;
		for (int i = 0; i < container.size(); i++) {
			ItemStack itemStack = container.getItem(i);
			if (itemStack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.HEAD) {
				helmet = itemStack;
			} else if (itemStack.is(AgriItems.MAGNIFYING_GLASS.get())) {
				glass = itemStack;
			}
		}
		if (helmet != null && glass != null) {
			ItemStack copy = helmet.copy();
			copy.set(AgriDataComponents.MAGNIFYING, Unit.INSTANCE);
			return copy;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return AgriRecipeSerializers.MAGNIFYING_HELMET.get();
	}

}
