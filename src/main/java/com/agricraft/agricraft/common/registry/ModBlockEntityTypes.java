package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.agricraft.agricraft.common.block.entity.SeedAnalyzerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntityTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AgriApi.MOD_ID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CropBlockEntity>> CROP = BLOCK_ENTITY_TYPES.register("crop", () -> BlockEntityType.Builder.of(CropBlockEntity::new, ModBlocks.CROP.get()).build(null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SeedAnalyzerBlockEntity>> SEED_ANALYZER = BLOCK_ENTITY_TYPES.register("seed_analyzer", () -> BlockEntityType.Builder.of(SeedAnalyzerBlockEntity::new, ModBlocks.SEED_ANALYZER.get()).build(null));

}
