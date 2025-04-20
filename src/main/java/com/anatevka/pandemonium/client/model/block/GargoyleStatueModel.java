package com.anatevka.pandemonium.client.model.block;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.GargoyleStatueBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class GargoyleStatueModel extends DefaultedBlockGeoModel<GargoyleStatueBlockEntity> {
    public GargoyleStatueModel() {
        super(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "gargoyle_statue"));
    }

    @Override
    public RenderType getRenderType(GargoyleStatueBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}