package com.agricraft.agricraft.api;

import com.agricraft.agricraft.api.tools.journal.JournalPage;
import com.agricraft.agricraft.api.tools.journal.JournalPageDrawer;
import com.agricraft.agricraft.api.tools.magnifying.MagnifyingInspector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface AgriClientApi {

	String UNKNOWN_SEED = "agricraft:unknown";
	String UNKNOWN_PLANT = "agricraft:crop/unknown";

	static AgriClientApi get() {
		return InstanceHolder.instance;
	}

	static void set(AgriClientApi instance) {
		InstanceHolder.instance = instance;
	}

	<T extends JournalPage> void registerJournalPageDrawer(ResourceLocation id, JournalPageDrawer<T> drawer);

	<T extends JournalPage> JournalPageDrawer<T> getJournalPageDrawer(T page);

	/**
	 * Register a predicate to allow the magnifying glass overlay to render
	 *
	 * @param predicate the predicate
	 */
	void registerMagnifyingAllowingPredicate(Predicate<Player> predicate);

	Collection<Predicate<Player>> getMagnifyingAllowingPredicates();

	/**
	 * Register a magnifying inspector
	 *
	 * @param inspector the inspector to add
	 */
	void registerMagnifyingInspector(MagnifyingInspector inspector);

	Stream<MagnifyingInspector> getMagnifyingInspectors();


	default BakedModel getPlantModel(ResourceLocation plantId, int stage) {
		return getPlantModel(plantId.toString(), stage);
	}

	default BakedModel getPlantModel(String plantId, int stage) {
		if (plantId.isEmpty()) {
			// somehow there is no plant, display nothing
			return null;
		} else {
			// compute the block model from the plant id and growth stage
			// will look like <namespace>:crop/<id>_stage<growth_stage> so the file is assets/<namespace>/models/crop/<id>_stage<growth_stage>.json
			String plant = plantId.replace(":", ":crop/") + "_stage" + stage;
			BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse(plant)));
			if (model.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
				// model not found, default to the unknown crop model that should always be present
				return Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse(UNKNOWN_PLANT)));
			}
			return model;
		}
	}

	default BakedModel getWeedModel(String weedId, int stage) {
		if (weedId.isEmpty()) {
			// somehow there is no plant, display nothing
			return null;
		} else {
			// compute the block model from the plant id and growth stage
			// will look like <namespace>:weed/<id>_stage<growth_stage> so the file is assets/<namespace>/models/weed/<id>_stage<growth_stage>.json
			String plant = weedId.replace(":", ":weed/") + "_stage" + stage;
			BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse(plant)));
			if (model.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
				// model not found, default to the unknown crop model that should always be present
				return Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse(UNKNOWN_PLANT)));
			}
			return model;
		}
	}

	default BakedModel getSeedModel(String plantId) {
		if (plantId.isEmpty()) {
			plantId = UNKNOWN_SEED;
		}
		// compute the model of the seed from the plant id. the seed model path will look like <namespace>:seed/<id> so the file is /assets/<namespace>/models/seed/<id>.json
		plantId = plantId.replace(":", ":seed/");

		BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.parse(plantId)));
		if (model.equals(Minecraft.getInstance().getModelManager().getMissingModel())) {
			// model not found, defaults to the missing model
			model = Minecraft.getInstance().getModelManager().getMissingModel();
		}
		return model;
	}

	@ApiStatus.Internal
	final class InstanceHolder {

		private static AgriClientApi instance = null;

		private InstanceHolder() {}

	}

}
