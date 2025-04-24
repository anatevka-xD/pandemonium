package com.anatevka.pandemonium.client.renderer.block;

import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.client.model.block.EscritoireModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EscritoireBlockEntityRenderer extends GeoBlockRenderer<EscritoireBlockEntity> {
    public EscritoireBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new EscritoireModel());
    }
}