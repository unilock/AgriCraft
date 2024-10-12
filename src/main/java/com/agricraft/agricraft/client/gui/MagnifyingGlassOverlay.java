package com.agricraft.agricraft.client.gui;

import com.agricraft.agricraft.api.AgriClientApi;
import com.agricraft.agricraft.api.tools.magnifying.MagnifyingInspectable;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Display a tooltip in the world when holding a magnifying glass or having a magnifying helmet equipped.
 * The tooltip shows information about the genome of the plant.
 * <br>
 * You can register a new predicate to allow the tooltip to be rendered with {@link com.agricraft.agricraft.api.AgriClientApi#registerMagnifyingAllowingPredicate}
 * You can add text to the tooltip by implementing the interface {@link MagnifyingInspectable}
 */
public class MagnifyingGlassOverlay {

	private static int hoverTicks;

	public static void renderOverlay(GuiGraphics graphics, DeltaTracker deltaTracker) {
		// greatly inspired from create goggles
		Minecraft mc = Minecraft.getInstance();
		if ((mc.screen != null && !(mc.screen instanceof ChatScreen)) || mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR || mc.player == null || mc.level == null) {
			hoverTicks = 0;
			return;
		}
		boolean shouldOverlay = false;
		for (Predicate<Player> predicate : AgriClientApi.get().getMagnifyingAllowingPredicates()) {
			if (predicate.test(mc.player)) {
				shouldOverlay = true;
				break;
			}
		}
		if (!shouldOverlay) {
			hoverTicks = 0;
			return;
		}
		Optional<MagnifyingInspectable> inspectable = AgriClientApi.get().getMagnifyingInspectors().map(inspectors -> inspectors.inspect(mc.level, mc.player, mc.hitResult))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
		if (inspectable.isEmpty()) {
			hoverTicks = 0;
			return;
		}

		hoverTicks++;
		int posX = graphics.guiWidth() / 2 + 20; //cfg.overlayOffsetX.get();
		int posY = graphics.guiHeight() / 2;// + cfg.overlayOffsetY.get();
		float fade = Mth.clamp(hoverTicks / 48f, 0, 1);  // goes from 0 to 1 in 48 ticks, then stays at 1
		posX += (int) (Math.pow(1 - fade, 3) * 8);
		List<Component> tooltip = new ArrayList<>();
		inspectable.get().addMagnifyingTooltip(tooltip, mc.player.isShiftKeyDown());

		if (!tooltip.isEmpty()) {
			int tooltipHeight = 8;
			if (tooltip.size() > 1) {
				tooltipHeight += 2; // gap between title lines and next lines
				tooltipHeight += (tooltip.size() - 1) * 10;
			}
			RenderSystem.setShaderColor(1, 1, 1, Mth.clamp(hoverTicks / 24f, 0, 0.8f));
			graphics.renderTooltip(mc.font, tooltip, Optional.empty(), posX - TooltipRenderUtil.MOUSE_OFFSET, posY - tooltipHeight / 2 + 12);
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}

	}

}
