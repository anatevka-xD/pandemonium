package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Pandemonium.MODID);
    public static final DeferredItem<Item> SIGNUM_MALI = ITEMS.registerItem("signum_mali", properties -> new Item(properties));
    public static final DeferredItem<Item> MANDRAKE_SEEDS = ITEMS.registerItem("mandrake_seeds", properties -> new Item(properties));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
