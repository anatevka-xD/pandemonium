package com.anatevka.pandemonium.client.model.block;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class EscritoireModel extends GeoModel<EscritoireBlockEntity> {
    private final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "geo/block/escritoire.geo.json");
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/block/escritoire.png");
    private final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "animations/escritoire.animation.json");

    @Override
    public ResourceLocation getModelResource(EscritoireBlockEntity animatable, @Nullable GeoRenderer<EscritoireBlockEntity> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(EscritoireBlockEntity animatable, @Nullable GeoRenderer<EscritoireBlockEntity> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(EscritoireBlockEntity animatable) {
        return ANIMATIONS;
    }
}