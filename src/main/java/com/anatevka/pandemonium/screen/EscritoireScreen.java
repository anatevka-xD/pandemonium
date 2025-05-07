package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.component.CipherData;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.registry.TagRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EscritoireScreen extends AbstractContainerScreen<EscritoireMenu> {
    private int cipherSlot;
    private List<Integer> cipherState;
    public EscritoireScreen(EscritoireMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = Images.ESCRITOIRE_GUI.getWidth();
        this.imageHeight = Images.ESCRITOIRE_GUI.getHeight();
        this.leftPos = Images.ESCRITOIRE_GUI.getLeftPos(this.width);
        this.topPos = Images.ESCRITOIRE_GUI.getTopPos(this.height);
        this.cipherSlot = 0;
        this.getCipherState();
        System.out.println(this.menu.getSlot(0).getItem());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        this.renderGUI(guiGraphics);
        this.renderResearch(guiGraphics);
        this.renderMaterialSlots(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public boolean keyPressed(int key, int scancode, int mods) {
        if (key == InputConstants.KEY_LEFT) {
            this.cipherSlot = Math.clamp(this.cipherSlot-1, 0, 25);
        } else if (key == InputConstants.KEY_RIGHT) {
            this.cipherSlot = Math.clamp(this.cipherSlot+1, 0, 25);
        } else if (key == InputConstants.KEY_UP) {
            cipherState.set(this.cipherSlot, cipherState.get(this.cipherSlot)-1 < 0 ? 25 : cipherState.get(this.cipherSlot)-1);
        }else if (key == InputConstants.KEY_DOWN) {
            cipherState.set(this.cipherSlot, cipherState.get(this.cipherSlot)+1 > 25 ? 0 : cipherState.get(this.cipherSlot)+1);
        }
        return super.keyPressed(key, scancode, mods);
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        System.out.println(this.menu.getSlot(0).getItem());
        if (slot != null && slot.index == 0) {
            this.getCipherState();
        }
    }

    private void getCipherState(){
        if(this.menu.getSlot(0).getItem().is(ItemRegistry.RESEARCH_PAGE)) {
            this.cipherState = new ArrayList<>(this.menu.getSlot(0).getItem().get(DataComponentRegistry.CIPHER_DATA).cipherState());
        } else {
            this.cipherState = new ArrayList<>(CipherData.DEFAULT_LIST);
        }
    }

    private void renderGUI(GuiGraphics guiGraphics){
        guiGraphics.blit(
                RenderType::guiTextured, Images.ESCRITOIRE_GUI.getImage(),
                Images.ESCRITOIRE_GUI.getLeftPos(this.width), Images.ESCRITOIRE_GUI.getTopPos(this.height),
                0f, 0f,
                Images.ESCRITOIRE_GUI.getWidth(), Images.ESCRITOIRE_GUI.getHeight(),
                256,256
        );
    }

    private void renderResearch(GuiGraphics guiGraphics){
        if (this.menu.getSlot(0).getItem().is(TagRegistry.Items.RESEARCH_TEXT)) {
            guiGraphics.blit(
                    RenderType::guiTextured, Images.LOST_PAGE.getImage(),
                    Images.LOST_PAGE.getLeftPos(this.width) - 40, Images.LOST_PAGE.getTopPos(this.height) - 66,
                    0f, 0f,
                    Images.LOST_PAGE.getWidth(), Images.LOST_PAGE.getHeight(),
                    78,106
            );
        }
        if (this.menu.getSlot(0).getItem().is(TagRegistry.Items.TRANSLATION_TEXT)) {
            guiGraphics.blit(
                    RenderType::guiTextured, Images.RESEARCH_PAGE.getImage(),
                    Images.RESEARCH_PAGE.getLeftPos(this.width) + 40, Images.RESEARCH_PAGE.getTopPos(this.height) - 66,
                    0f, 0f,
                    Images.RESEARCH_PAGE.getWidth(), Images.RESEARCH_PAGE.getHeight(),
                    78,106
            );
        }
        if (this.menu.getSlot(0).getItem().is(TagRegistry.Items.WANTS_CIPHER)) {
            guiGraphics.blit(
                    RenderType::guiTextured, Images.RESEARCH_CIPHER.getImage(),
                    Images.RESEARCH_CIPHER.getLeftPos(this.width), Images.RESEARCH_CIPHER.getTopPos(this.height) + 14,
                    0f, 0f,
                    Images.RESEARCH_CIPHER.getWidth(), Images.RESEARCH_CIPHER.getHeight(),
                    168,54
            );
            guiGraphics.blit(
                    RenderType::guiTextured, Images.CIPHER_SLOT.getImage(),
                    Images.CIPHER_SLOT.getLeftPos(this.width) - 75 + this.cipherSlot * 6, Images.CIPHER_SLOT.getTopPos(this.height) + 15,
                    0f, 0f,
                    Images.CIPHER_SLOT.getWidth(), Images.CIPHER_SLOT.getHeight(),
                    8,54
            );
            guiGraphics.blit(
                    RenderType::guiTextured, Images.RUNES.getImage(),
                    Images.RUNES.getLeftPos(this.width), Images.RUNES.getTopPos(this.height) - 7,
                    0f, 0f,
                    Images.RUNES.getWidth(), Images.RUNES.getHeight(),
                    156,8
            );
            for (int i = 0; i < 26; i++) {
                guiGraphics.blit(
                        RenderType::guiTextured, Images.TEXT_SCROLLER.getImage(),
                        Images.TEXT_SCROLLER.getLeftPos(this.width) - 75 + i * 6, Images.TEXT_SCROLLER.getTopPos(this.height) + 20,
                        this.cipherState.get(i) * 6f, 0f,
                        Images.TEXT_SCROLLER.getWidth(), Images.TEXT_SCROLLER.getHeight(),
                        156,42
                );
            }
        }
    }

    private void renderMaterialSlots(GuiGraphics guiGraphics) {
        ResearchRegistry.RESEARCH_MATERIALS.getEntries().forEach(
                (m) -> {
                    if(m.get().index() >= 0) {
                        guiGraphics.blit(
                                RenderType::guiTextured, this.menu.getSlot(0).getItem().is(m.get().requirementTag()) ? m.get().icon() : m.get().unusedIcon(),
                                Images.SLOT.getLeftPos(this.width) - 97, Images.SLOT.getTopPos(this.height) - 46 + m.get().index() * 26,
                                0f, 0f,
                                Images.SLOT.getWidth(), Images.SLOT.getHeight(),
                                18, 18
                        );
                        guiGraphics.blit(
                                RenderType::guiTextured, Images.MATERIAL_BAR.getImage(),
                                Images.MATERIAL_BAR.getLeftPos(this.width) - 97, Images.MATERIAL_BAR.getTopPos(this.height) - 34 + m.get().index() * 26,
                                0f, 4f,
                                Images.MATERIAL_BAR.getWidth(), Images.MATERIAL_BAR.getHeight(),
                                26, 8, m.get().color()
                        );
                        guiGraphics.blit(
                                RenderType::guiTextured, Images.MATERIAL_BAR.getImage(),
                                Images.MATERIAL_BAR.getLeftPos(this.width) - 97, Images.MATERIAL_BAR.getTopPos(this.height) - 34 + m.get().index() * 26,
                                0f, 0f,
                                Images.MATERIAL_BAR.getWidth()*this.menu.getResearchMaterialAmount(m.get().index())/100, Images.MATERIAL_BAR.getHeight(),
                                26, 8, m.get().color()
                        );
                    }
                }
        );
    }
}
