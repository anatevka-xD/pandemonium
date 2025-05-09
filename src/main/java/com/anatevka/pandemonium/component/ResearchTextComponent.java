package com.anatevka.pandemonium.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record ResearchTextComponent(List<String> researchText){
    public static final List<String> DEFAULT_TEXT = Arrays.asList(
            ("The 68th spirit is called Belial, he is a mighty king and powerfull;"),
            (" "),
            ("he was Created next after Lucifer; he appeareth in ye forme of a Beautiful angel sitting in a Charriot of fire, speaking wth a comly voice, "),
            ("Note this king Belial must have offerings sacrafices & gifts presented to him."),
            (" "),
            ("& his seal is Thus wch is to be worne as a Lamin, before ye Exorcist &c.")
    );
    public static final Codec<ResearchTextComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.listOf(0, 99).fieldOf("research_text").forGetter(ResearchTextComponent::researchText)).apply(instance, ResearchTextComponent::new));
    @Override
    public int hashCode() {
        return Objects.hash(this.researchText);
    }
}
