package com.anatevka.pandemonium.client.model.block;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.StoneChestBlockEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class StoneChestModel extends GeoModel<StoneChestBlockEntity> {
    private final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "geo/block/stone_chest.geo.json");
    private final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/block/stone_chest.png");
    private final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "animations/stone_chest.animation.json");

    @Override
    public ResourceLocation getModelResource(StoneChestBlockEntity animatable, @Nullable GeoRenderer<StoneChestBlockEntity> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(StoneChestBlockEntity animatable, @Nullable GeoRenderer<StoneChestBlockEntity> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(StoneChestBlockEntity animatable) {
        return ANIMATIONS;
    }
}
