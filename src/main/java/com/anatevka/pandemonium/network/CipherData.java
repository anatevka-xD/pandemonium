package com.anatevka.pandemonium.network;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record CipherData(BlockPos pos, List<Integer> cipherState) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CipherData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "cipher_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CipherData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BlockPos.CODEC),
            CipherData::pos,
            ByteBufCodecs.fromCodec(Codec.INT.listOf()),
            CipherData::cipherState,
            CipherData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataOnMain(final CipherData data, IPayloadContext context) {
        context.enqueueWork(() ->
                {
                    if (context.player().level().getBlockEntity(data.pos()) instanceof EscritoireBlockEntity be) {
                        ItemStack stack = be.getItemStack(0);
                        if (stack.is(ItemRegistry.RESEARCH_PAGE)) {
                            List<Integer> targetState = stack.get(DataComponentRegistry.CIPHER_DATA).targetState();
                            stack.set(DataComponentRegistry.CIPHER_DATA, new CipherStateComponent(data.cipherState(), targetState));
                            be.setItemStack(0, stack);
                            be.setChanged();
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
