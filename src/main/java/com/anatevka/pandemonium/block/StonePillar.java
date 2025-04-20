package com.anatevka.pandemonium.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class StonePillar extends DirectionalBlock {
    public static final MapCodec<StonePillar> CODEC = simpleCodec(StonePillar::new);
    public StonePillar(Properties properties) {
        super(properties);
    }
    @Override
    protected MapCodec<? extends DirectionalBlock> codec(){
        return CODEC;
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace()));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
