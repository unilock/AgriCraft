package com.agricraft.agricraft.common.plugin.industrialforegoing;

import com.agricraft.agricraft.api.AgriApi;
import com.buuz135.industrial.registry.IFRegistries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = AgriApi.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IndustrialForegoingPlugin {
    @SubscribeEvent
    public static void registerAgriCraftPlantRecollectable(RegisterEvent event) {
        if (ModList.get().isLoaded("industrialforegoing")) {
            event.register(IFRegistries.PLANT_RECOLLECTABLES_REGISTRY_KEY,
                    helper -> helper.register("agricraft", new AgriCraftPlantRecollectable())
            );
        }
    }
}
