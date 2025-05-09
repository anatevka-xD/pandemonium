package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SoulCandleBlockEntity extends BlockEntity {
    public SoulCandleBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.SOUL_CANDLE_BE.get(), pos, blockState);
    }
}
