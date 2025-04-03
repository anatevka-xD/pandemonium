package com.anatevka.pandemonium.block;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.custom.CopperPedestal;
import com.anatevka.pandemonium.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Pandemonium.MODID);

    public static final DeferredBlock<Block> FLESH_BLOCK = registerBlockWithItem("flesh_block", Block::new, BlockBehaviour.Properties.of()
            .strength(3f)
            .sound(SoundType.MUD));
    public static final DeferredBlock<Block> ALTAR = registerBlockWithItem("altar", Block::new, BlockBehaviour.Properties.of()
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.NETHERITE_BLOCK));

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

    public static final DeferredBlock<Block> WAXED_COPPER_PEDESTAL = registerBlockWithItem("waxed_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_PEDESTAL = registerBlockWithItem("waxed_exposed_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_PEDESTAL = registerBlockWithItem("waxed_weathered_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_PEDESTAL = registerBlockWithItem("waxed_oxidized_copper_pedestal",
            (properties) -> new CopperPedestal(WeatheringCopper.WeatherState.OXIDIZED, properties), BlockBehaviour.Properties
                    .ofFullCopy(Blocks.WAXED_COPPER_BLOCK));

    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> blockCreator, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockCreator, properties);
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
