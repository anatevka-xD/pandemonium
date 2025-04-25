package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.registry.BlockRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pandemonium.MODID);
    public static final Supplier<CreativeModeTab> PANDEMONIUM_TAB = CREATIVE_MODE_TABS.register("pandemonium_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemRegistry.SIGNUM_MALI.get()))
                    .title(Component.translatable("tab.pandemonium.pandemonium"))
                    .displayItems((parameters, output) -> {
                        output.accept(ItemRegistry.SIGNUM_MALI);
                        output.accept(ItemRegistry.MANDRAKE_SEEDS);
                        output.accept(ItemRegistry.GRIMOIRE);
                        output.accept(ItemRegistry.TATTERED_PAGE);
                        output.accept(ItemRegistry.LOST_PAGE);
                        output.accept(ItemRegistry.RESEARCH_PAGE);
                        output.accept(BlockRegistry.FLESH_BLOCK);
                        output.accept(BlockRegistry.ALTAR);
                        output.accept(BlockRegistry.OAK_SHELF);
                        output.accept(BlockRegistry.DARK_OAK_SHELF);
                        output.accept(BlockRegistry.CHERRY_SHELF);
                        output.accept(BlockRegistry.SPRUCE_SHELF);
                        output.accept(BlockRegistry.MANGROVE_SHELF);
                        output.accept(BlockRegistry.BIRCH_SHELF);
                        output.accept(BlockRegistry.JUNGLE_SHELF);
                        output.accept(BlockRegistry.ACACIA_SHELF);
                        output.accept(BlockRegistry.STONE_PILLAR);
                        output.accept(BlockRegistry.CHISELED_STONE_PILLAR);
                        output.accept(BlockRegistry.STONE_TILES);
                        output.accept(BlockRegistry.STONE_TILE_STAIRS);
                        output.accept(BlockRegistry.STONE_TILE_SLAB);
                        output.accept(BlockRegistry.CHIPPED_STONE_TILES);
                        output.accept(ItemRegistry.STONE_CHEST);
                        output.accept(ItemRegistry.GARGOYLE_STATUE);
                        output.accept(ItemRegistry.ESCRITOIRE);
                        output.accept(BlockRegistry.COPPER_PEDESTAL);
                        output.accept(BlockRegistry.EXPOSED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.WEATHERED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.OXIDIZED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.WAXED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_PEDESTAL);
                        output.accept(BlockRegistry.COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.EXPOSED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.WEATHERED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.OXIDIZED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.WAXED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_VILLAGER_STATUE);
                        output.accept(BlockRegistry.COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.EXPOSED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.WEATHERED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.OXIDIZED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.WAXED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_CREEPER_STATUE);
                        output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_CREEPER_STATUE);
                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
