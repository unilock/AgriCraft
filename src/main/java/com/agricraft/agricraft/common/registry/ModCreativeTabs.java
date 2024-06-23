package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.AgriCraft;
import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class ModCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AgriApi.MOD_ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB.register("main", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(ModItems.DEBUGGER.get()))
			.title(Component.translatable("itemGroup.agricraft.main"))
			.displayItems(ModItems::addItemsToTabs)
//				.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SEED_TAB = CREATIVE_MODE_TAB.register("seeds", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.agricraft.seeds"))
			.icon(() -> new ItemStack(Items.WHEAT_SEEDS))
			.displayItems((itemDisplayParameters, output) -> AgriApi.getPlantRegistry()
					.ifPresent(registry -> {
						AgriCraft.LOGGER.info("add seeds in tab: " + registry.stream().count());
						for (Map.Entry<ResourceKey<AgriPlant>, AgriPlant> entry : registry.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
							output.accept(AgriSeedItem.toStack(entry.getValue()));
						}
					}))
			.withTabsBefore(ModCreativeTabs.MAIN_TAB.getId())
			.build());

}
