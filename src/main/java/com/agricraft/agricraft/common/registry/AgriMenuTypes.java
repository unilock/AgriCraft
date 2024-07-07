package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.common.inventory.container.SeedAnalyzerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.MENU_TYPES;

public interface AgriMenuTypes {

	DeferredHolder<MenuType<?>, MenuType<SeedAnalyzerMenu>> SEED_ANALYZER_MENU = MENU_TYPES.register("seed_analyzer",
			() -> IMenuTypeExtension.create((id, inv, data) -> new SeedAnalyzerMenu(id, inv, inv.player, data.readBlockPos())));

	@ApiStatus.Internal
	static void register() {}

}
