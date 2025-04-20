package com.anatevka.pandemonium.client.renderer.block;

import com.anatevka.pandemonium.block.entity.GargoyleStatueBlockEntity;
import com.anatevka.pandemonium.client.model.block.GargoyleStatueModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GargoyleStatueBlockEntityRenderer extends GeoBlockRenderer<GargoyleStatueBlockEntity> {
    public GargoyleStatueBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new GargoyleStatueModel());
    }
}