package com.agricraft.agricraft.compat.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class AgriCraftJeiClient {

	public static IDrawable createDrawable(ResourceLocation location, int uOffset, int vOffset, int width, int height, int textureWidth, int textureHeight) {
		return new IDrawable() {
			@Override
			public int getWidth() {
				return width;
			}

			@Override
			public int getHeight() {
				return height;
			}

			@Override
			public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
				guiGraphics.blit(location, xOffset, yOffset, uOffset, vOffset, getWidth(), getHeight(), textureWidth, textureHeight);
			}
		};
	}

}
