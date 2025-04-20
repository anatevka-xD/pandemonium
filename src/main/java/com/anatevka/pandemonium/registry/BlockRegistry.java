package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.*;
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

    public static final DeferredBlock<StonePillar> STONE_PILLAR = registerBlockWithItem("stone_pillar", StonePillar::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<StonePillar> CHISELED_STONE_PILLAR = registerBlockWithItem("chiseled_stone_pillar", StonePillar::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> STONE_TILES = registerBlockWithItem("stone_tiles", Block::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<StairBlock> STONE_TILE_STAIRS = registerBlockWithItem("stone_tile_stairs",
            (properties) -> new StairBlock(BlockRegistry.STONE_TILES.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<SlabBlock> STONE_TILE_SLAB = registerBlockWithItem("stone_tile_slab", SlabBlock::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<Block> CHIPPED_STONE_TILES = registerBlockWithItem("chipped_stone_tiles", Block::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<CopperPedestal> COPPER_PEDESTAL = registerBlockWithItem("copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestal> EXPOSED_COPPER_PEDESTAL = registerBlockWithItem("exposed_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.EXPOSED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestal> WEATHERED_COPPER_PEDESTAL = registerBlockWithItem("weathered_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.WEATHERED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());
    public static final DeferredBlock<CopperPedestal> OXIDIZED_COPPER_PEDESTAL = registerBlockWithItem("oxidized_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks());

    public static final DeferredBlock<CopperPedestal> WAXED_COPPER_PEDESTAL = registerBlockWithItem("waxed_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestal> WAXED_EXPOSED_COPPER_PEDESTAL = registerBlockWithItem("waxed_exposed_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestal> WAXED_WEATHERED_COPPER_PEDESTAL = registerBlockWithItem("waxed_weathered_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<CopperPedestal> WAXED_OXIDIZED_COPPER_PEDESTAL = registerBlockWithItem("waxed_oxidized_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));

    public static final DeferredBlock<CopperVillagerStatue> COPPER_VILLAGER_STATUE = registerBlockWithItem("copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> EXPOSED_COPPER_VILLAGER_STATUE = registerBlockWithItem("exposed_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> WEATHERED_COPPER_VILLAGER_STATUE = registerBlockWithItem("weathered_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> OXIDIZED_COPPER_VILLAGER_STATUE = registerBlockWithItem("oxidized_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());

    public static final DeferredBlock<CopperVillagerStatue> WAXED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> WAXED_EXPOSED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_exposed_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> WAXED_WEATHERED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_weathered_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperVillagerStatue> WAXED_OXIDIZED_COPPER_VILLAGER_STATUE = registerBlockWithItem("waxed_oxidized_copper_villager_statue",
            (properties) -> new CopperVillagerStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());

    public static final DeferredBlock<CopperCreeperStatue> COPPER_CREEPER_STATUE = registerBlockWithItem("copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> EXPOSED_COPPER_CREEPER_STATUE = registerBlockWithItem("exposed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> WEATHERED_COPPER_CREEPER_STATUE = registerBlockWithItem("weathered_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> OXIDIZED_COPPER_CREEPER_STATUE = registerBlockWithItem("oxidized_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.UNAFFECTED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.COPPER_BLOCK)
                    .randomTicks()
                    .noOcclusion());

    public static final DeferredBlock<CopperCreeperStatue> WAXED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> WAXED_EXPOSED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_exposed_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> WAXED_WEATHERED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_weathered_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());
    public static final DeferredBlock<CopperCreeperStatue> WAXED_OXIDIZED_COPPER_CREEPER_STATUE = registerBlockWithItem("waxed_oxidized_copper_creeper_statue",
            (properties) -> new CopperCreeperStatue(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK)
                    .noOcclusion());

    public static final DeferredBlock<GargoyleStatue> GARGOYLE_STATUE = BLOCKS.registerBlock("gargoyle_statue", GargoyleStatue::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE)
            .noOcclusion());

    public static final DeferredBlock<StoneChest> STONE_CHEST = BLOCKS.registerBlock("stone_chest", StoneChest::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.STONE)
            .noOcclusion());

    public static final DeferredBlock<Escritoire> ESCRITOIRE = BLOCKS.registerBlock("escritoire", Escritoire::new, BlockBehaviour.Properties
            .ofFullCopy(Blocks.OAK_PLANKS)
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
