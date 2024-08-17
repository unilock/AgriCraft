package com.agricraft.agricraft.datagen;

import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.stream.Stream;

public class LangDatagen {

	public static void biomesoplenty(LanguageProvider lang) {
		lang.add("plant.agricraft.biomesoplenty.burning_blossom", "Burning Blossom");
		lang.add("plant.agricraft.biomesoplenty.glowflower", "Glowflower");
		lang.add("plant.agricraft.biomesoplenty.glowshroom", "Glowshroom");
		lang.add("plant.agricraft.biomesoplenty.lavender", "Lavender");
		lang.add("plant.agricraft.biomesoplenty.orange_cosmos", "Orange Cosmos");
		lang.add("plant.agricraft.biomesoplenty.pink_daffodil", "Pink Daffodil");
		lang.add("plant.agricraft.biomesoplenty.pink_hibiscus", "Pink Hibiscus");
		lang.add("plant.agricraft.biomesoplenty.rose", "Rose");
		lang.add("plant.agricraft.biomesoplenty.toadstool", "Toadstool");
		lang.add("plant.agricraft.biomesoplenty.violet", "Violet");
		lang.add("plant.agricraft.biomesoplenty.white_lavender", "White Lavender");
		lang.add("plant.agricraft.biomesoplenty.wilted_lily", "Wilted Lily");
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
					lang.add("plant.agricraft.croptopia." + name, "block.croptopia." + name + "_crop");
					lang.add("seed.agricraft.croptopia." + name, "item.croptopia." + name + "_seed");
				});
		lang.add("seed.agricraft.croptopia.vanilla", "item.croptopia.vanilla_seeds");  // somehow vanilla has an 's' to its seed
	}

	public static void farmersdelight(LanguageProvider lang) {
		lang.add("plant.agricraft.farmersdelight.cabbage", "Cabbage");
		lang.add("plant.agricraft.farmersdelight.onion", "Onions");
		lang.add("plant.agricraft.farmersdelight.tomato", "Tomato Vine");
		lang.add("plant.agricraft.farmersdelight.rice", "Rice Crops");

		lang.add("seed.agricraft.farmersdelight.cabbage", "Cabbage Seeds");
		lang.add("seed.agricraft.farmersdelight.onion", "Onion");
		lang.add("seed.agricraft.farmersdelight.tomato", "Tomato Seeds");
		lang.add("seed.agricraft.farmersdelight.rice", "Rice");

		lang.add("description.agricraft.farmersdelight.cabbage", "Full of vitamin K.");
		lang.add("description.agricraft.farmersdelight.onion", "Goes well with pickles.");
		lang.add("description.agricraft.farmersdelight.tomato", "Flood the fields.");
		lang.add("description.agricraft.farmersdelight.rice", "A red and plump fruit.");

		lang.add("soil.agricraft.farmersdelight.rich_farmland", "Rich farmland");
		lang.add("soil.agricraft.farmersdelight.rich_soil", "Rich soil");
	}

	public static void immersiveengineering(LanguageProvider lang) {
		lang.add("plant.agricraft.immersiveengineering.hemp", "Industrial Hemp");
		lang.add("seed.agricraft.immersiveengineering.hemp", "Industrial Hemp Seeds");
		lang.add("description.agricraft.immersiveengineering.hemp", "Testing 1, 2, 3.");
	}

	public static void pamhc2crops(LanguageProvider lang) {
		lang.add("plant.agricraft.pamhc2crops.agave", "Agave");
		lang.add("plant.agricraft.pamhc2crops.amaranth", "Amaranth");
		lang.add("plant.agricraft.pamhc2crops.arrowroot", "Arrowroot");
		lang.add("plant.agricraft.pamhc2crops.artichoke", "Artichoke");
		lang.add("plant.agricraft.pamhc2crops.asparagus", "Asparagus");
		lang.add("plant.agricraft.pamhc2crops.barley", "Barley");
		lang.add("plant.agricraft.pamhc2crops.bean", "Bean");
		lang.add("plant.agricraft.pamhc2crops.bellpepper", "Bellpepper");
		lang.add("plant.agricraft.pamhc2crops.blackberry", "Blackberry");
		lang.add("plant.agricraft.pamhc2crops.blueberry", "Blueberry");
		lang.add("plant.agricraft.pamhc2crops.broccoli", "Broccoli");
		lang.add("plant.agricraft.pamhc2crops.brusselsprout", "Brusselsprout");
		lang.add("plant.agricraft.pamhc2crops.cabbage", "Cabbage");
		lang.add("plant.agricraft.pamhc2crops.cactusfruit", "Cactusfruit");
		lang.add("plant.agricraft.pamhc2crops.candleberry", "Candleberry");
		lang.add("plant.agricraft.pamhc2crops.cantaloupe", "Cantaloupe");
		lang.add("plant.agricraft.pamhc2crops.cassava", "Cassava");
		lang.add("plant.agricraft.pamhc2crops.cauliflower", "Cauliflower");
		lang.add("plant.agricraft.pamhc2crops.celery", "Celery");
		lang.add("plant.agricraft.pamhc2crops.chickpea", "Chickpea");
		lang.add("plant.agricraft.pamhc2crops.chilipepper", "Chilipepper");
		lang.add("plant.agricraft.pamhc2crops.coffeebean", "Coffeebean");
		lang.add("plant.agricraft.pamhc2crops.corn", "Corn");
		lang.add("plant.agricraft.pamhc2crops.cotton", "Cotton");
		lang.add("plant.agricraft.pamhc2crops.cranberry", "Cranberry");
		lang.add("plant.agricraft.pamhc2crops.cucumber", "Cucumber");
		lang.add("plant.agricraft.pamhc2crops.eggplant", "Eggplant");
		lang.add("plant.agricraft.pamhc2crops.elderberry", "Elderberry");
		lang.add("plant.agricraft.pamhc2crops.flax", "Flax");
		lang.add("plant.agricraft.pamhc2crops.garlic", "Garlic");
		lang.add("plant.agricraft.pamhc2crops.ginger", "Ginger");
		lang.add("plant.agricraft.pamhc2crops.grape", "Grape");
		lang.add("plant.agricraft.pamhc2crops.greengrape", "Greengrape");
		lang.add("plant.agricraft.pamhc2crops.huckleberry", "Huckleberry");
		lang.add("plant.agricraft.pamhc2crops.jicama", "Jicama");
		lang.add("plant.agricraft.pamhc2crops.juniperberry", "Juniperberry");
		lang.add("plant.agricraft.pamhc2crops.jute", "Jute");
		lang.add("plant.agricraft.pamhc2crops.kale", "Kale");
		lang.add("plant.agricraft.pamhc2crops.kenaf", "Kenaf");
		lang.add("plant.agricraft.pamhc2crops.kiwi", "Kiwi");
		lang.add("plant.agricraft.pamhc2crops.kohlrabi", "Kohlrabi");
		lang.add("plant.agricraft.pamhc2crops.leek", "Leek");
		lang.add("plant.agricraft.pamhc2crops.lentil", "Lentil");
		lang.add("plant.agricraft.pamhc2crops.lettuce", "Lettuce");
		lang.add("plant.agricraft.pamhc2crops.millet", "Millet");
		lang.add("plant.agricraft.pamhc2crops.mulberry", "Mulberry");
		lang.add("plant.agricraft.pamhc2crops.mustardseeds", "Mustard Seeds");
		lang.add("plant.agricraft.pamhc2crops.oats", "Oats");
		lang.add("plant.agricraft.pamhc2crops.okra", "Okra");
		lang.add("plant.agricraft.pamhc2crops.onion", "Onion");
		lang.add("plant.agricraft.pamhc2crops.parsnip", "Parsnip");
		lang.add("plant.agricraft.pamhc2crops.peanut", "Peanut");
		lang.add("plant.agricraft.pamhc2crops.peas", "Peas");
		lang.add("plant.agricraft.pamhc2crops.pineapple", "Pineapple");
		lang.add("plant.agricraft.pamhc2crops.quinoa", "Quinoa");
		lang.add("plant.agricraft.pamhc2crops.radish", "Radish");
		lang.add("plant.agricraft.pamhc2crops.raspberry", "Raspberry");
		lang.add("plant.agricraft.pamhc2crops.rhubarb", "Rhubarb");
		lang.add("plant.agricraft.pamhc2crops.rice", "Rice");
		lang.add("plant.agricraft.pamhc2crops.rutabaga", "Rutabaga");
		lang.add("plant.agricraft.pamhc2crops.rye", "Rye");
		lang.add("plant.agricraft.pamhc2crops.scallion", "Scallion");
		lang.add("plant.agricraft.pamhc2crops.sesameseeds", "Sesame Seeds");
		lang.add("plant.agricraft.pamhc2crops.sisal", "Sisal");
		lang.add("plant.agricraft.pamhc2crops.soybean", "Soybean");
		lang.add("plant.agricraft.pamhc2crops.spiceleaf", "Spiceleaf");
		lang.add("plant.agricraft.pamhc2crops.spinach", "Spinach");
		lang.add("plant.agricraft.pamhc2crops.strawberry", "Strawberry");
		lang.add("plant.agricraft.pamhc2crops.sweetpotato", "Sweet Potato");
		lang.add("plant.agricraft.pamhc2crops.taro", "Taro");
		lang.add("plant.agricraft.pamhc2crops.tealeaf", "Tealeaf");
		lang.add("plant.agricraft.pamhc2crops.tomatillo", "Tomatillo");
		lang.add("plant.agricraft.pamhc2crops.tomato", "Tomato");
		lang.add("plant.agricraft.pamhc2crops.turnip", "Turnip");
		lang.add("plant.agricraft.pamhc2crops.waterchestnut", "Waterchestnut");
		lang.add("plant.agricraft.pamhc2crops.whitemushroom", "White Mushroom");
		lang.add("plant.agricraft.pamhc2crops.wintersquash", "Winter Squash");
		lang.add("plant.agricraft.pamhc2crops.zucchini", "Zucchini");
		lang.add("plant.agricraft.pamhc2crops.alfalfa", "Alfalfa");
		lang.add("plant.agricraft.pamhc2crops.aloe", "Aloe");
		lang.add("plant.agricraft.pamhc2crops.barrelcactus", "Barrel Cactus");
		lang.add("plant.agricraft.pamhc2crops.canola", "Canola");
		lang.add("plant.agricraft.pamhc2crops.cattail", "Cattail");
		lang.add("plant.agricraft.pamhc2crops.chia", "Chia");
		lang.add("plant.agricraft.pamhc2crops.cloudberry", "Cloudberry");
		lang.add("plant.agricraft.pamhc2crops.lotus", "Lotus");
		lang.add("plant.agricraft.pamhc2crops.nettles", "Nettles");
		lang.add("plant.agricraft.pamhc2crops.nopales", "Nopales");
		lang.add("plant.agricraft.pamhc2crops.sorghum", "Sorghum");
		lang.add("plant.agricraft.pamhc2crops.truffle", "Truffle");
		lang.add("plant.agricraft.pamhc2crops.wolfberry", "Wolfberry");
		lang.add("plant.agricraft.pamhc2crops.yucca", "Yucca");
		lang.add("plant.agricraft.pamhc2crops.bokchoy", "Bok Choy");
		lang.add("plant.agricraft.pamhc2crops.calabash", "Calabash");
		lang.add("plant.agricraft.pamhc2crops.guarana", "Guarana");
		lang.add("plant.agricraft.pamhc2crops.papyrus", "Papyrus");
		lang.add("plant.agricraft.pamhc2crops.sunchoke", "Sunchoke");

		lang.add("seed.agricraft.pamhc2crops.agave", "Agave Seeds");
		lang.add("seed.agricraft.pamhc2crops.amaranth", "Amaranth Seeds");
		lang.add("seed.agricraft.pamhc2crops.arrowroot", "Arrowroot Seeds");
		lang.add("seed.agricraft.pamhc2crops.artichoke", "Artichoke Seeds");
		lang.add("seed.agricraft.pamhc2crops.asparagus", "Asparagus Seeds");
		lang.add("seed.agricraft.pamhc2crops.barley", "Barley Seeds");
		lang.add("seed.agricraft.pamhc2crops.bean", "Bean Seeds");
		lang.add("seed.agricraft.pamhc2crops.bellpepper", "Bellpepper Seeds");
		lang.add("seed.agricraft.pamhc2crops.blackberry", "Blackberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.blueberry", "Blueberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.broccoli", "Broccoli Seeds");
		lang.add("seed.agricraft.pamhc2crops.brusselsprout", "Brusselsprout Seeds");
		lang.add("seed.agricraft.pamhc2crops.cabbage", "Cabbage Seeds");
		lang.add("seed.agricraft.pamhc2crops.cactusfruit", "Cactusfruit Seeds");
		lang.add("seed.agricraft.pamhc2crops.candleberry", "Candleberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.cantaloupe", "Cantaloupe Seeds");
		lang.add("seed.agricraft.pamhc2crops.cassava", "Cassava Seeds");
		lang.add("seed.agricraft.pamhc2crops.cauliflower", "Cauliflower Seeds");
		lang.add("seed.agricraft.pamhc2crops.celery", "Celery Seeds");
		lang.add("seed.agricraft.pamhc2crops.chickpea", "Chickpea Seeds");
		lang.add("seed.agricraft.pamhc2crops.chilipepper", "Chilipepper Seeds");
		lang.add("seed.agricraft.pamhc2crops.coffeebean", "Coffeebean Seeds");
		lang.add("seed.agricraft.pamhc2crops.corn", "Corn Seeds");
		lang.add("seed.agricraft.pamhc2crops.cotton", "Cotton Seeds");
		lang.add("seed.agricraft.pamhc2crops.cranberry", "Cranberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.cucumber", "Cucumber Seeds");
		lang.add("seed.agricraft.pamhc2crops.eggplant", "Eggplant Seeds");
		lang.add("seed.agricraft.pamhc2crops.elderberry", "Elderberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.flax", "Flax Seeds");
		lang.add("seed.agricraft.pamhc2crops.garlic", "Garlic Seeds");
		lang.add("seed.agricraft.pamhc2crops.ginger", "Ginger Seeds");
		lang.add("seed.agricraft.pamhc2crops.grape", "Grape Seeds");
		lang.add("seed.agricraft.pamhc2crops.greengrape", "Greengrape Seeds");
		lang.add("seed.agricraft.pamhc2crops.huckleberry", "Huckleberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.jicama", "Jicama Seeds");
		lang.add("seed.agricraft.pamhc2crops.juniperberry", "Juniperberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.jute", "Jute Seeds");
		lang.add("seed.agricraft.pamhc2crops.kale", "Kale Seeds");
		lang.add("seed.agricraft.pamhc2crops.kenaf", "Kenaf Seeds");
		lang.add("seed.agricraft.pamhc2crops.kiwi", "Kiwi Seeds");
		lang.add("seed.agricraft.pamhc2crops.kohlrabi", "Kohlrabi Seeds");
		lang.add("seed.agricraft.pamhc2crops.leek", "Leek Seeds");
		lang.add("seed.agricraft.pamhc2crops.lentil", "Lentil Seeds");
		lang.add("seed.agricraft.pamhc2crops.lettuce", "Lettuce Seeds");
		lang.add("seed.agricraft.pamhc2crops.millet", "Millet Seeds");
		lang.add("seed.agricraft.pamhc2crops.mulberry", "Mulberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.mustardseeds", "Mustard Seeds Seeds");
		lang.add("seed.agricraft.pamhc2crops.oats", "Oats Seeds");
		lang.add("seed.agricraft.pamhc2crops.okra", "Okra Seeds");
		lang.add("seed.agricraft.pamhc2crops.onion", "Onion Seeds");
		lang.add("seed.agricraft.pamhc2crops.parsnip", "Parsnip Seeds");
		lang.add("seed.agricraft.pamhc2crops.peanut", "Peanut Seeds");
		lang.add("seed.agricraft.pamhc2crops.peas", "Peas Seeds");
		lang.add("seed.agricraft.pamhc2crops.pineapple", "Pineapple Seeds");
		lang.add("seed.agricraft.pamhc2crops.quinoa", "Quinoa Seeds");
		lang.add("seed.agricraft.pamhc2crops.radish", "Radish Seeds");
		lang.add("seed.agricraft.pamhc2crops.raspberry", "Raspberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.rhubarb", "Rhubarb Seeds");
		lang.add("seed.agricraft.pamhc2crops.rice", "Rice Seeds");
		lang.add("seed.agricraft.pamhc2crops.rutabaga", "Rutabaga Seeds");
		lang.add("seed.agricraft.pamhc2crops.rye", "Rye Seeds");
		lang.add("seed.agricraft.pamhc2crops.scallion", "Scallion Seeds");
		lang.add("seed.agricraft.pamhc2crops.sesameseeds", "Sesame Seeds Seeds");
		lang.add("seed.agricraft.pamhc2crops.sisal", "Sisal Seeds");
		lang.add("seed.agricraft.pamhc2crops.soybean", "Soybean Seeds");
		lang.add("seed.agricraft.pamhc2crops.spiceleaf", "Spiceleaf Seeds");
		lang.add("seed.agricraft.pamhc2crops.spinach", "Spinach Seeds");
		lang.add("seed.agricraft.pamhc2crops.strawberry", "Strawberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.sweetpotato", "Sweet Potato Seeds");
		lang.add("seed.agricraft.pamhc2crops.taro", "Taro Seeds");
		lang.add("seed.agricraft.pamhc2crops.tealeaf", "Tealeaf Seeds");
		lang.add("seed.agricraft.pamhc2crops.tomatillo", "Tomatillo Seeds");
		lang.add("seed.agricraft.pamhc2crops.tomato", "Tomato Seeds");
		lang.add("seed.agricraft.pamhc2crops.turnip", "Turnip Seeds");
		lang.add("seed.agricraft.pamhc2crops.waterchestnut", "Waterchestnut Seeds");
		lang.add("seed.agricraft.pamhc2crops.whitemushroom", "White Mushroom Seeds");
		lang.add("seed.agricraft.pamhc2crops.wintersquash", "Winter Squash Seeds");
		lang.add("seed.agricraft.pamhc2crops.zucchini", "Zucchini Seeds");
		lang.add("seed.agricraft.pamhc2crops.alfalfa", "Alfalfa Seeds");
		lang.add("seed.agricraft.pamhc2crops.aloe", "Aloe Seeds");
		lang.add("seed.agricraft.pamhc2crops.barrelcactus", "Barrel Cactus Seeds");
		lang.add("seed.agricraft.pamhc2crops.canola", "Canola Seeds");
		lang.add("seed.agricraft.pamhc2crops.cattail", "Cattail Seeds");
		lang.add("seed.agricraft.pamhc2crops.chia", "Chia Seeds");
		lang.add("seed.agricraft.pamhc2crops.cloudberry", "Cloudberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.lotus", "Lotus Seeds");
		lang.add("seed.agricraft.pamhc2crops.nettles", "Nettles Seeds");
		lang.add("seed.agricraft.pamhc2crops.nopales", "Nopales Seeds");
		lang.add("seed.agricraft.pamhc2crops.sorghum", "Sorghum Seeds");
		lang.add("seed.agricraft.pamhc2crops.truffle", "Truffle Seeds");
		lang.add("seed.agricraft.pamhc2crops.wolfberry", "Wolfberry Seeds");
		lang.add("seed.agricraft.pamhc2crops.yucca", "Yucca Seeds");
		lang.add("seed.agricraft.pamhc2crops.bokchoy", "Bok Choy Seeds");
		lang.add("seed.agricraft.pamhc2crops.calabash", "Calabash Seeds");
		lang.add("seed.agricraft.pamhc2crops.guarana", "Guarana Seeds");
		lang.add("seed.agricraft.pamhc2crops.papyrus", "Papyrus Seeds");
		lang.add("seed.agricraft.pamhc2crops.sunchoke", "Sunchoke Seeds");
	}

}
