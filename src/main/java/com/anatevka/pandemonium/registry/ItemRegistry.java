package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.item.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.jar.Attributes;

public final class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Pandemonium.MODID);
    public static final DeferredItem<Item> SIGNUM_MALI = ITEMS.registerItem("signum_mali", Item::new);
    public static final DeferredItem<Item> MANDRAKE_SEEDS = ITEMS.registerItem("mandrake_seeds", Item::new);
    public static final DeferredItem<Item> GRIMOIRE = ITEMS.registerItem("grimoire", Item::new);
    public static final DeferredItem<Item> GARGOYLE_STATUE = ITEMS.registerItem("gargoyle_statue", properties -> new GargoyleStatueItem(BlockRegistry.GARGOYLE_STATUE.get(), properties));
    public static final DeferredItem<Item> STONE_CHEST = ITEMS.registerItem("stone_chest", properties -> new StoneChestItem(BlockRegistry.STONE_CHEST.get(), properties));
    public static final DeferredItem<Item> ESCRITOIRE = ITEMS.registerItem("escritoire", properties -> new EscritoireItem(BlockRegistry.ESCRITOIRE.get(), properties));
    public static final DeferredItem<ResearchPageItem> RESEARCH_PAGE = ITEMS.registerItem("research_page", ResearchPageItem::new);
    public static final DeferredItem<LostPageItem> LOST_PAGE = ITEMS.registerItem("lost_page", LostPageItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
