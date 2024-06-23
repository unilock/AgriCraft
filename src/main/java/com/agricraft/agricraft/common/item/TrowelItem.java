package com.agricraft.agricraft.common.item;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.crop.AgriGrowthStage;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.api.genetic.AgriGenomeProviderItem;
import com.agricraft.agricraft.common.block.CropBlock;
import com.agricraft.agricraft.common.registry.ModBlocks;
import com.agricraft.agricraft.common.registry.ModDataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TrowelItem extends Item implements AgriGenomeProviderItem {

	public TrowelItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return AgriApi.getCrop(context.getLevel(), context.getClickedPos())
				.map(crop -> tryUseOnCrop(crop, context.getItemInHand(), context.getPlayer()))
				.orElseGet(() -> tryPlantOnSoil(context.getLevel(), context.getClickedPos(), context.getItemInHand(), context.getPlayer()));
	}

	protected InteractionResult tryUseOnCrop(AgriCrop crop, ItemStack heldItem, Player player) {
		if (crop.hasWeeds()) {
			if (player != null && player.level().isClientSide()) {
				player.sendSystemMessage(Component.translatable("agricraft.message.trowel_weed"));
			}
			return InteractionResult.FAIL;
		} else if (crop.isCrossCropSticks()) {
			return InteractionResult.FAIL;
		}
		if (crop.hasPlant()) {
			// Try picking up a plant onto the trowel
			return tryPickUpPlant(crop, heldItem, player);
		}
		// Try planting a plant on crop sticks
		return tryPlantOnCropSticks(crop, heldItem, player);
	}

	protected InteractionResult tryPickUpPlant(AgriCrop crop, ItemStack stack, Player player) {
		if (crop.getLevel() == null || crop.getLevel().isClientSide()) {
			return InteractionResult.PASS;
		}
		if (this.hasPlant(stack)) {
			if (player != null) {
				player.sendSystemMessage(Component.translatable("agricraft.message.trowel_plant"));
			}
			return InteractionResult.FAIL;
		} else {
			AgriGenome genome = crop.getGenome();
			if (genome != null) {
				this.setPlant(stack, genome, crop.getGrowthStage());
				crop.removeGenome();
			}
			return InteractionResult.SUCCESS;
		}
	}

	protected InteractionResult tryPlantOnCropSticks(AgriCrop crop, ItemStack stack, Player player) {
		if (crop.getLevel() == null || crop.getLevel().isClientSide()) {
			return InteractionResult.PASS;
		}
		if (crop.isCrossCropSticks()) {
			return InteractionResult.FAIL;
		}
		if (this.hasPlant(stack)) {
			if (crop.hasCropSticks()) {
				this.getGenome(stack).ifPresent(genome ->
						this.getGrowthStage(stack).ifPresent(growth -> {
							this.removePlant(stack);
							crop.plantGenome(genome, player);
							crop.setGrowthStage(growth);
						}));
			}
			return InteractionResult.SUCCESS;
		} else {
			if (player != null) {
				player.sendSystemMessage(Component.translatable("agricraft.message.trowel_no_plant"));
			}
			return InteractionResult.FAIL;
		}
	}

	protected InteractionResult tryPlantOnSoil(Level level, BlockPos pos, ItemStack stack, Player player) {
		if (this.hasPlant(stack)) {
			return AgriApi.getCrop(level, pos.above())
					.map(crop -> this.tryPlantOnCropSticks(crop, stack, player))
					.orElseGet(() -> this.tryNewPlant(level, pos.above(), stack, player));
		}
		return InteractionResult.FAIL;
	}

	protected InteractionResult tryNewPlant(Level world, BlockPos pos, ItemStack stack, @Nullable Player player) {
		if (AgriCraftConfig.PLANT_OFF_CROP_STICKS.get()) {
			CropBlock cropBlock = ModBlocks.CROP.get();
			BlockState newState = cropBlock.blockStatePlant(cropBlock.defaultBlockState());
			if (world.setBlock(pos, newState, 3)) {
				boolean success = AgriApi.getCrop(world, pos).map(crop -> this.getGenome(stack).map(genome ->
								this.getGrowthStage(stack).map(stage -> {
									crop.plantGenome(genome, player);
									crop.setGrowthStage(stage);
									this.removePlant(stack);
									return true;
								}).orElse(false))
						.orElse(false)).orElse(false);
				if (success) {
					return InteractionResult.SUCCESS;
				} else {
					world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
				}
			}
		}
		return InteractionResult.FAIL;
	}

	public void setPlant(ItemStack stack, AgriGenome genome, AgriGrowthStage stage) {
		if (this.hasPlant(stack)) {
			return;
		}
		stack.set(ModDataComponentTypes.GENOME, genome);
		stack.set(ModDataComponentTypes.TROWEL_DATA, new TrowelData(stage.index(), stage.total()));
	}

	public void removePlant(ItemStack stack) {
		stack.remove(ModDataComponentTypes.GENOME);
		stack.remove(ModDataComponentTypes.TROWEL_DATA);
	}

	public boolean hasPlant(ItemStack itemStack) {
		return itemStack.has(ModDataComponentTypes.GENOME);
	}

	public Optional<AgriGrowthStage> getGrowthStage(ItemStack stack) {
		TrowelData data = stack.get(ModDataComponentTypes.TROWEL_DATA);
		if (data != null) {
			return Optional.of(new AgriGrowthStage(data.index, data.total));
		}
		return Optional.empty();
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		tooltipComponents.add(Component.translatable("agricraft.tooltip.trowel").withStyle(ChatFormatting.DARK_GRAY));
		AgriGenome genome = stack.get(ModDataComponentTypes.GENOME);
		if (genome != null) {
			genome.appendHoverText(tooltipComponents, TooltipFlag.ADVANCED);
		}
	}

	public record TrowelData(int index, int total) {

		public static Codec<TrowelData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.INT.fieldOf("index").forGetter(TrowelData::index),
				Codec.INT.fieldOf("total").forGetter(TrowelData::total)
		).apply(instance, TrowelData::new));
		public static StreamCodec<ByteBuf, TrowelData> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.INT, TrowelData::index,
				ByteBufCodecs.INT, TrowelData::total,
				TrowelData::new
		);

	}

}

