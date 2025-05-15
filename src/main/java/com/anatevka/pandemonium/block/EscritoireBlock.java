package com.anatevka.pandemonium.block;

import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.RawAnimation;

public class EscritoireBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final int INPUT_SLOT_INDEX = 0;
    public static final int SLOT_COUNT = 1;

    public static final MapCodec<EscritoireBlock> CODEC = simpleCodec(EscritoireBlock::new);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 10.0, 15.0);
    public static final RawAnimation ESCRITOIRE_OPEN = RawAnimation.begin().thenPlay("escritoire.animation.open");
    public static final RawAnimation ESCRITOIRE_CLOSE = RawAnimation.begin().thenPlay("escritoire.animation.close");

    public EscritoireBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {return SHAPE;}
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));}
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {return state.rotate(mirror.getRotation(state.getValue(FACING)));}
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {builder.add(FACING, OPEN);}
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    /* Block Entity */

    @Override
    public RenderShape getRenderShape(BlockState state) {return RenderShape.INVISIBLE;}
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EscritoireBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : ((level1, pos, state, blockEntity) -> tick(level, pos));
    }

    protected void tick(Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof EscritoireBlockEntity escritoireBlockEntity) {
            escritoireBlockEntity.eatItems();
            escritoireBlockEntity.animateState(pos, level);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof EscritoireBlockEntity escritoireBlockEntity) {
                escritoireBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (level.getBlockEntity(pos) instanceof EscritoireBlockEntity escritoireBlockEntity)
        {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(escritoireBlockEntity, pos);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }
}

