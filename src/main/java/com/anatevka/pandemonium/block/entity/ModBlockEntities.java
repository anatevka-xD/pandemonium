package com.anatevka.pandemonium.block.entity;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Pandemonium.MODID);
    public static final Supplier<BlockEntityType<CopperPedestalBlockEntity>> COPPER_PEDESTAL_BE =
            BLOCK_ENTITIES.register("copper_pedestal_be",
                    () -> new BlockEntityType<>(
                            CopperPedestalBlockEntity::new,
                            ModBlocks.COPPER_PEDESTAL.get(),
                            ModBlocks.EXPOSED_COPPER_PEDESTAL.get(),
                            ModBlocks.WEATHERED_COPPER_PEDESTAL.get(),
                            ModBlocks.OXIDIZED_COPPER_PEDESTAL.get(),
                            ModBlocks.WAXED_COPPER_PEDESTAL.get(),
                            ModBlocks.WAXED_EXPOSED_COPPER_PEDESTAL.get(),
                            ModBlocks.WAXED_WEATHERED_COPPER_PEDESTAL.get(),
                            ModBlocks.WAXED_OXIDIZED_COPPER_PEDESTAL.get()
                    ));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}











