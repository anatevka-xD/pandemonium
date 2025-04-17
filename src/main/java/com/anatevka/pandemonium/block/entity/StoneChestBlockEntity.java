package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.block.custom.StoneChest;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StoneChestBlockEntity extends BaseContainerBlockEntity implements Container, MenuProvider, Nameable, GeoBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public StoneChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.STONE_CHEST_BE.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
                StoneChestBlockEntity.this.playSound(p_155064_, SoundEvents.GRINDSTONE_USE);
                StoneChestBlockEntity.this.updateBlockState(p_155064_, true);
            }

            protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
                StoneChestBlockEntity.this.playSound(p_155074_, SoundEvents.GRINDSTONE_USE);
                StoneChestBlockEntity.this.updateBlockState(p_155074_, false);
            }

            protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
            }

            protected boolean isOwnContainer(Player p_155060_) {
                if (p_155060_.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)p_155060_.containerMenu).getContainer();
                    return container == StoneChestBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void saveAdditional(CompoundTag p_187459_, HolderLookup.Provider p_323686_) {
        super.saveAdditional(p_187459_, p_323686_);
            ContainerHelper.saveAllItems(p_187459_, this.items, p_323686_);
    }

    protected void loadAdditional(CompoundTag p_155055_, HolderLookup.Provider p_324230_) {
        super.loadAdditional(p_155055_, p_324230_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155055_, this.items, p_324230_);
    }

    public int getContainerSize() {
        return 27;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    protected Component getDefaultName() {
        return Component.translatable("block.pandemonium.stone_chest");
    }

    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return ChestMenu.threeRows(id, player, this);
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
            triggerAnim("chest_state", "open");
        }

    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
            triggerAnim("chest_state", "close");
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), (BlockState)state.setValue(StoneChest.OPEN, open), 3);
    }

    void playSound(BlockState state, SoundEvent sound) {
        Vec3i vec3i = ((Direction)state.getValue(StoneChest.FACING)).getUnitVec3i();
        double d0 = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double d1 = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.level.playSound((Player)null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    /* Animation */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "chest_state", state -> PlayState.STOP)
                .triggerableAnim("close", StoneChest.CLOSE_CHEST)
                .triggerableAnim("open", StoneChest.OPEN_CHEST));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
