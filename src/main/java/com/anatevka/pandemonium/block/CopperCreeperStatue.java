package com.anatevka.pandemonium.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CopperCreeperStatue extends CopperStatue {
    public static final MapCodec<CopperCreeperStatue> CODEC = RecordCodecBuilder.mapCodec(
            (properties) -> properties.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopper::getAge), propertiesCodec())
                    .apply(properties, CopperCreeperStatue::new)
    );
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

    public CopperCreeperStatue(WeatherState weatherState, BlockBehaviour.Properties properties) {
        super(weatherState, properties);
    }
    @Override
    protected MapCodec<? extends CopperCreeperStatue> codec() {
        return CODEC;
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

















