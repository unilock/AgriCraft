package com.agricraft.agricraft.api;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Optional;

public class SideUtils {

	public static Optional<RegistryAccess> getRegistryAccess() {
		if (ServerLifecycleHooks.getCurrentServer() == null) {
			if (FMLEnvironment.dist == Dist.CLIENT && Minecraft.getInstance().level != null) {
				return Optional.of(Minecraft.getInstance().level.registryAccess());
			}
		} else {
			return Optional.of(ServerLifecycleHooks.getCurrentServer().registryAccess());
		}
		return Optional.empty();
	}
}
