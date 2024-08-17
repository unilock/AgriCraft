package com.agricraft.agricraft.datagen;

import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.stream.Stream;

public class LangDatagen {

	public static void biomesoplenty(LanguageProvider lang) {
		Stream.of("burning_blossom", "glowflower", "glowshroom", "lavender", "orange_cosmos", "pink_daffodil", "pink_hibiscus", "rose", "toadstool", "violet", "white_lavender", "wilted_lily").forEach(name -> lang.add("plant.agricraft.biomesoplenty." + name, "block.biomesoplenty." + name));
		lang.add("seed.agricraft.biomesoplenty.burning_blossom", "Burning Blossom Seeds");
		lang.add("seed.agricraft.biomesoplenty.glowflower", "Glowflower Seeds");
		lang.add("seed.agricraft.biomesoplenty.glowshroom", "Glowshroom Spores");
		lang.add("seed.agricraft.biomesoplenty.lavender", "Lavender Seeds");
		lang.add("seed.agricraft.biomesoplenty.orange_cosmos", "Orange Cosmos Seeds");
		lang.add("seed.agricraft.biomesoplenty.pink_daffodil", "Pink Daffodil Seeds");
		lang.add("seed.agricraft.biomesoplenty.pink_hibiscus", "Pink Hibiscus Seeds");
		lang.add("seed.agricraft.biomesoplenty.rose", "Rose Seeds");
		lang.add("seed.agricraft.biomesoplenty.toadstool", "Toadstool Spores");
		lang.add("seed.agricraft.biomesoplenty.violet", "Violet Seeds");
		lang.add("seed.agricraft.biomesoplenty.white_lavender", "White Lavender Seeds");
		lang.add("seed.agricraft.biomesoplenty.wilted_lily", "Wilted Lily Seeds");
		lang.add("description.agricraft.biomesoplenty.burning_blossom", "Watch out for burns.");
		lang.add("description.agricraft.biomesoplenty.glowflower", "A flower glowing in the dark.");
		lang.add("description.agricraft.biomesoplenty.glowshroom", "A mushroom glowing in the dark.");
		lang.add("description.agricraft.biomesoplenty.lavender", "A flower with multiple purple blossoms and a refreshing scent.");
		lang.add("description.agricraft.biomesoplenty.orange_cosmos", "Doesn't it look like the sun?");
		lang.add("description.agricraft.biomesoplenty.pink_daffodil", "This flower is generally found near ponds or lakes.");
		lang.add("description.agricraft.biomesoplenty.pink_hibiscus", "Its gigantic blooms are loved by bees, butterflies and hummingbirds.");
		lang.add("description.agricraft.biomesoplenty.rose", "One of the most popular and widely cultivated groups of flowering plants.");
		lang.add("description.agricraft.biomesoplenty.toadstool", "A type of edible mushroom found in the forest.");
		lang.add("description.agricraft.biomesoplenty.violet", "A delicate flower growing in meadows.");
		lang.add("description.agricraft.biomesoplenty.white_lavender", "A delicate lavender");
		lang.add("description.agricraft.biomesoplenty.wilted_lily", "That's what happens when you don't water your plants.");
	}

	public static void croptopia(LanguageProvider lang) {
		Stream.of("artichoke", "asparagus", "barley", "basil", "bellpepper", "blackbean", "blackberry",
						"blueberry", "broccoli", "cabbage", "cantaloupe", "cauliflower", "celery", "chile_pepper",
						"coffee", "corn", "cranberry", "cucumber", "currant", "eggplant", "elderberry", "garlic",
						"ginger", "grape", "greenbean", "greenonion", "honeydew", "hops", "kale", "kiwi", "leek",
						"lettuce", "mustard", "oat", "olive", "onion", "peanut", "pepper", "pineapple", "radish",
						"raspberry", "rhubarb", "rice", "rutabaga", "saguaro", "soybean", "spinach", "squash",
						"strawberry", "sweetpotato", "tea", "tomatillo", "tomato", "turmeric", "turnip", "yam",
						"zucchini")
				.forEach(name -> {
					lang.add("plant.agricraft.croptopia." + name, "item.croptopia." + name);
					lang.add("seed.agricraft.croptopia." + name, "item.croptopia." + name + "_seed");
				});
		lang.add("plant.agricraft.croptopia.vanilla", "item.croptopia.vanilla");
		lang.add("seed.agricraft.croptopia.vanilla", "item.croptopia.vanilla_seeds");  // somehow vanilla has an 's' to its seed
	}

