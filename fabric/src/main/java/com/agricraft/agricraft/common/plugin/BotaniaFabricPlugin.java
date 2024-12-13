package com.agricraft.agricraft.common.plugin;

import com.agricraft.agricraft.common.block.entity.CropBlockEntity;
import com.agricraft.agricraft.common.registry.ModBlockEntityTypes;
import com.agricraft.agricraft.compat.botania.AgriHornHarvestable;
import com.agricraft.agricraft.compat.botania.BotaniaPlugin;
import vazkii.botania.api.BotaniaFabricCapabilities;

public class BotaniaFabricPlugin {

    public static void init() {
        BotaniaPlugin.init();
        BotaniaFabricCapabilities.HORN_HARVEST.registerForBlockEntities((blockEntity, context) -> {
            if (blockEntity instanceof CropBlockEntity) {
                return AgriHornHarvestable.INSTANCE;
            }
            return null;
        }, ModBlockEntityTypes.CROP.get());
    }

    public String modid() {
        return "botania";
    }

    public String description() {
        return "botania compatibility";
    }
}
