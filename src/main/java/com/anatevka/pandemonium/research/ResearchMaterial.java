package com.anatevka.pandemonium.research;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ResearchMaterial(ResourceLocation icon, ResourceLocation unusedIcon, Item item, TagKey<Item> tag, int index, int color) {

}
