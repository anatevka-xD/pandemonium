package com.anatevka.pandemonium.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record CipherData(int cipherSlot, List<Integer> cipherState){
    public static final List<Integer> DEFAULT_LIST = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);
    public static final Codec<CipherData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("cipherSlot").forGetter(CipherData::cipherSlot),
                    Codec.INT.listOf(0,26).fieldOf("cipherState").forGetter(CipherData::cipherState)).apply(instance, CipherData::new));
    @Override
    public int hashCode() {
        return Objects.hash(this.cipherSlot, this.cipherState);
    }
}
