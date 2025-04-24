package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.entity.CopperPedestalBlockEntity;
import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.block.entity.GargoyleStatueBlockEntity;
import com.anatevka.pandemonium.block.entity.StoneChestBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Pandemonium.MODID);
    public static final Supplier<BlockEntityType<CopperPedestalBlockEntity>> COPPER_PEDESTAL_BE =
            BLOCK_ENTITIES.register("copper_pedestal_be",
                    () -> new BlockEntityType<>(
                            CopperPedestalBlockEntity::new,
                            BlockRegistry.COPPER_PEDESTAL.get(),
                            BlockRegistry.EXPOSED_COPPER_PEDESTAL.get(),
                            BlockRegistry.WEATHERED_COPPER_PEDESTAL.get(),
                            BlockRegistry.OXIDIZED_COPPER_PEDESTAL.get(),
                            BlockRegistry.WAXED_COPPER_PEDESTAL.get(),
                            BlockRegistry.WAXED_EXPOSED_COPPER_PEDESTAL.get(),
                            BlockRegistry.WAXED_WEATHERED_COPPER_PEDESTAL.get(),
                            BlockRegistry.WAXED_OXIDIZED_COPPER_PEDESTAL.get()
                    ));
    public static final Supplier<BlockEntityType<StoneChestBlockEntity>> STONE_CHEST_BE =
            BLOCK_ENTITIES.register("stone_chest_be",
                    () -> new BlockEntityType<>(
                            StoneChestBlockEntity::new,
                            BlockRegistry.STONE_CHEST.get()
                    ));
    public static final Supplier<BlockEntityType<GargoyleStatueBlockEntity>> GARGOYLE_STATUE_BE =
            BLOCK_ENTITIES.register("gargoyle_chest_be",
                    () -> new BlockEntityType<>(
                            GargoyleStatueBlockEntity::new,
                            BlockRegistry.GARGOYLE_STATUE.get()
                    ));
    public static final Supplier<BlockEntityType<EscritoireBlockEntity>> ESCRITOIRE_BE =
            BLOCK_ENTITIES.register("escritoire_be",
                    () -> new BlockEntityType<>(
                            EscritoireBlockEntity::new,
                            BlockRegistry.ESCRITOIRE.get()
                    ));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}











