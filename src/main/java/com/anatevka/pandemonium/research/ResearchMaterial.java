package com.anatevka.pandemonium.research;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ResearchMaterial(ResourceLocation icon, ResourceLocation unusedIcon, Item item, TagKey<Item> resourceTag, TagKey<Item> requirementTag, int index, int color) {

}
