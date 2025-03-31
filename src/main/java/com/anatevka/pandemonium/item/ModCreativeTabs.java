package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Pandemonium.MODID);
    public static final Supplier<CreativeModeTab> PANDEMONIUM_TAB = CREATIVE_MODE_TABS.register("pandemonium_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SIGNUM_MALI.get()))
                    .title(Component.translatable("tab.pandemonium.pandemonium"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SIGNUM_MALI);
                        output.accept(ModItems.MANDRAKE_SEEDS);
                        output.accept(ModBlocks.FLESH_BLOCK);
                        output.accept(ModBlocks.ALTAR);
                    }).build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
