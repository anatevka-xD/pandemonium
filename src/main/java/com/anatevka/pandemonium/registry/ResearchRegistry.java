package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.research.ResearchMaterial;
import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public final class ResearchRegistry {
    public static final ResourceKey<Registry<ResearchMaterial>> REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "research_materials"));
    public static final Registry<ResearchMaterial> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(true)
            .maxId(256)
            .create();

    public static final DeferredRegister<ResearchMaterial> RESEARCH_MATERIALS =
            DeferredRegister.create(REGISTRY, Pandemonium.MODID);

    public static final Supplier<ResearchMaterial> EMPTY = RESEARCH_MATERIALS.register("empty",
            properties -> new ResearchMaterial(
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, ""),
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, ""),
                    Items.STONE, TagRegistry.Items.EMPTY, TagRegistry.Items.EMPTY,
                    -1, 0xff000000));
    public static final Supplier<ResearchMaterial> PAPER = RESEARCH_MATERIALS.register("paper",
            properties -> new ResearchMaterial(
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_paper.png"),
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_paper_unused.png"),
                    Items.PAPER, TagRegistry.Items.MATERIAL_PAPER, TagRegistry.Items.WANTS_PAPER,
                    0, 0xffc0a999));
    public static final Supplier<ResearchMaterial> INK = RESEARCH_MATERIALS.register("ink",
            properties -> new ResearchMaterial(
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_ink.png"),
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_ink_unused.png"),
                    Items.INK_SAC, TagRegistry.Items.MATERIAL_INK, TagRegistry.Items.WANTS_INK,
                    1, 0xff706ead));
    public static final Supplier<ResearchMaterial> AMETHYST = RESEARCH_MATERIALS.register("amethyst",
            properties -> new ResearchMaterial(
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_amethyst.png"),
                    ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, "textures/gui/sprites/escritoire/slot_amethyst_unused.png"),
                    Items.AMETHYST_SHARD, TagRegistry.Items.MATERIAL_AMETHYST, TagRegistry.Items.WANTS_AMETHYST,
                    2, 0xffb38ef3));

    public static void register(IEventBus eventBus) {
        RESEARCH_MATERIALS.register(eventBus);
    }
}

