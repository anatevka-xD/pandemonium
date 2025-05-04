package com.anatevka.pandemonium.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;

public record CipherStateData (int cipherSlot, List<Integer> cipherState){
    public static final Codec<CipherStateData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("cipherSlot").forGetter(CipherStateData::cipherSlot),
                    Codec.INT.listOf(0,26).fieldOf("cipherState").forGetter(CipherStateData::cipherState)).apply(instance, CipherStateData::new));
    @Override
    public int hashCode() {
        return Objects.hash(this.cipherSlot, this.cipherState);
    }
}
