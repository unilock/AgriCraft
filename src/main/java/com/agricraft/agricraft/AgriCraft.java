package com.agricraft.agricraft;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.codecs.AgriMutation;
import com.agricraft.agricraft.api.codecs.AgriSoil;
import com.agricraft.agricraft.api.config.AgriCraftConfig;
import com.agricraft.agricraft.api.fertilizer.AgriFertilizer;
import com.agricraft.agricraft.api.genetic.AgriGenes;
import com.agricraft.agricraft.api.plant.AgriPlant;
import com.agricraft.agricraft.api.plant.AgriWeed;
import com.agricraft.agricraft.api.stat.AgriStats;
import com.agricraft.agricraft.common.commands.DumpRegistriesCommand;
import com.agricraft.agricraft.common.commands.GiveSeedCommand;
import com.agricraft.agricraft.common.handler.VanillaSeedConversion;
import com.agricraft.agricraft.common.registry.ModBlockEntityTypes;
import com.agricraft.agricraft.common.registry.ModBlocks;
import com.agricraft.agricraft.common.registry.ModCreativeTabs;
import com.agricraft.agricraft.common.registry.ModDataComponentTypes;
import com.agricraft.agricraft.common.registry.ModItems;
import com.agricraft.agricraft.common.registry.ModMenus;
import com.agricraft.agricraft.common.registry.ModRecipeSerializers;
import com.agricraft.agricraft.plugin.minecraft.MinecraftPlugin;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Mod(AgriApi.MOD_ID)
public class AgriCraft {

	public static final Logger LOGGER = LogUtils.getLogger();

	public AgriCraft(IEventBus bus, ModContainer modContainer) {
		AgriStats.STATS.register(bus);
		AgriGenes.GENES.register(bus);
		ModDataComponentTypes.DATA_COMPONENTS.register(bus);
		ModBlocks.BLOCKS.register(bus);
		ModItems.ITEMS.register(bus);
		ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
		ModCreativeTabs.CREATIVE_MODE_TAB.register(bus);
		ModMenus.MENUS.register(bus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
		LOGGER.info("Intializing API for " + AgriApi.MOD_ID);
		bus.addListener(AgriCraft::onCommonSetup);
		bus.addListener(AgriCraft::onRegisterDatapackRegistry);
		bus.addListener(AgriCraft::onAddPackFinders);
		NeoForge.EVENT_BUS.addListener(AgriCraft::onRegisterCommands);
		NeoForge.EVENT_BUS.addListener(AgriCraft::onRightClick);
		modContainer.registerConfig(ModConfig.Type.COMMON, AgriCraftConfig.SPEC);
	}

	public static void onCommonSetup(FMLCommonSetupEvent event) {
		MinecraftPlugin.init();
//		SereneSeasonPlugin.init();
	}

	public static void onRegisterDatapackRegistry(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(AgriApi.AGRIPLANTS, AgriPlant.CODEC, AgriPlant.CODEC);
		event.dataPackRegistry(AgriApi.AGRIWEEDS, AgriWeed.CODEC, AgriWeed.CODEC);
		event.dataPackRegistry(AgriApi.AGRISOILS, AgriSoil.CODEC, AgriSoil.CODEC);
		event.dataPackRegistry(AgriApi.AGRIMUTATIONS, AgriMutation.CODEC, AgriMutation.CODEC);
		event.dataPackRegistry(AgriApi.AGRIFERTILIZERS, AgriFertilizer.CODEC, AgriFertilizer.CODEC);
	}

	public static void onRegisterCommands(RegisterCommandsEvent event) {
		GiveSeedCommand.register(event.getDispatcher(), event.getBuildContext());
		DumpRegistriesCommand.register(event.getDispatcher(), event.getBuildContext());
	}

	public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		if (VanillaSeedConversion.onRightClick(event.getEntity(), event.getHand(), event.getPos(), event.getHitVec())) {
			event.setUseItem(TriState.FALSE);
			event.setCanceled(true);
		}
	}

	public static void onAddPackFinders(AddPackFindersEvent event) {
		IModFileInfo agricraft = ModList.get().getModFileById(AgriApi.MOD_ID);
		if (event.getPackType() == PackType.SERVER_DATA) {
			for (IModInfo mod : ModList.get().getMods()) {
				addPack("datapacks", mod.getModId(), agricraft, PackType.SERVER_DATA, event);
//				event.addPackFinders(ResourceLocation.parse("agricraft:datapacks/"+mod.getModId()),
//						PackType.SERVER_DATA,
//						Component.translatable("agricraft.datapacks." + mod.getModId()),
//						PackSource.BUILT_IN,
//						false,
//						Pack.Position.TOP
//				);
			}
		}
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			for (IModInfo mod : ModList.get().getMods()) {
				addPack("resourcepacks", mod.getModId(), agricraft, PackType.CLIENT_RESOURCES, event);
//				event.addPackFinders(ResourceLocation.parse("agricraft:resourcepacks/"+mod.getModId()),
//						PackType.CLIENT_RESOURCES,
//						Component.translatable("agricraft.resourcepacks." + mod.getModId()),
//						PackSource.BUILT_IN,
//						false,
//						Pack.Position.TOP
//						);
			}
		}
	}

	public static void addPack(String type, String modid, IModFileInfo agricraft, PackType packType, AddPackFindersEvent event) {
		PackLocationInfo packLocationInfo = new PackLocationInfo("mod/agricraft_" + type + "_" + modid,
				Component.translatable("agricraft." + type + "." + modid),
				PackSource.BUILT_IN,
				Optional.of(new KnownPack("agricraft", "mod/agricraft_" + type + "_" + modid, agricraft.versionString())));
		Path resourcePath = agricraft.getFile().findResource(type, modid);
		Pack.ResourcesSupplier resources = BuiltInPackSource.fromName(path -> new PathPackResources(path, resourcePath));
		try (PackResources packresources = resources.openPrimary(packLocationInfo)) {
			PackMetadataSection packmetadatasection = packresources.getMetadataSection(PackMetadataSection.TYPE);
			if (packmetadatasection == null) {
				return;
			}
		} catch (IOException ignored) {
		}
		Pack pack = Pack.readMetaAndCreate(packLocationInfo, resources, packType, new PackSelectionConfig(false, Pack.Position.TOP, false));
		if (pack != null) {
			event.addRepositorySource(packConsumer -> packConsumer.accept(pack));
		}
	}

}
