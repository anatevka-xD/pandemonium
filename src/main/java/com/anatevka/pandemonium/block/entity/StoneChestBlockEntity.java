package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.block.StoneChest;
import com.anatevka.pandemonium.registry.BlockEntityRegistry;
import com.anatevka.pandemonium.registry.SoundRegistry;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

public class StoneChestBlockEntity extends BaseContainerBlockEntity implements Container, MenuProvider, Nameable, GeoBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public StoneChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.STONE_CHEST_BE.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
                StoneChestBlockEntity.this.updateBlockState(p_155064_, true);
            }

            protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
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
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
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

    /* Animation */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "chest_state", state -> PlayState.STOP)
                .triggerableAnim("close", StoneChest.CLOSE_CHEST)
                .triggerableAnim("open", StoneChest.OPEN_CHEST)
                .setSoundKeyframeHandler(event -> {
                    Player player = ClientUtil.getClientPlayer();

                    if (player != null) {
                        if (Objects.equals(event.getKeyframeData().getSound(), "pandemonium:stone_chest_close")) {
                            this.level.playSound(player, this.getBlockPos(), SoundRegistry.STONE_CHEST_CLOSE.get(), SoundSource.BLOCKS);
                        }
                        if (Objects.equals(event.getKeyframeData().getSound(), "pandemonium:stone_chest_open")) {
                            this.level.playSound(player, this.getBlockPos(), SoundRegistry.STONE_CHEST_OPEN.get(), SoundSource.BLOCKS);
                        }
                        if (Objects.equals(event.getKeyframeData().getSound(), "pandemonium:stone_chest_plink")) {
                            this.level.playSound(player, this.getBlockPos(), SoundRegistry.STONE_CHEST_PLINK.get(), SoundSource.BLOCKS);
                        }
                    }
                })
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
