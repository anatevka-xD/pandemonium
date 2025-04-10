package com.anatevka.pandemonium.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CopperStatue extends Block implements WeatheringCopper {
    public static final MapCodec<CopperStatue> CODEC = RecordCodecBuilder.mapCodec(
            (properties) -> properties.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopper::getAge), propertiesCodec())
                    .apply(properties, CopperStatue::new)
    );
    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    private static final int ROTATIONS;
    public static final IntegerProperty ROTATION;
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private final WeatherState weatherState;

    public CopperStatue(WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(ROTATION, 0));
    }
    @Override
    protected MapCodec<? extends CopperStatue> codec() {
        return CODEC;
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state,level,pos,random);
    }
    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return this.weatherState != WeatherState.OXIDIZED;
    }
    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }

    /* Rotations */

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)super.getStateForPlacement(context).setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()));
    }
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(ROTATION, rotation.rotate((Integer)state.getValue(ROTATION), ROTATIONS));
    }
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return (BlockState)state.setValue(ROTATION, mirror.mirror((Integer)state.getValue(ROTATION), ROTATIONS));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{ROTATION});
    }
    static {
        ROTATIONS = MAX + 1;
        ROTATION = BlockStateProperties.ROTATION_16;
    }
}

















