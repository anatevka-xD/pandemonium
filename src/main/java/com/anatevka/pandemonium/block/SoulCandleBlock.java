package com.anatevka.pandemonium.block;

import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.block.entity.SoulCandleBlockEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class SoulCandleBlock extends AbstractCandleBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final MapCodec<SoulCandleBlock> CODEC = simpleCodec(SoulCandleBlock::new);
    public static final int MIN_CANDLES = 1;
    public static final int MAX_CANDLES = 4;
    public static final IntegerProperty CANDLES;
    public static final BooleanProperty LIT;
    public static final BooleanProperty WATERLOGGED;
    public static final ToIntFunction<BlockState> LIGHT_EMISSION;
    private static final Int2ObjectMap<List<Vec3>> PARTICLE_OFFSETS;
    private static final VoxelShape ONE_AABB;
    private static final VoxelShape TWO_AABB;
    private static final VoxelShape THREE_AABB;
    private static final VoxelShape FOUR_AABB;

    public MapCodec<SoulCandleBlock> codec() {
        return CODEC;
    }

    public SoulCandleBlock(BlockBehaviour.Properties p_152801_) {
        super(p_152801_);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(CANDLES, 1)).setValue(LIT, false)).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {return new SoulCandleBlockEntity(blockPos, blockState);}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : ((level1, pos, state, blockEntity) -> tick(level, pos, state));
    }

    protected void tick(Level level, BlockPos pos, BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof SoulCandleBlockEntity soulCandleBlockEntity) {
            Player player = level.getNearestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, state.getValue(CANDLES) * 4.0, false);
            if (player == null && canExtinguish(state)) {
                extinguish(null, state, level, pos);
                level.sendBlockUpdated(pos, state, state, 3);
            } else if (player != null && canLight(state)) {
                setLit(level, state, pos, true);
                level.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }

    private static void setLit(LevelAccessor level, BlockState state, BlockPos pos, boolean lit) {
        level.setBlock(pos, (BlockState)state.setValue(LIT, lit), 11);
    }

    @Override
    public void animateTick(BlockState p_220697_, Level p_220698_, BlockPos p_220699_, RandomSource p_220700_) {
        if ((Boolean)p_220697_.getValue(LIT)) {
            this.getParticleOffsets(p_220697_).forEach((p_220695_) -> {
                addParticlesAndSound(p_220698_, p_220695_.add((double)p_220699_.getX(), (double)p_220699_.getY(), (double)p_220699_.getZ()), p_220700_);
            });
        }

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

    // Mostly duplicate of vanilla candle

    protected boolean canBeReplaced(BlockState p_152814_, BlockPlaceContext p_152815_) {
        return !p_152815_.isSecondaryUseActive() && p_152815_.getItemInHand().getItem() == this.asItem() && (Integer)p_152814_.getValue(CANDLES) < 4 ? true : super.canBeReplaced(p_152814_, p_152815_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_152803_) {
        BlockState blockstate = p_152803_.getLevel().getBlockState(p_152803_.getClickedPos());
        if (blockstate.is(this)) {
            return (BlockState)blockstate.cycle(CANDLES);
        } else {
            FluidState fluidstate = p_152803_.getLevel().getFluidState(p_152803_.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return (BlockState)super.getStateForPlacement(p_152803_).setValue(WATERLOGGED, flag);
        }
    }

    protected BlockState updateShape(BlockState p_152833_, LevelReader p_374497_, ScheduledTickAccess p_374340_, BlockPos p_152837_, Direction p_152834_, BlockPos p_152838_, BlockState p_152835_, RandomSource p_374318_) {
        if ((Boolean)p_152833_.getValue(WATERLOGGED)) {
            p_374340_.scheduleTick(p_152837_, Fluids.WATER, Fluids.WATER.getTickDelay(p_374497_));
        }

        return super.updateShape(p_152833_, p_374497_, p_374340_, p_152837_, p_152834_, p_152838_, p_152835_, p_374318_);
    }

    protected FluidState getFluidState(BlockState p_152844_) {
        return (Boolean)p_152844_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152844_);
    }

    protected VoxelShape getShape(BlockState p_152817_, BlockGetter p_152818_, BlockPos p_152819_, CollisionContext p_152820_) {
        switch ((Integer)p_152817_.getValue(CANDLES)) {
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152840_) {
        p_152840_.add(new Property[]{CANDLES, LIT, WATERLOGGED});
    }

    public boolean placeLiquid(LevelAccessor p_152805_, BlockPos p_152806_, BlockState p_152807_, FluidState p_152808_) {
        if (!(Boolean)p_152807_.getValue(WATERLOGGED) && p_152808_.getType() == Fluids.WATER) {
            BlockState blockstate = (BlockState)p_152807_.setValue(WATERLOGGED, true);
            p_152805_.setBlock(p_152806_, blockstate, 3);
            p_152805_.scheduleTick(p_152806_, p_152808_.getType(), p_152808_.getType().getTickDelay(p_152805_));
            return true;
        } else {
            return false;
        }
    }

    public static boolean canLight(BlockState state) {
        return !(Boolean)state.getValue(LIT);
    }

    public static boolean canExtinguish(BlockState state) {
        return (Boolean)state.getValue(LIT);
    }

    protected Iterable<Vec3> getParticleOffsets(BlockState p_152812_) {
        return (Iterable)PARTICLE_OFFSETS.get((Integer)p_152812_.getValue(CANDLES));
    }

    protected boolean canBeLit(BlockState p_152842_) {
        return super.canBeLit(p_152842_);
    }

    protected boolean canSurvive(BlockState p_152829_, LevelReader p_152830_, BlockPos p_152831_) {
        return Block.canSupportCenter(p_152830_, p_152831_.below(), Direction.UP);
    }

    static {
        CANDLES = BlockStateProperties.CANDLES;
        LIT = AbstractCandleBlock.LIT;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        LIGHT_EMISSION = (p_152848_) -> {
            return (Boolean)p_152848_.getValue(LIT) ? 3 * (Integer)p_152848_.getValue(CANDLES) : 0;
        };
        PARTICLE_OFFSETS = (Int2ObjectMap) Util.make(() -> {
            Int2ObjectMap<List<Vec3>> int2objectmap = new Int2ObjectOpenHashMap();
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
