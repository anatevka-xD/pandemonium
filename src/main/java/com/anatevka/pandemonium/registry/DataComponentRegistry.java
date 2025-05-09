package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class DataComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Pandemonium.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CipherStateComponent>> CIPHER_DATA =
            register("cipher_state", builder -> builder.persistent(CipherStateComponent.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResearchTextComponent>> RESEARCH_TEXT =
            register("research_text", builder -> builder.persistent(ResearchTextComponent.CODEC));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, ()-> builderOperator.apply(DataComponentType.builder()).build());
    }
    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
