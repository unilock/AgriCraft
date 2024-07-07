package com.agricraft.agricraft.common.block.entity;

import com.agricraft.agricraft.common.block.SeedAnalyzerBlock;
import com.agricraft.agricraft.common.inventory.container.SeedAnalyzerMenu;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import com.agricraft.agricraft.common.item.JournalItem;
import com.agricraft.agricraft.common.registry.AgriBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SeedAnalyzerBlockEntity extends BlockEntity implements MenuProvider {

	public static final int SEED_SLOT = 0;
	public static final int JOURNAL_SLOT = 1;
	private SimpleContainer inventory;


	public SeedAnalyzerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(AgriBlockEntities.SEED_ANALYZER.get(), blockPos, blockState);
		this.inventory = new SimpleContainer(2);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("journal")) {
			ItemStack journal = ItemStack.parseOptional(registries, tag.getCompound("journal"));
			this.inventory.setItem(JOURNAL_SLOT, journal);
		}
		if (tag.contains("seed")) {
			ItemStack seed = ItemStack.parseOptional(registries, tag.getCompound("seed"));
			this.inventory.setItem(SEED_SLOT, seed);
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (hasJournal()) {
			tag.put("journal", getJournal().save(registries));
		}
		if (hasSeed()) {
			tag.put("seed", getSeed().save(registries));
		}
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return super.saveWithoutMetadata(registries);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public boolean hasSeed() {
		return !this.inventory.getItem(SEED_SLOT).isEmpty();
	}

	public ItemStack getSeed() {
		return this.inventory.getItem(SEED_SLOT);
	}

	public boolean insertSeed(ItemStack seed, @Nullable LivingEntity entity) {
		if (this.inventory.getItem(SEED_SLOT).isEmpty()) {
			ItemStack stack = seed.consumeAndReturn(1, entity);
			this.inventory.setItem(SEED_SLOT, stack);
			if (hasJournal()) {
				JournalItem.researchPlant(this.getJournal(), ResourceLocation.parse(AgriSeedItem.getSpecies(seed)));
			}
			this.setChanged();
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
			return true;
		}
		return false;
	}

	public ItemStack extractSeed() {
		ItemStack stack = this.inventory.getItem(SEED_SLOT).copy();
		this.inventory.setItem(SEED_SLOT, ItemStack.EMPTY);
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		return stack;
	}

	public boolean hasJournal() {
		return !this.inventory.getItem(JOURNAL_SLOT).isEmpty();
	}

	public ItemStack getJournal() {
		return this.inventory.getItem(JOURNAL_SLOT);
	}

	public boolean insertJournal(ItemStack journal, @Nullable LivingEntity entity) {
		if (this.inventory.getItem(JOURNAL_SLOT).isEmpty()) {
			this.inventory.setItem(JOURNAL_SLOT, journal.consumeAndReturn(1, entity));
			this.setChanged();
			BlockState state = this.getBlockState().setValue(SeedAnalyzerBlock.JOURNAL, true);
			level.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
			return true;
		}
		return false;
	}

	public ItemStack extractJournal() {
		ItemStack stack = this.inventory.getItem(JOURNAL_SLOT).copy();
		this.inventory.setItem(JOURNAL_SLOT, ItemStack.EMPTY);
		this.setChanged();
		BlockState state = this.getBlockState().setValue(SeedAnalyzerBlock.JOURNAL, false);
		level.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		return stack;
	}

	public SimpleContainer getInventory() {
		return inventory;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("screen.agricraft.seed_analyzer");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		return new SeedAnalyzerMenu(i, inventory, player, this.worldPosition);
	}

}
