package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.*;
import com.anatevka.pandemonium.block.EscritoireBlock;
import com.anatevka.pandemonium.block.ShelfBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Pandemonium.MODID);

    public static final DeferredBlock<Block> FLESH_BLOCK = registerBlockWithItem("flesh_block", Block::new, BlockBehaviour.Properties.of()
            .strength(3f)
            .sound(SoundType.MUD));
    public static final DeferredBlock<Block> ALTAR = registerBlockWithItem("altar", Block::new, BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK));

    public static final DeferredBlock<ShelfBlock> OAK_SHELF = registerBlockWithItem("oak_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<ShelfBlock> SPRUCE_SHELF = registerBlockWithItem("spruce_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.SPRUCE_PLANKS));
    public static final DeferredBlock<ShelfBlock> BIRCH_SHELF = registerBlockWithItem("birch_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.BIRCH_PLANKS));
    public static final DeferredBlock<ShelfBlock> JUNGLE_SHELF = registerBlockWithItem("jungle_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.JUNGLE_PLANKS));
    public static final DeferredBlock<ShelfBlock> ACACIA_SHELF = registerBlockWithItem("acacia_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.ACACIA_PLANKS));
    public static final DeferredBlock<ShelfBlock> MANGROVE_SHELF = registerBlockWithItem("mangrove_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.MANGROVE_PLANKS));
    public static final DeferredBlock<ShelfBlock> CHERRY_SHELF = registerBlockWithItem("cherry_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.CHERRY_PLANKS));
    public static final DeferredBlock<ShelfBlock> DARK_OAK_SHELF = registerBlockWithItem("dark_oak_shelf", ShelfBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.DARK_OAK_PLANKS));

    public static final DeferredBlock<StonePillarBlock> STONE_PILLAR = registerBlockWithItem("stone_pillar", StonePillarBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<StonePillarBlock> CHISELED_STONE_PILLAR = registerBlockWithItem("chiseled_stone_pillar", StonePillarBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> STONE_TILES = registerBlockWithItem("stone_tiles", Block::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<StairBlock> STONE_TILE_STAIRS = registerBlockWithItem("stone_tile_stairs",
            (properties) -> new StairBlock(BlockRegistry.STONE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<SlabBlock> STONE_TILE_SLAB = registerBlockWithItem("stone_tile_slab", SlabBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> CHIPPED_STONE_TILES = registerBlockWithItem("chipped_stone_tiles", Block::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<CopperPedestalBlock> COPPER_PEDESTAL = registerBlockWithItem("copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestalBlock> EXPOSED_COPPER_PEDESTAL = registerBlockWithItem("exposed_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.EXPOSED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestalBlock> WEATHERED_COPPER_PEDESTAL = registerBlockWithItem("weathered_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.WEATHERED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestalBlock> OXIDIZED_COPPER_PEDESTAL = registerBlockWithItem("oxidized_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());

    public static final DeferredBlock<CopperPedestalBlock> WAXED_COPPER_PEDESTAL = registerBlockWithItem("waxed_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestalBlock> WAXED_EXPOSED_COPPER_PEDESTAL = registerBlockWithItem("waxed_exposed_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestalBlock> WAXED_WEATHERED_COPPER_PEDESTAL = registerBlockWithItem("waxed_weathered_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestalBlock> WAXED_OXIDIZED_COPPER_PEDESTAL = registerBlockWithItem("waxed_oxidized_copper_pedestal",
            (properties) -> new CopperPedestalBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));

    public static final DeferredBlock<CopperVillagerStatueBlock> COPPER_VILLAGER_STATUE = registerBlockWithItem("copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> EXPOSED_COPPER_VILLAGER_STATUE = registerBlockWithItem("exposed_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> WEATHERED_COPPER_VILLAGER_STATUE = registerBlockWithItem("weathered_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> OXIDIZED_COPPER_VILLAGER_STATUE = registerBlockWithItem("oxidized_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());

    public static final DeferredBlock<CopperVillagerStatueBlock> WAXED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> WAXED_EXPOSED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_exposed_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> WAXED_WEATHERED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_weathered_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatueBlock> WAXED_OXIDIZED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_oxidized_copper_villager_statue",
            (properties) -> new CopperVillagerStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());

    public static final DeferredBlock<CopperCreeperStatueBlock> COPPER_CREEPER_STATUE = registerBlockWithItem("copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> EXPOSED_COPPER_CREEPER_STATUE = registerBlockWithItem("exposed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> WEATHERED_COPPER_CREEPER_STATUE = registerBlockWithItem("weathered_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> OXIDIZED_COPPER_CREEPER_STATUE = registerBlockWithItem("oxidized_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());

    public static final DeferredBlock<CopperCreeperStatueBlock> WAXED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> WAXED_EXPOSED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_exposed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> WAXED_WEATHERED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_weathered_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatueBlock> WAXED_OXIDIZED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_oxidized_copper_creeper_statue",
            (properties) -> new CopperCreeperStatueBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());

    public static final DeferredBlock<GargoyleStatueBlock> GARGOYLE_STATUE = BLOCKS.registerBlock("gargoyle_statue", GargoyleStatueBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE)
            .noOcclusion());

    public static final DeferredBlock<StoneChestBlock> STONE_CHEST = BLOCKS.registerBlock("stone_chest", StoneChestBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE)
            .noOcclusion());

    public static final DeferredBlock<EscritoireBlock> ESCRITOIRE = BLOCKS.registerBlock("escritoire", EscritoireBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.OAK_PLANKS)
            .noOcclusion());

    public static final DeferredBlock<SoulCandleBlock> SOUL_CANDLE = registerBlockWithItem("soul_candle", SoulCandleBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.SOUL_SOIL)
            .lightLevel(SoulCandleBlock.LIGHT_EMISSION));
    public static final DeferredBlock<GnomeBlock> GNOME = registerBlockWithItem("gnome", GnomeBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.FLOWER_POT)
            .noOcclusion());

    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> blockCreator, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockCreator, properties);
        ItemRegistry.ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