	public static void farmersdelight(LanguageProvider lang) {
		lang.add("plant.agricraft.farmersdelight.cabbage", "item.farmersdelight.cabbage");
		lang.add("plant.agricraft.farmersdelight.onion", "item.farmersdelight.onion");
		lang.add("plant.agricraft.farmersdelight.tomato", "item.farmersdelight.tomato");
		lang.add("plant.agricraft.farmersdelight.rice", "item.farmersdelight.rice");

		lang.add("seed.agricraft.farmersdelight.cabbage", "item.farmersdelight.cabbage_seeds");
		lang.add("seed.agricraft.farmersdelight.onion", "item.farmersdelight.onion");
		lang.add("seed.agricraft.farmersdelight.tomato", "item.farmersdelight.tomato_seeds");
		lang.add("seed.agricraft.farmersdelight.rice", "item.farmersdelight.rice");

		lang.add("description.agricraft.farmersdelight.cabbage", "Full of vitamin K.");
		lang.add("description.agricraft.farmersdelight.onion", "Goes well with pickles.");
		lang.add("description.agricraft.farmersdelight.tomato", "A red and plump fruit.");
		lang.add("description.agricraft.farmersdelight.rice", "Flood the fields.");

		lang.add("soil.agricraft.farmersdelight.rich_soil_farmland", "block.farmersdelight.rich_soil_farmland");
		lang.add("soil.agricraft.farmersdelight.rich_soil", "block.farmersdelight.rich_soil");
	}

	public static void immersiveengineering(LanguageProvider lang) {
		lang.add("plant.agricraft.immersiveengineering.hemp", "block.immersiveengineering.hemp");
		lang.add("seed.agricraft.immersiveengineering.hemp", "item.immersiveengineering.seed");
		lang.add("description.agricraft.immersiveengineering.hemp", "Testing 1, 2, 3.");
	}

	public static void pamhc2crops(LanguageProvider lang) {
		Stream.of("agave", "alfalfa", "aloe", "amaranth", "arrowroot", "artichoke", "asparagus", "barley",
						"barrelcactus", "bean", "bellpepper", "blackberry", "blueberry", "bokchoy", "broccoli",
						"brusselsprout", "cabbage", "cactusfruit", "calabash", "candleberry", "canola", "cantaloupe",
						"cassava", "cattail", "cauliflower", "celery", "chia", "chickpea", "chilipepper", "cloudberry",
						"coffeebean", "corn", "cotton", "cranberry", "cucumber", "eggplant", "elderberry", "flax",
						"garlic", "ginger", "grape", "greengrape", "guarana", "huckleberry", "jicama", "juniperberry",
						"jute", "kale", "kenaf", "kiwi", "kohlrabi", "leek", "lentil", "lettuce", "lotus", "millet",
						"mulberry", "mustardseeds", "nettles", "nopales", "oats", "okra", "onion", "papyrus", "parsnip",
						"peanut", "peas", "pineapple", "quinoa", "radish", "raspberry", "rhubarb", "rice", "rutabaga",
						"rye", "scallion", "sesameseeds", "sisal", "sorghum", "soybean", "spiceleaf", "spinach",
						"strawberry", "sunchoke", "sweetpotato", "taro", "tealeaf", "tomatillo", "tomato", "truffle",
						"turnip", "waterchestnut", "whitemushroom", "wintersquash", "wolfberry", "yucca", "zucchini")
				.forEach(name -> {
					lang.add("plant.agricraft.pamhc2crops." + name, "item.pamhc2crops." + name + "item");
					lang.add("seed.agricraft.pamhc2crops." + name, "item.pamhc2crops." + name + "seeditem");
				});
	}

}
