package com.anatevka.pandemonium.network;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.awt.*;
import java.util.List;
import java.util.Random;

public record ConvertResearchable(BlockPos pos) implements CustomPacketPayload {
    public static final Type<ConvertResearchable> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "convert_researchable"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertResearchable> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BlockPos.CODEC),
            ConvertResearchable::pos,
            ConvertResearchable::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnMain(final ConvertResearchable data, IPayloadContext context) {
        context.enqueueWork(() ->
                {
                    if (context.player().level().getBlockEntity(data.pos()) instanceof EscritoireBlockEntity be) {
                        if (be.getItemStack(0).is(ItemRegistry.LOST_PAGE)) {
                            ItemStack stack = be.getItemStack(0);
                            Random rand = new Random();
                            Color color = new Color(rand.nextInt(0, 256), rand.nextInt(0, 256), rand.nextInt(0, 256));
                            ItemStack research = new ItemStack(ItemRegistry.RESEARCH_PAGE.get(), 1);
                            List<String> researchText = stack.get(DataComponentRegistry.RESEARCH_TEXT).researchText();

                            research.set(DataComponentRegistry.RESEARCH_TEXT, new ResearchTextComponent(researchText));
                            research.set(DataComponentRegistry.CIPHER_DATA, stack.get(DataComponentRegistry.CIPHER_DATA));
                            research.set(DataComponents.DYED_COLOR, new DyedItemColor(color.getRGB(), true));


                            if (be.getItemStack(0).is(ItemRegistry.LOST_PAGE)) {
                                be.setItemStack(0, research);
                                be.setChanged();
                            }
                        }
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("pandemonium.networking.failed", e.getMessage()));
                    return null;
                });
    }
}
