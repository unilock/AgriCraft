package com.agricraft.agricraft.datagen;

import com.agricraft.agricraft.api.fertilizer.AgriFertilizer;
import com.agricraft.agricraft.api.fertilizer.AgriFertilizerParticle;
import com.agricraft.agricraft.api.fertilizer.AgriFertilizerVariant;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class FertilizersDatagen {

	private static void mc(BootstrapContext<AgriFertilizer> context, String fertilizerId, AgriFertilizer fertilizer) {
		context.register(
				ResourceKey.create(AgriFertilizer.REGISTRY_KEY, ResourceLocation.withDefaultNamespace(fertilizerId)),
				fertilizer
		);
	}

	public static void registerFertilizers(BootstrapContext<AgriFertilizer> context) {
		mc(context, "bone_meal", new AgriFertilizer.Builder()
				.variants(new AgriFertilizerVariant.Builder().item("minecraft:bone_meal").build())
				.particles(new AgriFertilizerParticle("minecraft:happy_villager", 0.6, 0.4, 0.6, 2, List.of("positive")),
						new AgriFertilizerParticle("minecraft:smoke", 0.6, 0.4, 0.6, 2, List.of("negative")))
				.neutralOn("agricraft:amathyllis", "agricraft:aurigold", "agricraft:carbonation", "agricraft:cuprosia",
						"agricraft:diamahlia", "agricraft:emeryllis", "agricraft:ferranium", "agricraft:jaslumine",
						"agricraft:lapender", "agricraft:nethereed", "agricraft:niccissus", "agricraft:nitor_wart",
						"agricraft:osmonium", "agricraft:petinia", "agricraft:platiolus", "agricraft:plombean",
						"agricraft:quartzanthemum", "agricraft:redstodendron")
				.build());
	}

	public static void registerMysticalAgriculture(BootstrapContext<AgriFertilizer> context) {
		context.register(
				ResourceKey.create(AgriFertilizer.REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath("mysticalagriculture", "mystical_fertilizer")),
				new AgriFertilizer.Builder()
						.variants(new AgriFertilizerVariant.Builder().item("mysticalagriculture:mystical_fertilizer").build())
						.potency(5)
						.triggerMutation(false)
						.triggerWeeds(false)
						.particles(new AgriFertilizerParticle("minecraft:happy_villager", 0.6, 0.4, 0.6, 2, List.of("positive")),
								new AgriFertilizerParticle("minecraft:smoke", 0.6, 0.4, 0.6, 2, List.of("negative")))
						.build()
		);
	}

}
