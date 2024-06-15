package com.agricraft.agricraft.api.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class AgriCraftConfig {

	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue PLANT_OFF_CROP_STICKS = BUILDER.push("core")
			.comment("Set to false to disable planting of (agricraft) seeds outside crop sticks")
			.define("plant_off_crop_sticks", true);
	public static final ModConfigSpec.BooleanValue CROP_STICKS_COLLIDE = BUILDER
			.comment("Set to false to disable collision boxes on crop sticks")
			.define("crop_sticks_collide", true);
	public static final ModConfigSpec.BooleanValue only_fertile_crops_spread = BUILDER
			.comment("Set to true to allow only fertile plants to be able to cause, participate in, or contribute to a spreading / mutation action (note that this may cause issues with obtaining some specific plants)")
			.define("only_fertile_crops_spread", false);
	public static final ModConfigSpec.BooleanValue allow_fertilizer_mutation = BUILDER
			.comment("Set to false if to disable triggering of mutations by using fertilizers on a cross crop.")
			.define("allow_fertilizer_mutation", true);
	public static final ModConfigSpec.BooleanValue clone_mutations = BUILDER
			.comment("Set to true to allow mutations on clone events (spreading from single crop).")
			.define("clone_mutations", false);
	public static final ModConfigSpec.BooleanValue override_vanilla_farming = BUILDER
			.comment("Set to true to override vanilla farming, meaning vanilla seeds will be converted to agricraft seeds on planting.")
			.define("override_vanilla_farming", true);
	public static final ModConfigSpec.BooleanValue converts_seed_only_in_analyzer = BUILDER
			.comment("Set to true to convert seeds only in the analyzer. Has no effect if \"Override vanilla farming\" is set to false.")
			.define("converts_seed_only_in_analyzer", false);
	public static final ModConfigSpec.DoubleValue growth_multiplier = BUILDER
			.comment("Global growth rate multiplier for crops.")
			.defineInRange("growth_multiplier", 1.0, 0.0, 3.0);
	public static final ModConfigSpec.BooleanValue only_mature_seed_drops = BUILDER
			.comment("Set this to true to make only mature crops drop seeds (to encourage trowel usage).")
			.define("only_mature_seed_drops", false);
	public static final ModConfigSpec.BooleanValue disable_weeds = BUILDER
			.comment("Set to true to completely disable the spawning of weeds")
			.define("disable_weeds", false);
	public static final ModConfigSpec.BooleanValue mature_weeds_kill_plants = BUILDER
			.comment("Set to false to disable mature weeds killing plants")
			.define("mature_weeds_kill_plants", true);
	public static final ModConfigSpec.BooleanValue weeds_spreading = BUILDER
			.comment("Set to false to disable the spreading of weeds")
			.define("weeds_spreading", true);
	public static final ModConfigSpec.BooleanValue weeds_destroy_crop_sticks = BUILDER
			.comment("Set this to true to have weeds destroy the crop sticks when they are broken with weeds (to encourage rake usage).")
			.define("weeds_destroy_crop_sticks", false);
	public static final ModConfigSpec.BooleanValue raking_drops_items = BUILDER
			.comment("Set to false if you wish to disable drops from raking weeds.")
			.define("raking_drops_items", true);
	public static final ModConfigSpec.DoubleValue seed_compost_value = BUILDER
			.comment("Defines the seed compost value, if set to zero, seeds will not be compostable.")
			.defineInRange("seed_compost_value", 0.3, 0.0, 1.0);
//	public static boolean animalAttraction = false;
	public static final ModConfigSpec.IntValue seed_bag_capactity = BUILDER
			.comment("The amount of seeds one seed bag can hold.")
			.defineInRange("seed_bag_capactity", 64, 0, 256);
	public static final ModConfigSpec.IntValue seed_bag_enchant_cost = BUILDER
			.comment("Enchantment cost in player levels to enchant the seed bag.")
			.defineInRange("seed_bag_enchant_cost", 10, 0, 30);
//	public static boolean allowGrassDropResets = false;


	public static final ModConfigSpec.IntValue gain_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Gain stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("gain_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue gain_max = BUILDER
			.comment("Maximum allowed value of the Gain stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("gain_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue gain_hidden = BUILDER
			.comment("Set to true to hide the Gain stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("gain_hidden", false);
	public static final ModConfigSpec.IntValue growth_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Growth stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("growth_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue growth_max = BUILDER
			.comment("Maximum allowed value of the Growth stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("growth_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue growth_hidden = BUILDER
			.comment("Set to true to hide the Growth stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("growth_hidden", false);
	public static final ModConfigSpec.IntValue strength_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Strength stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("strength_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue strength_max = BUILDER
			.comment("Maximum allowed value of the Strength stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("strength_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue strength_hidden = BUILDER
			.comment("Set to true to hide the Strength stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("strength_hidden", false);
	public static final ModConfigSpec.IntValue resistance_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Resistance stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("resistance_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue resistance_max = BUILDER
			.comment("Maximum allowed value of the Resistance stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("resistance_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue resistance_hidden = BUILDER
			.comment("Set to true to hide the Resistance stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("resistance_hidden", false);
	public static final ModConfigSpec.IntValue fertility_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Fertility stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("fertility_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue fertility_max = BUILDER
			.comment("Maximum allowed value of the Fertility stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("fertility_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue fertility_hidden = BUILDER
			.comment("Set to true to hide the Fertility stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("fertility_hidden", false);
	public static final ModConfigSpec.IntValue mutativity_min = BUILDER.pop().push("stats")
			.comment("Minimum allowed value of the Mutativity stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("mutativity_min", 1, 1, 10);
	public static final ModConfigSpec.IntValue mutativity_max = BUILDER
			.comment("Maximum allowed value of the Mutativity stat (setting min and max equal will freeze the stat to that value in crop breeding)")
			.defineInRange("mutativity_max", 10, 1, 10);
	public static final ModConfigSpec.BooleanValue mutativity_hidden = BUILDER
			.comment("Set to true to hide the Mutativity stat (hidden stats will not show up in tooltips or seed analysis). Setting min and max equal and hiding a stat effectively disables it, with its behaviour at the defined value for min and max.")
			.define("mutativity_hidden", false);

}
