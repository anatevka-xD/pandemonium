package com.anatevka.pandemonium.client.renderer.item;

import com.anatevka.pandemonium.item.StoneChestItem;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import static java.lang.Math.PI;

public class StoneChestItemRenderer extends GeoItemRenderer<StoneChestItem> {
    private static final Vector3f INVENTORY_DIFFUSE_LIGHT_0 = (new Vector3f(0.2F, -1.0F, 1.0F)).normalize().rotateY((float) -PI/2);
    private static final Vector3f INVENTORY_DIFFUSE_LIGHT_1 = (new Vector3f(-0.2F, -1.0F, 0.0F)).normalize().rotateY((float) -PI/2);

    public StoneChestItemRenderer(GeoModel<StoneChestItem> model) {
        super(model);
    }

    @Override
    protected void renderInGui(ItemDisplayContext transformType, PoseStack poseStack,
                               MultiBufferSource bufferSource, int packedLight, int packedOverlay, float partialTick) {
        RenderSystem.setShaderLights(INVENTORY_DIFFUSE_LIGHT_0, INVENTORY_DIFFUSE_LIGHT_1);

        MultiBufferSource.BufferSource defaultBufferSource = bufferSource instanceof MultiBufferSource.BufferSource bufferSource2 ? bufferSource2 : Minecraft.getInstance().renderBuffers().bufferSource();
        RenderType renderType = getRenderType(this.animatable, getTextureLocation(this.animatable), defaultBufferSource, partialTick);
        VertexConsumer buffer = ItemRenderer.getFoilBuffer(bufferSource, renderType, true, this.currentItemStack != null && this.currentItemStack.hasFoil());

        poseStack.pushPose();
        defaultRender(poseStack, this.animatable, defaultBufferSource, renderType, buffer, partialTick, packedLight);
        defaultBufferSource.endBatch();
        RenderSystem.enableDepthTest();
        Lighting.setupFor3DItems();
        poseStack.popPose();
    }
}
