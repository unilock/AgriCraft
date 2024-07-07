package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.AgriCraft;
import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

import static com.agricraft.agricraft.common.registry.AgriRegistries.CREATIVE_MODE_TAB;

public interface AgriTabs {

	DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB.register("main", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(AgriItems.DEBUGGER.get()))
			.title(Component.translatable("itemGroup.agricraft.main"))
			.displayItems(AgriItems::addItemsToTabs)
//				.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.build());
	DeferredHolder<CreativeModeTab, CreativeModeTab> SEED_TAB = CREATIVE_MODE_TAB.register("seeds", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.agricraft.seeds"))
			.icon(() -> new ItemStack(Items.WHEAT_SEEDS))
			.displayItems((itemDisplayParameters, output) -> AgriApi.get().getPlantRegistry()
					.ifPresent(registry -> {
						AgriCraft.LOGGER.info("add seeds in tab: " + registry.size());
						for (Map.Entry<ResourceKey<AgriPlant>, AgriPlant> entry : registry.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
							output.accept(AgriSeedItem.toStack(entry.getValue()));
						}
					}))
			.withTabsBefore(AgriTabs.MAIN_TAB.getId())
			.build());

	@ApiStatus.Internal
	static void register() {}

}
