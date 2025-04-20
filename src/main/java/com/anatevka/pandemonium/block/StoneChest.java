package com.anatevka.pandemonium.block;

import com.anatevka.pandemonium.block.entity.StoneChestBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.RawAnimation;

public class StoneChest extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<StoneChest> CODEC = simpleCodec(StoneChest::new);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public StoneChest(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() { return CODEC; }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_49069_, Level p_49070_, BlockPos p_49071_, Player p_49072_, BlockHitResult p_49074_) {
        if (p_49070_.getBlockEntity(p_49071_) instanceof StoneChestBlockEntity stoneChestBlockEntity) {
            p_49072_.openMenu(stoneChestBlockEntity);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected void tick(BlockState p_220758_, ServerLevel p_220759_, BlockPos p_220760_, RandomSource p_220761_) {
        BlockEntity blockentity = p_220759_.getBlockEntity(p_220760_);
        if (blockentity instanceof StoneChestBlockEntity) {
            ((StoneChestBlockEntity)blockentity).recheckOpen();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new StoneChestBlockEntity(pos, blockState);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    public static final RawAnimation OPEN_CHEST = RawAnimation.begin().thenPlay("chest_open.new");
    public static final RawAnimation CLOSE_CHEST = RawAnimation.begin().thenPlay("chest_close.new");
}
