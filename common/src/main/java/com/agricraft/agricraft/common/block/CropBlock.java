package com.agricraft.agricraft.common.block;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.config.CoreConfig;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.client.ClientUtil;
import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import com.agricraft.agricraft.common.item.CropSticksItem;
import com.agricraft.agricraft.common.registry.ModItems;
import com.agricraft.agricraft.common.util.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class CropBlock extends Block implements EntityBlock, BonemealableBlock, SimpleFluidloggedBlock {

	public static final VoxelShape SINGLE_STICKS = Stream.of(
			Block.box(2, -3, 2, 3, 14, 3),
			Block.box(13, -3, 2, 14, 14, 3),
			Block.box(2, -3, 13, 3, 14, 14),
			Block.box(13, -3, 13, 14, 14, 14)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	public static final VoxelShape CROSS_STICKS = Stream.of(
			SINGLE_STICKS,
			Block.box(0, 11, 2, 16, 12, 3),
			Block.box(0, 11, 13, 16, 12, 14),
			Block.box(2, 11, 0, 3, 12, 16),
			Block.box(13, 11, 0, 14, 12, 16)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	public static final VoxelShape CROP_STICKS = Stream.of(
			Block.box(2, 0, 2, 3, 14, 3),
			Block.box(13, 0, 2, 14, 14, 3),
			Block.box(2, 0, 13, 3, 14, 14),
			Block.box(13, 0, 13, 14, 14, 14)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	public static final EnumProperty<CropStickVariant> STICK_VARIANT = EnumProperty.create("variant", CropStickVariant.class, CropStickVariant.values());
	public static final EnumProperty<CropState> CROP_STATE = EnumProperty.create("crop", CropState.class, CropState.values());
	public static final IntegerProperty LIGHT = IntegerProperty.create("light", 0, 16);
	private static final ItemStack BONE_MEAL = new ItemStack(Items.BONE_MEAL);

	public CropBlock() {
		super(Properties.of()
				.mapColor(MapColor.PLANT)
				.pushReaction(PushReaction.DESTROY)
				.isRedstoneConductor((state, getter, pos) -> false)
				.randomTicks()
				.noOcclusion()
				.forceSolidOff()
				.noParticlesOnBreak()
				.lightLevel(blockState -> blockState.getValue(LIGHT))
				.sound(SoundType.CROP));
		this.registerDefaultState(this.stateDefinition.any().setValue(SimpleFluidloggedBlock.LAVALOGGED, false)
				.setValue(SimpleFluidloggedBlock.WATERLOGGED, false)
				.setValue(STICK_VARIANT, CropStickVariant.WOODEN)
				.setValue(CROP_STATE, CropState.SINGLE_STICKS)
				.setValue(LIGHT, 0));
	}

	public static void spawnItem(Level level, BlockPos pos, ItemStack stack) {
		level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
	}

	public static InteractionResult applyCropSticks(Level level, BlockPos pos, BlockState state, CropStickVariant variant) {
		if (variant == null) {
			return InteractionResult.FAIL;
		}
		if (level.isClientSide()) {
			return InteractionResult.PASS;
		}
		CropState cropState = state.getValue(CROP_STATE);
		BlockState newState = state;
		if (cropState == CropState.PLANT) {
			newState = state.setValue(CROP_STATE, CropState.PLANT_STICKS);
		} else if (cropState == CropState.SINGLE_STICKS && state.getValue(STICK_VARIANT) == variant) {
			newState = state.setValue(CROP_STATE, CropState.DOUBLE_STICKS);
		}
		if (newState == state) {
			return InteractionResult.FAIL;
		} else {
			level.setBlock(pos, newState, 3);
			if (cropState.hasSticks()) {
				variant.playSound(level, pos);
			} else {
				SoundType sound = Blocks.WHEAT.getSoundType(Blocks.WHEAT.defaultBlockState());
				level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);
			}
			return InteractionResult.SUCCESS;
		}
	}

	public static InteractionResultHolder<CropStickVariant> removeCropSticks(Level level, BlockPos pos, BlockState state) {
		CropStickVariant stickVariant = state.getValue(STICK_VARIANT);
		if (level.isClientSide()) {
			return InteractionResultHolder.pass(stickVariant);
		}
		CropState cropState = state.getValue(CROP_STATE);
		BlockState newState = state;
		if (!cropState.hasPlant()) {
			newState = state.setValue(CROP_STATE, CropState.SINGLE_STICKS);
		}
		if (newState == state) {
			return InteractionResultHolder.fail(stickVariant);
		} else {
			level.setBlock(pos, newState, 3);
			if (cropState == CropState.DOUBLE_STICKS) {
				stickVariant.playSound(level, pos);
			}
			return InteractionResultHolder.success(stickVariant);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SimpleFluidloggedBlock.LAVALOGGED);
		builder.add(SimpleFluidloggedBlock.WATERLOGGED);
		builder.add(STICK_VARIANT);
		builder.add(CROP_STATE);
		builder.add(LIGHT);
	}

	@Override
	@NotNull
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		CropState cropState = state.getValue(CROP_STATE);
		if (cropState == CropState.DOUBLE_STICKS) {
			return CROSS_STICKS;
		}
		if (level.getBlockEntity(pos) instanceof CropBlockEntity cbe) {
			if (!cbe.hasPlant() && !cbe.hasWeeds()) {
				return SINGLE_STICKS;
			}
			// shape is dependant of the plant and the weed
			return cropState.hasSticks() ? Shapes.join(CROP_STICKS, cbe.getShape(), BooleanOp.OR) : cbe.getShape();
		}
		return super.getShape(state, level, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		CropState cropState = state.getValue(CROP_STATE);
		if (CoreConfig.cropSticksCollide) {
			if (cropState.hasSticks()) {
				return cropState == CropState.DOUBLE_STICKS ? CROSS_STICKS : SINGLE_STICKS;
			}
		}
		return Shapes.empty();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = this.defaultBlockState();
		if (context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.LAVA)) {
			state = state.setValue(SimpleFluidloggedBlock.LAVALOGGED, true);
		} else if (context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER)) {
			state = state.setValue(SimpleFluidloggedBlock.WATERLOGGED, true);
		}
		ItemStack stack = context.getItemInHand();
		if (stack.getItem() instanceof CropSticksItem) {
			state = this.blockStateCropStick(state, CropStickVariant.fromItem(stack));
		} else if (stack.getItem() instanceof AgriSeedItem) {
			if (!CoreConfig.plantOffCropSticks) {
				return null;
			}
			state = this.blockStatePlant(state);
		}
		return state;
	}

	public BlockState blockStateCropStick(BlockState state, CropStickVariant variant) {
		return state.setValue(STICK_VARIANT, variant);  // crop_state should already be single_sticks
	}

	public BlockState blockStatePlant(BlockState state) {
		return state.setValue(CROP_STATE, CropState.PLANT);  // stick_variant should already be wooden
	}

	@Override
	@NotNull
	public FluidState getFluidState(BlockState pState) {
		if (pState.getValue(SimpleFluidloggedBlock.LAVALOGGED)) {
			return Fluids.LAVA.getSource(false);
		}
		if (pState.getValue(SimpleFluidloggedBlock.WATERLOGGED)) {
			return Fluids.WATER.getSource(false);
		}
		return super.getFluidState(pState);
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return Platform.get().createCropBlockEntity(pos, state);
	}

	@Override
	@NotNull
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		Optional<AgriCrop> optional = AgriApi.getCrop(level, pos);
		if (optional.isEmpty()) {
			return InteractionResult.FAIL;
		}
		AgriCrop crop = optional.get();
		// TODO: @Ketheroth future: run plant pre logic
		// plant pre logic
		if (crop.hasPlant()) {
			Optional<InteractionResult> result = crop.getPlant().onRightClickPre(crop, player.getItemInHand(hand), player);
			if (result.isPresent()) {
				return result.get();
			}
		}
		// crop logic
		InteractionResult result = rightClickLogic(player.getItemInHand(hand), state, level, pos, player, hand, crop);
		// plant post logic
		if (crop.hasPlant()) {
			Optional<InteractionResult> override = crop.getPlant().onRightClickPost(crop, player.getItemInHand(hand), player);
			if (override.isPresent()) {
				return override.get();
			}
		}
		return result;
	}

	protected InteractionResult rightClickLogic(ItemStack heldItem, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, AgriCrop crop) {
		// do nothing from off hand
		if (hand == InteractionHand.OFF_HAND) {
			return InteractionResult.PASS;
		}
		// TODO: @Ketheroth replace with item tag
		if (heldItem.is(ModItems.CLIPPER.get()) || heldItem.is(ModItems.IRON_RAKE.get()) || heldItem.is(ModItems.WOODEN_RAKE.get())) {
			return InteractionResult.PASS;
		}
		if (AgriApi.getFertilizerAdapter(heldItem).isPresent()) {
			return AgriApi.getFertilizerAdapter(heldItem).get().valueOf(heldItem).map(fertilizer -> {
				if (crop.acceptsFertilizer(fertilizer)) {
					InteractionResult result = fertilizer.applyFertilizer(level, pos, crop, heldItem, level.random, player);
					if (result == InteractionResult.CONSUME || result == InteractionResult.SUCCESS) {
						crop.onApplyFertilizer(fertilizer, level.random);
						if (crop.hasPlant()) {
							crop.getPlant().onFertilized(crop, heldItem, level.random);
						}
					}
					return result;
				}
				return InteractionResult.CONSUME;
			}).orElse(InteractionResult.PASS);
		}
		// placement of crop sticks or creation of cross crop
		if (heldItem.getItem() instanceof CropSticksItem) {
			InteractionResult result = applyCropSticks(level, pos, state, CropStickVariant.fromItem(heldItem));
			if (result == InteractionResult.SUCCESS) {
				if (!player.isCreative()) {
					player.getItemInHand(hand).shrink(1);
				}
				return result;
			}
		}
		// planting from seed
		Optional<AgriGenome> genome = AgriApi.getGenomeAdapter(heldItem).flatMap(adapter -> adapter.valueOf(heldItem));
		if (genome.isPresent()) {
			if (!crop.isCrossCropSticks() && !crop.hasPlant()) {
				crop.plantGenome(genome.get());
				if (!player.isCreative()) {
					player.getItemInHand(hand).shrink(1);
				}
				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.CONSUME;
			}
		}
		// harvesting or de-cross-crop'ing if nothing else checks out
		if (crop.isCrossCropSticks()) {
			InteractionResultHolder<CropStickVariant> result = removeCropSticks(level, pos, state);
			if (result.getResult() == InteractionResult.SUCCESS) {
				if (!player.isCreative()) {
					spawnItem(level, pos, CropStickVariant.toItem(result.getObject()));
				}
				return InteractionResult.CONSUME;
			}
		} else if (crop.hasPlant() && crop.canBeHarvested()) {
			crop.harvest(itemStack -> spawnItem(level, pos, itemStack), player);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	@Override
	@NotNull
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		if (level.getBlockEntity(pos) instanceof AgriCrop crop) {
			if (crop.hasPlant()) {
				// prioritize the plant if there is any
				return AgriSeedItem.toStack(crop.getGenome());
			}
			if (crop.hasCropSticks()) {
				return CropStickVariant.toItem(state.getValue(STICK_VARIANT));
			}
		}
		return super.getCloneItemStack(level, pos, state);
	}

	@Override
	protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
		this.spawnDestroyParticles(level, state, pos);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(SimpleFluidloggedBlock.LAVALOGGED)) {
			level.scheduleTick(pos, Fluids.LAVA, Fluids.LAVA.getTickDelay(level));
		} else if (state.getValue(SimpleFluidloggedBlock.WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		if (!state.canSurvive(level, pos)) {
			this.spawnDestroyParticles(level, state, pos);
			if (state.getValue(SimpleFluidloggedBlock.LAVALOGGED)) {
				return Fluids.LAVA.defaultFluidState().createLegacyBlock();
			} else if (state.getValue(SimpleFluidloggedBlock.WATERLOGGED)) {
				return Fluids.WATER.defaultFluidState().createLegacyBlock();
			}
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return AgriApi.getSoil(level, pos.below(), level.registryAccess()).isPresent();
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return AgriApi.getCrop(level, pos).map(crop -> crop.hasPlant() && crop.isFertile() && !crop.isFullyGrown()).orElse(false);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return AgriApi.getFertilizer(BONE_MEAL).flatMap(fertilizer ->
				AgriApi.getCrop(level, pos).map(crop -> !crop.isFullyGrown() && crop.acceptsFertilizer(fertilizer))
		).orElse(false);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		// transfert the right click with a bonemeal to the block entity
		AgriApi.getFertilizer(BONE_MEAL).ifPresent(fertilizer ->
				AgriApi.getCrop(level, pos).ifPresent(crop -> fertilizer.applyFertilizer(level, pos, crop, BONE_MEAL, random, null))
		);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (state.getValue(STICK_VARIANT) == CropStickVariant.WOODEN && level.getFluidState(pos).is(Fluids.LAVA)) {
			this.spawnDestroyParticles(level, state, pos);
			level.destroyBlock(pos, true);
			return;
		}
		AgriApi.getCrop(level, pos).ifPresent(agriCrop -> {
			if (agriCrop.hasPlant()) {
				agriCrop.getPlant().onRandomTick(agriCrop, random);
			}
			agriCrop.applyGrowthTick();
		});
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		AgriApi.getCrop(level, pos).ifPresent(crop -> {
			if (crop.hasPlant()) {
				crop.getPlant().spawnParticles(crop, random);
			}
		});
	}

	@Override
	public void attack(BlockState state, Level level, BlockPos pos, Player player) {
		AgriApi.getCrop(level, pos).ifPresent(crop -> {
			if (crop.hasPlant()) {
				crop.getPlant().onBroken(crop, player);
			}
		});
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
		List<ItemStack> drops = new ArrayList<>();
		BlockEntity tile = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (tile == null) {
			return drops;
		}
		CropState cropState = state.getValue(CROP_STATE);
		if (cropState.hasSticks()) {
			drops.add(CropStickVariant.toItem(state.getValue(STICK_VARIANT), cropState == CropState.DOUBLE_STICKS ? 2 : 1));
		}
		// ask the block entity for the harvest products
		if (tile instanceof AgriCrop crop) {
			crop.getHarvestProducts(drops::add);
			if (crop.hasPlant() && (crop.isFullyGrown() || !CoreConfig.onlyMatureSeedDrops)) {
				drops.add(AgriSeedItem.toStack(crop.getGenome()));
			}
		}
		return drops;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		AgriApi.getCrop(level, pos).ifPresent(crop -> {
			if (crop.hasPlant()) {
				crop.getPlant().onEntityCollision(crop, entity);
			}
		});
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(CROP_STATE).hasPlant();
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return state.getValue(CROP_STATE).hasPlant();
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return super.getDirectSignal(state, level, pos, null);
	}

	@Override
	public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.getDirectSignal(state, level, pos, direction);
	}

	@Override
	public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return state.getValue(CROP_STATE).hasPlant() ? AgriApi.getCrop(level, pos).map(crop -> {
			if (crop.getPlant() != null) {
				return crop.getPlant().getRedstonePower(crop);
			}
			return 0;
		}).orElse(0) : 0;
	}

	private void spawnDestroyParticles(LevelAccessor level, BlockState state, BlockPos pos) {
		if (level.isClientSide() && level.getBlockEntity(pos) instanceof AgriCrop crop) {
			// we handle the break particles ourselves to mimic the used model and spawn their particles instead of ours
			CropState cropState = state.getValue(CROP_STATE);
			if (cropState.hasSticks()) {
				if (cropState == CropState.DOUBLE_STICKS) {
					ClientUtil.spawnParticlesForSticks(state.getValue(STICK_VARIANT), level, state, pos, CROSS_STICKS);
				} else {
					ClientUtil.spawnParticlesForSticks(state.getValue(STICK_VARIANT), level, state, pos, SINGLE_STICKS);
				}
			}
			if (crop.hasPlant()) {
				String plantModelId = crop.getPlantId().replace(":", ":crop/") + "_stage" + crop.getGrowthStage().index();
				if (level.getBlockEntity(pos) instanceof CropBlockEntity cbe) {
					ClientUtil.spawnParticlesForPlant(plantModelId, level, state, pos, cbe.getShape());
				}
			}
		}
	}

}
