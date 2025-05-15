package com.anatevka.pandemonium.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Objects;

public record ResearchTextComponent(List<String> researchText){
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final List<String> DEFAULT_TEXT = List.of("Tis true without lying, certain and most true. ", " ",
            "That which is below is like that which is above and that which is above is like that which is below to do the miracle of one only thing.");
    public static final Codec<ResearchTextComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.listOf(0, 99).fieldOf("research_text").forGetter(ResearchTextComponent::researchText)).apply(instance, ResearchTextComponent::new));
    @Override
    public int hashCode() {
        return Objects.hash(this.researchText);
    }

    public static String encryptString(String string, List<Integer> cipher){
        String newString = "";
        int len = string.length();
        for(int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if (alphabet.indexOf(c) == -1) {
                newString += c;
            } else {
                Integer shift = (int) (Math.floor(alphabet.indexOf(c)/26.0) * 26.0);
                Integer cipherIndex = (alphabet.indexOf(c) - shift);
                Integer cipherValue = cipher.get(cipherIndex);
                char encryptedChar = alphabet.charAt(cipherValue + shift);

                newString += encryptedChar;
            }
        }
        return newString;
    }

    public static String decryptString(String string, List<Integer> cipher){
        String newString = "";
        int len = string.length();
        for(int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if (alphabet.indexOf(c) == -1) {
                newString += c;
            } else {
                Integer shift = (int) (Math.floor(alphabet.indexOf(c)/26.0) * 26.0);
                Integer cipherIndex = (alphabet.indexOf(c) - shift);
                Integer cipherValue = cipher.get(cipherIndex);
                char encryptedChar = alphabet.charAt(cipherValue + shift);

                newString += encryptedChar;
            }
        }
        return newString;
    }
}
