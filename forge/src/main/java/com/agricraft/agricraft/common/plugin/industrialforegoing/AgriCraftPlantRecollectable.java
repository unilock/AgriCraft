package com.agricraft.agricraft.common.plugin.industrialforegoing;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.crop.AgriCrop;
import com.buuz135.industrial.api.plant.PlantRecollectable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AgriCraftPlantRecollectable extends PlantRecollectable {

    public AgriCraftPlantRecollectable() {
        super("agricraft");
    }

    @Override
    public boolean canBeHarvested(Level level, BlockPos blockPos, BlockState blockState) {
        return AgriApi.getCrop(level, blockPos).map(AgriCrop::canBeHarvested).orElse(false);
    }

    @Override
    public List<ItemStack> doHarvestOperation(Level level, BlockPos blockPos, BlockState blockState) {
        List<ItemStack> drops = new ArrayList<>();
        AgriApi.getCrop(level, blockPos).ifPresent(crop -> crop.harvest(drops::add, null));
        return drops;
    }

    @Override
    public boolean shouldCheckNextPlant(Level level, BlockPos blockPos, BlockState blockState) {
        return true;
    }
}
