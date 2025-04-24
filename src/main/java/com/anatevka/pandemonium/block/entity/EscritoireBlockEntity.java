package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.research.ResearchMaterialStackHandler;
import com.anatevka.pandemonium.registry.BlockEntityRegistry;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

import java.util.Objects;

public class EscritoireBlockEntity extends BlockEntity implements GeoBlockEntity{
    public boolean deskOpen = false;

    public final ResearchMaterialStackHandler researchMaterialStackHandler = new ResearchMaterialStackHandler(ResearchRegistry.RESEARCH_MATERIALS.getEntries().size()-1);
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(2 + ResearchRegistry.REGISTRY.size()-1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {if (slot == 0 || slot == 1 ) {return 1;} return super.getStackLimit(slot, stack);}
        @Override
        protected void onContentsChanged(int slot) {setChanged();if(!level.isClientSide()) {level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);}}
    };

    public EscritoireBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.ESCRITOIRE_BE.get(), pos, blockState);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    /* Inventory */

    public void clearContents() {for(int i = 0; i < itemStackHandler.getSlots(); i++) {itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);}}

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {inv.setItem(i, itemStackHandler.getStackInSlot(i));}
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("escritoire_inventory", itemStackHandler.serializeNBT(registries));
        for (int i = 0; i<ResearchRegistry.REGISTRY.size()-1; i++) {
            tag.putInt(researchMaterialStackHandler.getStackInSlot(i).getResearchMaterial().toString(), researchMaterialStackHandler.getStackInSlot(i).getAmount());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("escritoire_inventory"));
        for (int i = 0; i<researchMaterialStackHandler.getSize(); i++) {
            this.researchMaterialStackHandler.setStackSize(i, tag.getInt(researchMaterialStackHandler.getStackMaterial(i).toString()));
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {return ClientboundBlockEntityDataPacket.create(this);}

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {return saveWithoutMetadata(registries);}

    /* Animation */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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

    //Fill up internal material buffers with valid items
    public void eatItems() {
        for(int i = 0; i<researchMaterialStackHandler.getSize(); i++){
            if(itemStackHandler.getStackInSlot(i+2).is(researchMaterialStackHandler.getStackMaterial(i).tag())) {
                if(researchMaterialStackHandler.getStackSize(i) <= 95) {
                    itemStackHandler.extractItem(i + 2, 1, false);
                    researchMaterialStackHandler.increaseStackSize(i, 5);
                }
            }
        }
    }
}
