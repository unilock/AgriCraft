package com.agricraft.agricraft.api.tools.seedbag;

import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;

/**
 * Sorts its entries by having the best in first, and the worst in last
 */
public interface BagSorter extends Comparator<BagEntry> {

	ResourceLocation getId();

}
