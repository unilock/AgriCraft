package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.common.inventory.container.SeedAnalyzerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AgriApi.MOD_ID);

	public static final DeferredHolder<MenuType<?>, MenuType<SeedAnalyzerMenu>> SEED_ANALYZER_MENU = MENUS.register("seed_analyzer",
			() -> IMenuTypeExtension.create((id, inv, data) -> new SeedAnalyzerMenu(id, inv, inv.player, data.readBlockPos())));

}
