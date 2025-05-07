package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.research.ResearchMaterialStackHandler;
import com.anatevka.pandemonium.registry.BlockEntityRegistry;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.registry.SoundRegistry;
import com.anatevka.pandemonium.screen.EscritoireMenu;
import com.google.common.primitives.Ints;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EscritoireBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {
    private boolean deskOpen = false;
    private final List<Integer> cipherState = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);
    private final ResearchMaterialStackHandler researchMaterialInv = new ResearchMaterialStackHandler(ResearchRegistry.REGISTRY.size() - 1);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ItemStackHandler inventory = new ItemStackHandler(Escritoire.SLOT_COUNT + ResearchRegistry.REGISTRY.size() - 1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {if (slot == 0) {return 1;} return super.getStackLimit(slot, stack);}
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public EscritoireBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.ESCRITOIRE_BE.get(), pos, blockState);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    /* Inventory */

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {inv.setItem(i, inventory.getStackInSlot(i));}
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void eatItems() {
        for(int i = 0; i< researchMaterialInv.getSize(); i++){
            if(inventory.getStackInSlot(i+Escritoire.SLOT_COUNT).is(researchMaterialInv.getStackMaterial(i).resourceTag())) {
                if(researchMaterialInv.getStackSize(i) <= 90) {
                    inventory.extractItem(i + Escritoire.SLOT_COUNT, 1, false);
                    researchMaterialInv.increaseStackSize(i, 10);
                }
            }
        }
    }

    public void setItemStack(int index, ItemStack item){
        inventory.setStackInSlot(index, item);
    }

    public ItemStack getItemStack(int index) {
        return inventory.getStackInSlot(0);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(EscritoireMenu.TITLE);
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EscritoireMenu(
                i, inventory, ContainerLevelAccess.create(player.level(), this.getBlockPos()), this.inventory,
                new ContainerData() {
                    @Override
                    public int get(int i) {
                        return researchMaterialInv.getStackSize(i);
                    }

                    @Override
                    public void set(int i, int i1) {

                    }

                    @Override
                    public int getCount() {
                        return researchMaterialInv.getSize();
                    }
        },
                new ContainerData() {
                    @Override
                    public int get(int i) {
                        return switch (i) {
                            case 0 -> getBlockPos().getX();
                            case 1 -> getBlockPos().getY();
                            case 2 -> getBlockPos().getZ();
                            default -> 0;
                        };
                    }

                    @Override
                    public void set(int i, int i1) {

                    }

                    @Override
                    public int getCount() {
                        return 3;
                    }
                });
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("escritoire_inventory", inventory.serializeNBT(registries));
        for (int i = 0; i < ResearchRegistry.REGISTRY.size() - 1; i++) {
            tag.putInt(researchMaterialInv.getStackInSlot(i).getResearchMaterial().toString(), researchMaterialInv.getStackInSlot(i).getAmount());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("escritoire_inventory"));
        for (int i = 0; i < ResearchRegistry.REGISTRY.size() - 1; i++) {
            this.researchMaterialInv.setStackSize(i, tag.getInt(researchMaterialInv.getStackMaterial(i).toString()));
        }
    }

    /* Animation */

    public void animateState(BlockPos pos, Level level){
        Player player = level.getNearestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 3.0, false);
        if(this.deskOpen) {
            if (player == null) {
                this.deskOpen = false;
                triggerAnim("escritoire_state", "close");
            } else {
                this.deskOpen = true;
                triggerAnim("escritoire_state", "open");
            }
        } else {
            if (player != null) {
                this.deskOpen = true;
                triggerAnim("escritoire_state", "open");
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "escritoire_state", state -> PlayState.STOP)
                .triggerableAnim("close", Escritoire.ESCRITOIRE_CLOSE)
                .triggerableAnim("open", Escritoire.ESCRITOIRE_OPEN)
                .setSoundKeyframeHandler(event -> {
                    Player player = ClientUtil.getClientPlayer();

                    if (player != null) {
                        if (Objects.equals(event.getKeyframeData().getSound(), "pandemonium:escritoire_close")) {
                            this.level.playSound(player, this.getBlockPos(), SoundRegistry.ESCRITOIRE_CLOSE.get(), SoundSource.BLOCKS, 0.1f, 0.9f);
                        }
                        if (Objects.equals(event.getKeyframeData().getSound(), "pandemonium:escritoire_open")) {
                            this.level.playSound(player, this.getBlockPos(), SoundRegistry.ESCRITOIRE_OPEN.get(), SoundSource.BLOCKS, 0.1f, 1.1f);
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
