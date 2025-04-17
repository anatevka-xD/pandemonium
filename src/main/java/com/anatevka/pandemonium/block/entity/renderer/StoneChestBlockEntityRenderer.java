package com.anatevka.pandemonium.block.entity.renderer;

import com.anatevka.pandemonium.block.entity.StoneChestBlockEntity;
import com.anatevka.pandemonium.geo.block.StoneChestGeoModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StoneChestBlockEntityRenderer extends GeoBlockRenderer<StoneChestBlockEntity> {
    public StoneChestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new StoneChestGeoModel());
    }
}