package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Pandemonium.MODID);

    public static final Supplier<SoundEvent> STONE_CHEST_OPEN = registerSoundEvent("stone_chest_open");
    public static final Supplier<SoundEvent> STONE_CHEST_CLOSE = registerSoundEvent("stone_chest_close");
    public static final Supplier<SoundEvent> STONE_CHEST_PLINK = registerSoundEvent("stone_chest_plink");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
