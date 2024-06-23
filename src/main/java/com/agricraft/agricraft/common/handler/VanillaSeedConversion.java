package com.agricraft.agricraft.common.handler;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.codecs.AgriSoil;
import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.common.block.CropBlock;
import com.agricraft.agricraft.common.block.CropState;
import com.agricraft.agricraft.common.block.entity.SeedAnalyzerBlockEntity;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import com.agricraft.agricraft.common.registry.ModBlocks;
import com.agricraft.agricraft.common.registry.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class VanillaSeedConversion {

	/**
	 * @param player
	 * @param hand
	 * @param pos
	 * @param blockHitResult
	 * @return {@code true} if the right click must be canceled, false otherwise
	 */
	public static boolean onRightClick(Player player, InteractionHand hand, BlockPos pos, BlockHitResult blockHitResult) {
		// TODO: @Ketheroth API: should we add item exceptions?
		ItemStack heldItem = player.getItemInHand(hand);
		if (!AgriCraftConfig.OVERRIDE_VANILLA_FARMING.get()
				|| heldItem.isEmpty()
				|| heldItem.getItem() instanceof AgriSeedItem
				|| AgriApi.getCrop(player.level(), pos).isPresent()) {
			return false;
		}

		if (player.isShiftKeyDown()) {
			if (player.level().getBlockEntity(pos) instanceof SeedAnalyzerBlockEntity seedAnalyzer && !seedAnalyzer.hasSeed()) {
				// if the analyzer is empty, convert the item to an agricraft seed and let it handle the rest
				Optional<AgriGenome> genome = AgriApi.getGenomeAdapter(heldItem).flatMap(adapter -> adapter.valueOf(heldItem));
				if (genome.isPresent() && seedAnalyzer.insertSeed(AgriSeedItem.toStack(genome.get()), null)) {
					heldItem.shrink(1);
					return true;
				}
			}
		}

		if (!AgriCraftConfig.CONVERTS_SEED_ONLY_IN_ANALYZER.get()) {
			BlockPos cropPos = pos.relative(blockHitResult.getDirection());
			Optional<AgriSoil> optionalSoil = AgriApi.getSoil(player.level(), cropPos.below());
			if (optionalSoil.isPresent()) {
				Optional<AgriCrop> optionalCrop = AgriApi.getCrop(player.level(), cropPos);
				if (optionalCrop.isPresent()) {
					if (!optionalCrop.get().hasPlant() && !optionalCrop.get().isCrossCropSticks()) {
						Optional<AgriGenome> genome = AgriApi.getGenomeAdapter(heldItem).flatMap(adapter -> adapter.valueOf(heldItem));
						if (genome.isPresent()) {
							plantSeedOnCrop(player, hand, optionalCrop.get(), AgriSeedItem.toStack(genome.get()));
							return true;
						}
					} else {
						return false;
					}
				} else if (player.level().getBlockState(cropPos).isAir() && AgriCraftConfig.PLANT_OFF_CROP_STICKS.get()) {
					Optional<AgriGenome> genome = AgriApi.getGenomeAdapter(heldItem).flatMap(adapter -> adapter.valueOf(heldItem));
					if (genome.isPresent()) {
						player.level().setBlock(cropPos, ModBlocks.CROP.get().defaultBlockState().setValue(CropBlock.CROP_STATE, CropState.PLANT), 3);
						plantSeedOnCrop(player, hand, AgriApi.getCrop(player.level(), cropPos).get(), AgriSeedItem.toStack(genome.get()));
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void plantSeedOnCrop(Player player, InteractionHand hand, AgriCrop crop, ItemStack seed) {
		AgriGenome genome = seed.get(ModDataComponentTypes.GENOME);
		if (genome != null) {
			crop.plantGenome(genome);
			if (player != null && !player.isCreative()) {
				player.getItemInHand(hand).shrink(1);
			}
		}
	}

}
