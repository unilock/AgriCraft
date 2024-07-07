package com.agricraft.agricraft.api;

import com.agricraft.agricraft.api.adapter.AgriAdapter;
import com.agricraft.agricraft.api.codecs.AgriMutation;
import com.agricraft.agricraft.api.codecs.AgriSoil;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.agricraft.agricraft.api.fertilizer.AgriFertilizer;
import com.agricraft.agricraft.api.genetic.AgriGene;
import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.agricraft.agricraft.api.genetic.AgriMutationHandler;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.api.plant.AgriPlantModifierFactory;
import com.agricraft.agricraft.api.plant.AgriWeed;
import com.agricraft.agricraft.api.requirement.AgriGrowthCondition;
import com.agricraft.agricraft.api.requirement.SeasonLogic;
import com.agricraft.agricraft.api.stat.AgriStat;
import com.agricraft.agricraft.api.tools.journal.JournalData;
import com.agricraft.agricraft.api.tools.seedbag.BagSorter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.stream.Stream;

public interface AgriApi {

	String MOD_ID = "agricraft";

	static AgriApi get() {
		return InstanceHolder.instance;
	}

	static void set(AgriApi instance) {
		InstanceHolder.instance = instance;
	}

	Registry<AgriGene<?>> getGeneRegistry();

	Registry<AgriStat> getStatRegistry();

	Registry<AgriGrowthCondition<?>> getGrowthConditionRegistry();

	Registry<AgriPlantModifierFactory> getPlantModifierFactoryRegistry();

	Optional<Registry<AgriPlant>> getPlantRegistry();

	Optional<Registry<AgriPlant>> getPlantRegistry(RegistryAccess registryAccess);

	Optional<Registry<AgriWeed>> getWeedRegistry();

	Optional<Registry<AgriWeed>> getWeedRegistry(RegistryAccess registryAccess);

	Optional<Registry<AgriSoil>> getSoilRegistry();

	Optional<Registry<AgriSoil>> getSoilRegistry(RegistryAccess registryAccess);

	Optional<Registry<AgriMutation>> getMutationRegistry();

	Optional<Registry<AgriMutation>> getMutationRegistry(RegistryAccess registryAccess);

	Optional<Registry<AgriFertilizer>> getFertilizerRegistry();

	Optional<Registry<AgriFertilizer>> getFertilizerRegistry(RegistryAccess registryAccess);


	default Optional<AgriPlant> getPlant(ResourceLocation id) {
		return getPlantRegistry().flatMap(reg -> reg.getOptional(id));
	}

	default Optional<AgriPlant> getPlant(ResourceLocation id, RegistryAccess registryAccess) {
		return getPlantRegistry(registryAccess).flatMap(reg -> reg.getOptional(id));
	}

	default Optional<AgriWeed> getWeed(ResourceLocation id) {
		return this.getWeedRegistry().flatMap(reg -> reg.getOptional(id));
	}

	default Optional<AgriWeed> getWeed(ResourceLocation id, RegistryAccess registryAccess) {
		return this.getWeedRegistry(registryAccess).flatMap(reg -> reg.getOptional(id));
	}

	default Optional<AgriSoil> getSoil(BlockGetter level, BlockPos pos) {
		return getSoilRegistry().flatMap(reg -> getSoil(reg, level, pos));
	}

	default Optional<AgriSoil> getSoil(BlockGetter level, BlockPos pos, RegistryAccess registryAccess) {
		return getSoilRegistry(registryAccess).flatMap(reg -> getSoil(reg, level, pos));
	}

	default Optional<AgriSoil> getSoil(Registry<AgriSoil> registry, BlockGetter level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		return registry.stream().filter(soil -> soil.isVariant(blockState)).findFirst();
	}

	default Optional<AgriMutation> getMutation(ResourceLocation id) {
		return getMutationRegistry().flatMap(reg -> reg.getOptional(id));
	}

	default Stream<AgriMutation> getMutationsFromParents(String parent1, String parent2) {
		ResourceLocation p1 = ResourceLocation.parse(parent1);
		ResourceLocation p2 = ResourceLocation.parse(parent2);
		return getMutationRegistry().map(reg -> reg.stream()
						.filter(mutation -> (mutation.parent1().equals(p1) && mutation.parent2().equals(p2))
								|| (mutation.parent1().equals(p2) && mutation.parent2().equals(p1))))
				.orElse(Stream.empty());
	}

	default Optional<AgriCrop> getCrop(BlockGetter level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof AgriCrop crop) {
			return Optional.of(crop);
		}
		return Optional.empty();
	}

	Optional<JournalData> getJournalData(ItemStack itemStack);

	Optional<AgriGenome> getGenome(ItemStack itemStack);

	void registerGenomeAdapter(AgriAdapter<AgriGenome> adapter);

	Optional<AgriAdapter<AgriGenome>> getGenomeAdapter(Object obj);

	void registerFertilizerAdapter(AgriAdapter<AgriFertilizer> adapter);

	Optional<AgriAdapter<AgriFertilizer>> getFertilizerAdapter(Object obj);

	default Optional<AgriFertilizer> getFertilizer(ItemStack itemStack) {
		return getFertilizerAdapter(itemStack).flatMap(adapter -> adapter.valueOf(itemStack));
	}

	SeasonLogic getSeasonLogic();

	AgriMutationHandler getMutationHandler();

	void setMutationHandler(AgriMutationHandler handler);

	void registerSeedBagSorter(BagSorter sorter);

	@ApiStatus.Internal
	final class InstanceHolder {

		private static AgriApi instance = null;

		private InstanceHolder() {
		}

	}

}
