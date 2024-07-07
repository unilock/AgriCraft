package com.agricraft.agricraft.common.item;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.tools.journal.JournalData;
import com.agricraft.agricraft.api.tools.journal.JournalPage;
import com.agricraft.agricraft.client.ClientUtil;
import com.agricraft.agricraft.common.block.entity.SeedAnalyzerBlockEntity;
import com.agricraft.agricraft.common.item.journal.EmptyPage;
import com.agricraft.agricraft.common.item.journal.FrontPage;
import com.agricraft.agricraft.common.item.journal.GeneticsPage;
import com.agricraft.agricraft.common.item.journal.GrowthReqsPage;
import com.agricraft.agricraft.common.item.journal.IntroductionPage;
import com.agricraft.agricraft.common.item.journal.MutationsPage;
import com.agricraft.agricraft.common.item.journal.PlantPage;
import com.agricraft.agricraft.common.registry.AgriDataComponents;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class JournalItem extends Item {

	public JournalItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		ItemStack stack = player.getItemInHand(usedHand);
		if (player.isDiscrete()) {
			return InteractionResultHolder.pass(stack);
		}
		if (level.isClientSide) {
			ClientUtil.openJournalScreen(player, usedHand);
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.pass(stack);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (level.isClientSide) {
			return InteractionResult.CONSUME;
		}
		ItemStack heldItem = context.getItemInHand();
		// if a seed analyzer was clicked, insert the journal inside
		if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof SeedAnalyzerBlockEntity seedAnalyzer) {
			if (seedAnalyzer.insertJournal(heldItem, context.getPlayer())) {
				context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
			}
			return seedAnalyzer.insertJournal(heldItem, context.getPlayer()) ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
		}
		return super.useOn(context);
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = new ItemStack(this);
//		researchPlant(stack, new ResourceLocation("minecraft:wheat"));
		return stack;
	}

	public static void researchPlant(ItemStack journal, ResourceLocation plantId) {
		Data data = journal.get(AgriDataComponents.JOURNAL_DATA.get());
		if (data != null && !data.plants.contains(plantId)) {
			journal.set(AgriDataComponents.JOURNAL_DATA, data.addPlant(plantId));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		Data data = stack.get(AgriDataComponents.JOURNAL_DATA);
		int size = data == null ? 0 : data.getDiscoveredSeeds().size();
		tooltipComponents.add(Component.translatable("agricraft.tooltip.journal", size).withStyle(ChatFormatting.GRAY));
	}

	public static JournalData getJournalData(ItemStack journal) {
		return journal.get(AgriDataComponents.JOURNAL_DATA);
	}

	public static class Data implements JournalData {

		public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ResourceLocation.CODEC.listOf().fieldOf("plants").forGetter(data -> data.plants)
		).apply(instance, Data::new));
		public static StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
				ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), data -> data.plants,
				Data::new
		);

		private final ImmutableList<ResourceLocation> plants;
		private final List<JournalPage> pages;

		public Data() {
			this.plants = ImmutableList.of();
			this.pages = new ArrayList<>();
			this.initializePages();
		}

		public Data(Collection<ResourceLocation> plants) {
			ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
			plants.stream().sorted(Comparator.comparing(ResourceLocation::toString)).forEach(builder::add);
			this.plants = builder.build();
			this.pages = new ArrayList<>();
			this.initializePages();
		}

		private Data(ImmutableList<ResourceLocation> plants) {
			this.plants = plants;
			this.pages = new ArrayList<>();
			this.initializePages();
		}

		public Data addPlant(ResourceLocation plant) {
			ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
			Stream.concat(plants.stream(), Stream.of(plant)).sorted(Comparator.comparing(ResourceLocation::toString)).forEach(builder::add);
			return new Data(builder.build());
		}

		public void initializePages() {
			this.pages.clear();
			this.pages.add(new FrontPage());
			this.pages.add(new IntroductionPage());
			this.pages.add(new GeneticsPage());
			this.pages.add(new GrowthReqsPage());
			for (ResourceLocation plant : this.plants) {
				if (AgriApi.get().getPlant(plant).isEmpty()) {
					continue;
				}
				PlantPage plantPage = new PlantPage(plant, plants);
				this.pages.add(plantPage);
				List<List<ResourceLocation>> mutations = plantPage.getMutationsOffPage();
				int size = mutations.size();
				if (size > 0) {
					int remaining = size;
					int from = 0;
					int to = Math.min(remaining, MutationsPage.LIMIT);
					while (remaining > 0) {
						pages.add(new MutationsPage(mutations.subList(from, to)));
						remaining -= (to - from);
						from = to;
						to = from + Math.min(remaining, MutationsPage.LIMIT);
					}
				}
			}
			// TODO: @Ketheroth send modify page event
		}

		@Override
		public JournalPage getPage(int index) {
			if (0 <= index && index < this.pages.size()) {
				return this.pages.get(index);
			}
			return new EmptyPage();
		}

		@Override
		public int size() {
			return this.pages.size();
		}

		@Override
		public List<ResourceLocation> getDiscoveredSeeds() {
			return this.plants;
		}

		@Override
		public final boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof Data data)) {
				return false;
			}

			return plants.equals(data.plants) && pages.equals(data.pages);
		}

		@Override
		public int hashCode() {
			int result = plants.hashCode();
			result = 31 * result + pages.hashCode();
			return result;
		}

	}

}
