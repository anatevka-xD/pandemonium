package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.block.StoneChest;
import com.anatevka.pandemonium.registry.BlockEntityRegistry;
import com.anatevka.pandemonium.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

public class EscritoireBlockEntity extends BlockEntity implements GeoBlockEntity {
    public boolean deskOpen = false;

    public EscritoireBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.ESCRITOIRE_BE.get(), pos, blockState);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public void animateState(BlockPos pos, Level level){
        Player player = level.getNearestPlayer((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 3.0, false);
        if(this.deskOpen) {
            if (player == null) {
                this.deskOpen = false;
                triggerAnim("escritoire_state", "close");
            } else {
                triggerAnim("escritoire_state", "open");
            }
        } else {
            if (player != null) {
                this.deskOpen = true;
                triggerAnim("escritoire_state", "open");
            } else {
                triggerAnim("escritoire_state", "close");
            }
        }
    }

    /* Animation */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "escritoire_state", state -> PlayState.CONTINUE)
                .triggerableAnim("close", Escritoire.ESCRITOIRE_CLOSE)
                .triggerableAnim("open", Escritoire.ESCRITOIRE_OPEN)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
