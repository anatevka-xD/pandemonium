package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagRegistry {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> EMPTY = createTag("empty");
        public static final TagKey<Item> RESEARCH_TEXT = createTag("research_text");
        public static final TagKey<Item> TRANSLATION_TEXT = createTag("translation_text");
        public static final TagKey<Item> WANTS_CIPHER = createTag("wants_cipher");
        public static final TagKey<Item> WANTS_PAPER = createTag("wants_paper");
        public static final TagKey<Item> WANTS_INK = createTag("wants_ink");
        public static final TagKey<Item> WANTS_AMETHYST = createTag("wants_amethyst");
        public static final TagKey<Item> MATERIAL_PAPER = createTag("material_paper");
        public static final TagKey<Item> MATERIAL_INK = createTag("material_ink");
        public static final TagKey<Item> MATERIAL_AMETHYST = createTag("material_amethyst");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, name));
        }
    }
}
