package com.anatevka.pandemonium.block.custom;

import com.anatevka.pandemonium.block.entity.CopperPedestalBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CopperVillagerStatue extends HorizontalDirectionalBlock implements WeatheringCopper {
    public static final MapCodec<CopperVillagerStatue> CODEC = RecordCodecBuilder.mapCodec(
            (properties) -> properties.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopper::getAge), propertiesCodec())
                    .apply(properties, CopperVillagerStatue::new)
    );
    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);
    private final WeatherState weatherState;

    public CopperVillagerStatue(WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }
    @Override
    protected MapCodec<? extends CopperVillagerStatue> codec() {
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }
}

















