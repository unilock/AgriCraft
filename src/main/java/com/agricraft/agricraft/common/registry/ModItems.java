package com.agricraft.agricraft.common.registry;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.common.block.CropStickVariant;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import com.agricraft.agricraft.common.item.ClipperItem;
import com.agricraft.agricraft.common.item.CropSticksItem;
import com.agricraft.agricraft.common.item.DebuggerItem;
import com.agricraft.agricraft.common.item.JournalItem;
import com.agricraft.agricraft.common.item.MagnifyingGlassItem;
import com.agricraft.agricraft.common.item.RakeItem;
import com.agricraft.agricraft.common.item.SeedBagItem;
import com.agricraft.agricraft.common.item.TrowelItem;
import net.minecraft.util.Unit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AgriApi.MOD_ID);

	public static final DeferredItem<DebuggerItem> DEBUGGER = ITEMS.register("debugger", () -> new DebuggerItem(new Item.Properties()));

	public static final DeferredItem<JournalItem> JOURNAL = ITEMS.register("journal", () -> new JournalItem(new Item.Properties().component(ModDataComponentTypes.JOURNAL_DATA.get(), new JournalItem.Data())));
	public static final DeferredItem<BlockItem> SEED_ANALYZER = ITEMS.register("seed_analyzer", () -> new BlockItem(ModBlocks.SEED_ANALYZER.get(), new Item.Properties()));
	public static final DeferredItem<ClipperItem> CLIPPER = ITEMS.register("clipper", () -> new ClipperItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<MagnifyingGlassItem> MAGNIFYING_GLASS = ITEMS.register("magnifying_glass", () -> new MagnifyingGlassItem(new Item.Properties().component(ModDataComponentTypes.MAGNIFYING.get(), Unit.INSTANCE).stacksTo(1)));
	public static final DeferredItem<RakeItem> WOODEN_RAKE = ITEMS.register("wooden_rake", () -> new RakeItem(new Item.Properties(), RakeItem.WOOD_LOGIC));
	public static final DeferredItem<RakeItem> IRON_RAKE = ITEMS.register("iron_rake", () -> new RakeItem(new Item.Properties(), RakeItem.IRON_LOGIC));
	public static final DeferredItem<TrowelItem> TROWEL = ITEMS.register("trowel", () -> new TrowelItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<CropSticksItem> WOODEN_CROP_STICKS = ITEMS.register("wooden_crop_sticks", () -> new CropSticksItem(ModBlocks.CROP.get(), CropStickVariant.WOODEN));
	public static final DeferredItem<CropSticksItem> IRON_CROP_STICKS = ITEMS.register("iron_crop_sticks", () -> new CropSticksItem(ModBlocks.CROP.get(), CropStickVariant.IRON));
	public static final DeferredItem<CropSticksItem> OBSIDIAN_CROP_STICKS = ITEMS.register("obsidian_crop_sticks", () -> new CropSticksItem(ModBlocks.CROP.get(), CropStickVariant.OBSIDIAN));
	public static final DeferredItem<SeedBagItem> SEED_BAG = ITEMS.register("seed_bag", () -> new SeedBagItem(new Item.Properties().component(ModDataComponentTypes.SEED_BAG_DATA.get(), new SeedBagItem.Data()).stacksTo(1)));
	public static final DeferredItem<AgriSeedItem> SEED = ITEMS.register("seed", () -> new AgriSeedItem(new Item.Properties()));

	public static final DeferredItem<Item> COAL_PEBBLE = ITEMS.register("coal_pebble", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> EMERALD_SHARD = ITEMS.register("emerald_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> QUARTZ_SHARD = ITEMS.register("quartz_shard", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> NETHERITE_SLIVER = ITEMS.register("netherite_sliver", () -> new Item(new Item.Properties()));
	public static final DeferredItem<Item> AMATHYLLIS_PETAL = ITEMS.register("amathyllis_petal", () -> new Item(new Item.Properties()));

	public static void addItemsToTabs(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
		if (itemDisplayParameters.hasPermissions()) {
			output.accept(DEBUGGER.get());
		}
		output.accept(JOURNAL.get().getDefaultInstance());
		output.accept(SEED_ANALYZER.get());
		output.accept(CLIPPER.get());
		output.accept(MAGNIFYING_GLASS.get());
		output.accept(WOODEN_RAKE.get());
		output.accept(IRON_RAKE.get());
		output.accept(TROWEL.get());
		output.accept(WOODEN_CROP_STICKS.get());
		output.accept(IRON_CROP_STICKS.get());
		output.accept(OBSIDIAN_CROP_STICKS.get());
		output.accept(SEED_BAG.get());

		output.accept(COAL_PEBBLE.get());
		output.accept(COPPER_NUGGET.get());
		output.accept(DIAMOND_SHARD.get());
		output.accept(EMERALD_SHARD.get());
		output.accept(QUARTZ_SHARD.get());
		output.accept(NETHERITE_SLIVER.get());
		output.accept(AMATHYLLIS_PETAL.get());
	}

}
