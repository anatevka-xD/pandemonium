package com.anatevka.pandemonium.client.renderer.block;

import com.anatevka.pandemonium.block.entity.StoneChestBlockEntity;
import com.anatevka.pandemonium.client.model.block.StoneChestModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StoneChestBlockEntityRenderer extends GeoBlockRenderer<StoneChestBlockEntity> {
    public StoneChestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new StoneChestModel());
    }
}