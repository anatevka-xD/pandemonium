package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import com.anatevka.pandemonium.registry.BlockRegistry;
import com.anatevka.pandemonium.registry.MenuRegistry;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.research.EscritoireSyncData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class EscritoireMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final ContainerData data;

    public static final String TITLE = "container.pandemonium.escritoire";

    public static EscritoireMenu getClientMenu(int id, Inventory playerInventory) {
        return new EscritoireMenu(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2 + ResearchRegistry.REGISTRY.size()-1), new SimpleContainerData(ResearchRegistry.REGISTRY.size()-1));
    }

    public static MenuProvider getServerMenuProvider(EscritoireBlockEntity te, BlockPos activationPos) {
        return new SimpleMenuProvider(
                 (id, playerInventory, serverPlayer) -> new EscritoireMenu(id, playerInventory, activationPos, te.itemStackHandler, new EscritoireSyncData(te)),
                Component.translatable(TITLE));
    }

    protected EscritoireMenu(int containerId, Inventory playerInv, BlockPos pos, IItemHandler inv, ContainerData data) {
        super(MenuRegistry.ESCRITOIRE_MENU.get(), containerId);
        final Player player = playerInv.player;

        this.access = ContainerLevelAccess.create(player.level(), pos);
        this.data = data;

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        this.addSlot(new SlotItemHandler(inv, Escritoire.inputSlotIndex, -17, 3));
        this.addSlot(new SlotItemHandler(inv, Escritoire.outputSlotIndex, 177, 3));
        for (int i = 0; i<ResearchRegistry.RESEARCH_MATERIALS.getEntries().size()-1; i++) {

            this.addSlot(new SlotItemHandler(inv, i + Escritoire.startIndex, -17, 26 + i * 26));
        }
        this.addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }
    @Override
    public boolean stillValid(Player player) {return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.ESCRITOIRE.get());}
    private void addPlayerInventory(Inventory playerInventory) {for (int i = 0; i < 3; ++i) {for (int l = 0; l < 9; ++l) {this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 129 + i * 18));}}
    }
    private void addPlayerHotbar(Inventory playerInventory) {for (int i = 0; i < 9; ++i) {this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 187));}
    }

    public int getAmount(){
        return data.get(0);
    }
}
