package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.common.block.CropBlock;
import com.agricraft.agricraft.common.block.SeedAnalyzerBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AgriApi.MOD_ID);

	public static final DeferredBlock<CropBlock> CROP = BLOCKS.register("crop", CropBlock::new);
	public static final DeferredBlock<SeedAnalyzerBlock> SEED_ANALYZER = BLOCKS.register("seed_analyzer", SeedAnalyzerBlock::new);

}
