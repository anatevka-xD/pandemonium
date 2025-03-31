package com.anatevka.pandemonium.block;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Pandemonium.MODID);
    public static final DeferredBlock<Block> FLESH_BLOCK = registerBlockWithItem("flesh_block", Block::new, BlockBehaviour.Properties.of()
            .strength(4f)
            .sound(SoundType.MUD));
    public static final DeferredBlock<Block> ALTAR = registerBlockWithItem("altar", Block::new, BlockBehaviour.Properties.of()
            .strength(4f)
            .sound(SoundType.NETHERITE_BLOCK));
    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> blockCreator, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockCreator, properties);
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
        return block;
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
