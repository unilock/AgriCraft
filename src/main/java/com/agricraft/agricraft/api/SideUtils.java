package com.agricraft.agricraft.api;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class SideUtils {

	public static RegistryAccess getRegistryAccess() {
		if (FMLEnvironment.dist == Dist.CLIENT) {
			return Minecraft.getInstance().level.registryAccess();
		} else {
			return ServerLifecycleHooks.getCurrentServer().registryAccess();
		}
	}
}
