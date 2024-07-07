package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.common.block.CropBlock;
import com.agricraft.agricraft.common.block.SeedAnalyzerBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.ApiStatus;

import static com.agricraft.agricraft.common.registry.AgriRegistries.BLOCKS;

public interface AgriBlocks {

	DeferredBlock<CropBlock> CROP = BLOCKS.register("crop", CropBlock::new);
	DeferredBlock<SeedAnalyzerBlock> SEED_ANALYZER = BLOCKS.register("seed_analyzer", SeedAnalyzerBlock::new);

	@ApiStatus.Internal
	static void register() {}

}
