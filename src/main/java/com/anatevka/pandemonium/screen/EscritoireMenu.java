package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.component.CipherData;
import com.anatevka.pandemonium.registry.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class EscritoireMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final ContainerData researchMaterialData;
    public static final String TITLE = "container.pandemonium.escritoire";

    public EscritoireMenu(int containerId, Inventory playerInventory) {
        this(
                containerId, playerInventory, ContainerLevelAccess.NULL, new ItemStackHandler(Escritoire.SLOT_COUNT + ResearchRegistry.REGISTRY.size() - 1),
                new SimpleContainerData(ResearchRegistry.REGISTRY.size() - 1));
    }

    public EscritoireMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, IItemHandler inventory, ContainerData researchMaterialData) {
        super(MenuRegistry.ESCRITOIRE_MENU.get(), containerId);
        this.access = access;
        this.researchMaterialData = researchMaterialData;

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (i == 0) {
                this.addSlot(new SlotItemHandler(inventory, i, 11, 48));
            } else {
                this.addSlot(new SlotItemHandler(inventory, i, 11, 48 + i * 26));
            }
        }

        this.addStandardInventorySlots(playerInventory, 36, 174);
        this.addDataSlots(researchMaterialData);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(this.access, player, BlockRegistry.ESCRITOIRE.get());
    }

    public int getResearchMaterialAmount (int i) {
        return this.researchMaterialData.get(i);
    }
}
