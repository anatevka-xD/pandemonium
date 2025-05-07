package com.anatevka.pandemonium.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record CipherState(List<Integer> cipherState){
    public static final List<Integer> DEFAULT_LIST = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);
    public static final Codec<CipherState> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.listOf(0,26).fieldOf("cipherState").forGetter(CipherState::cipherState)).apply(instance, CipherState::new));
    @Override
    public int hashCode() {
        return Objects.hash(this.cipherState);
    }
}
