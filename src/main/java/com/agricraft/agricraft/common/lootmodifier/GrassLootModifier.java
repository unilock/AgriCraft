package com.agricraft.agricraft.common.lootmodifier;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.common.item.AgriSeedItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.List;

public class GrassLootModifier extends LootModifier {

	public static final MapCodec<GrassLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
			LootModifier.codecStart(instance).and(instance.group(
					Codec.BOOL.fieldOf("reset").forGetter(e -> e.reset),
					Codec.DOUBLE.fieldOf("chance").forGetter(e -> e.chance),
					Entry.CODEC.listOf().fieldOf("seeds").forGetter(e -> e.entries)
			)).apply(instance, GrassLootModifier::new)
	);

	private final boolean reset;
	private final double chance;
	private final List<Entry> entries;
	private final int totalWeight;

	public GrassLootModifier(LootItemCondition[] conditionsIn, boolean reset, double chance, List<Entry> entries) {
		super(conditionsIn);
		this.reset = reset;
		this.chance = chance;
		this.entries = entries;
		this.totalWeight = entries.stream().mapToInt(Entry::weight).sum();
	}

	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (AgriCraftConfig.ALLOW_GRASS_DROP_RESETS.get() && this.reset) {
			generatedLoot.clear();
		}
		if (generatedLoot.isEmpty() && this.roll(context.getRandom()) && !this.entries.isEmpty()) {
			Entry entry = selectRandomEntry(context.getRandom());
			if (entry != null) {
				ItemStack stack = entry.generateSeed(context.getRandom());
				if (!stack.isEmpty()) {
					generatedLoot.add(stack);
				}
			}
		}
		return generatedLoot;
	}

	protected boolean roll(RandomSource random) {
		return random.nextDouble() < this.chance;
	}

	protected Entry selectRandomEntry(RandomSource random) {
		int i = random.nextInt(this.totalWeight);
		for (Entry entry : this.entries) {
			if (entry.weight >= i && entry.getPlant() != null) {
				return entry;
			}
			i -= entry.weight;
		}
		return null;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC;
	}

	public record Entry(ResourceLocation plantId, int minStat, int maxStat, int weight) {

		public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				ResourceLocation.CODEC.fieldOf("plant").forGetter(Entry::plantId),
				Codec.INT.fieldOf("min_stat").forGetter(Entry::minStat),
				Codec.INT.fieldOf("max_stat").forGetter(Entry::maxStat),
				Codec.INT.fieldOf("weight").forGetter(Entry::weight)
		).apply(instance, Entry::new));

		public AgriPlant getPlant() {
			return AgriApi.get().getPlant(this.plantId).orElse(null);
		}

		public ItemStack generateSeed(RandomSource random) {
			AgriPlant plant = this.getPlant();
			if (plant == null) {
				return ItemStack.EMPTY;
			}
			return AgriSeedItem.toStack(new AgriGenome.Builder(this.plantId.toString()).randomStats(stat -> random.nextIntBetweenInclusive(this.minStat, this.maxStat)).build());
		}

	}

}
