package com.anatevka.pandemonium.registry;

import com.anatevka.pandemonium.Pandemonium;
import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public final class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Pandemonium.MODID);
    public static final DeferredItem<Item> SIGNUM_MALI = ITEMS.registerItem("signum_mali", Item::new);
    public static final DeferredItem<Item> MANDRAKE_SEEDS = ITEMS.registerItem("mandrake_seeds", Item::new);
    public static final DeferredItem<Item> GRIMOIRE = ITEMS.registerItem("grimoire", Item::new);
    public static final DeferredItem<Item> GARGOYLE_STATUE = ITEMS.registerItem("gargoyle_statue", properties ->
            new GargoyleStatueItem(BlockRegistry.GARGOYLE_STATUE.get(), properties));
    public static final DeferredItem<Item> STONE_CHEST = ITEMS.registerItem("stone_chest", properties ->
            new StoneChestItem(BlockRegistry.STONE_CHEST.get(), properties));
    public static final DeferredItem<Item> ESCRITOIRE = ITEMS.registerItem("escritoire", properties ->
            new EscritoireItem(BlockRegistry.ESCRITOIRE.get(), properties));
    public static final DeferredItem<ResearchPageItem> RESEARCH_PAGE = ITEMS.register("research_page",
            registryName -> new ResearchPageItem(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, registryName))
                            .component(DataComponentRegistry.RESEARCH_TEXT.get(), new ResearchTextComponent(new ArrayList<>(ResearchTextComponent.DEFAULT_TEXT)))
                            .component(DataComponentRegistry.CIPHER_DATA.get(), new CipherStateComponent(new ArrayList<>(CipherStateComponent.DEFAULT_LIST), List.of()))
            )
    );
    public static final DeferredItem<LostPageItem> LOST_PAGE = ITEMS.register("lost_page",
            registryName -> new LostPageItem(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, registryName))
                            .component(DataComponentRegistry.RESEARCH_TEXT.get(), new ResearchTextComponent(new ArrayList<>(ResearchTextComponent.DEFAULT_TEXT)))
                            .component(DataComponentRegistry.CIPHER_DATA.get(), new CipherStateComponent(new ArrayList<>(CipherStateComponent.DEFAULT_LIST), List.of()))
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
