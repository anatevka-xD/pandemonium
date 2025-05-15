package com.anatevka.pandemonium.block;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class SoulCandleBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<SoulCandleBlock> CODEC = simpleCodec(SoulCandleBlock::new);
    public static final BooleanProperty LIT;
    public static final IntegerProperty CANDLES;
    public static final BooleanProperty WATERLOGGED;
    public static final ToIntFunction<BlockState> LIGHT_EMISSION;
    private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS;
    private static final VoxelShape ONE_AABB;
    private static final VoxelShape TWO_AABB;
    private static final VoxelShape THREE_AABB;
    private static final VoxelShape FOUR_AABB;

    public SoulCandleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(CANDLES, 1)
                        .setValue(LIT, false)
                        .setValue(WATERLOGGED, false));
    }

    // Block shape and state
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, WATERLOGGED);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(CANDLES)) {
            case 1:
            default:
                return ONE_AABB;
            case 2:
                return TWO_AABB;
            case 3:
                return THREE_AABB;
            case 4:
                return FOUR_AABB;
        }
    }

    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(CANDLES) < 4 ? true : super.canBeReplaced(state, context);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        context.getLevel().scheduleTick(context.getClickedPos(), this, 4);

        // State for placement
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.cycle(CANDLES);
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
        }

    }

    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            BlockState blockstate = state.setValue(WATERLOGGED, true);
            level.setBlock(pos, blockstate, 3);

            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            return true;
        } else {
            return false;
        }
    }


    // Tick logic
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Light/extinguish candle if there's a player nearby
        Player player = level.getNearestPlayer(
                (double)pos.getX() + 0.5,
                (double)pos.getY() + 0.5,
                (double)pos.getZ() + 0.5,
                4.0 * state.getValue(CANDLES), false);
        if (state.getValue(LIT) && player == null) {
            if (!level.getPlayers(LivingEntity::isAlive).isEmpty()) {
                // Extinguish
                level.setBlock(pos, state.setValue(LIT, false), 3);
                level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else if (!state.getValue(LIT) && player != null) {
            // Light
            level.setBlock(pos, state.setValue(LIT, true), 3);

        }
        level.scheduleTick(pos, this, 4);
    }

    // Particles and sounds
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            this.getParticleOffsets(state).forEach((offset) -> addParticlesAndSound(level, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
        }

    }

    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS.get(state.getValue(CANDLES));
    }

    private static void addParticlesAndSound(Level level, Vec3 offset, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, offset.x, offset.y, offset.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                level.playLocalSound(offset.x + 0.5, offset.y + 0.5, offset.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, offset.x, offset.y, offset.z, 0.0, 0.0, 0.0);
    }

    // Definitions
    static {
        CANDLES = BlockStateProperties.CANDLES;
        LIT = BlockStateProperties.LIT;
        LIGHT_EMISSION = (properties) -> properties.getValue(LIT) ? 3 * properties.getValue(CANDLES) : 0;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        PARTICLE_OFFSETS = Util.make(() -> {
            Int2ObjectMap<List<Vec3>> int2objectmap = new Int2ObjectOpenHashMap<>();
            int2objectmap.defaultReturnValue(ImmutableList.of());
            int2objectmap.put(1, ImmutableList.of(new Vec3(0.5, 0.60, 0.5)));
            int2objectmap.put(2, ImmutableList.of(new Vec3(0.41, 0.60, 0.47), new Vec3(0.625, 0.40, 0.5)));
            int2objectmap.put(3, ImmutableList.of(new Vec3(0.375, 0.60, 0.53), new Vec3(0.59, 0.40, 0.375), new Vec3(0.625, 0.533, 0.59)));
            int2objectmap.put(4, ImmutableList.of(new Vec3(0.41, 0.60, 0.375), new Vec3(0.625, 0.533, 0.656), new Vec3(0.625, 0.40, 0.438), new Vec3(0.375, 0.467, 0.59)));
            return Int2ObjectMaps.unmodifiable(int2objectmap);
        });
        ONE_AABB = Block.box(6.5, 0.0, 6.5, 9.5, 6.0, 9.5);
        TWO_AABB = Block.box(5.0, 0.0, 6.0, 11.5, 6.0, 9.5);
        THREE_AABB = Block.box(4.5, 0.0, 4.5, 11.5, 6.0, 11.0);
        FOUR_AABB = Block.box(4.5, 0.0, 4.5, 11.5, 6.0, 12.0);
    }
}
