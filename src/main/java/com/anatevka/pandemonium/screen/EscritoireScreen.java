package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.network.CipherData;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.registry.TagRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EscritoireScreen extends AbstractContainerScreen<EscritoireMenu> {
    private static final Style ENCHANTING_TABLE = Style.EMPTY.withFont(Minecraft.ALT_FONT);
    private int cipherSlot;
    private List<Integer> cipherState;
    public EscritoireScreen(EscritoireMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = Images.ESCRITOIRE_GUI.getWidth();
        this.imageHeight = Images.ESCRITOIRE_GUI.getHeight();
        this.leftPos = Images.ESCRITOIRE_GUI.getLeftPos(this.width);
        this.topPos = Images.ESCRITOIRE_GUI.getTopPos(this.height);
        this.cipherSlot = 0;
        this.cipherState = Arrays.asList(-1);
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        if (slotId == 0) {
            this.cipherState.set(0, -1);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if(this.menu.getSlot(0).getItem().is(ItemRegistry.RESEARCH_PAGE)) {
            if (this.cipherState.get(0) == -1) {
                this.cipherState = new ArrayList<>(this.menu.getSlot(0).getItem().get(DataComponentRegistry.CIPHER_DATA).cipherState());
            }
        } else {
            this.cipherState.set(0, -1);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        this.renderGUI(guiGraphics);
        this.renderResearch(guiGraphics);
        this.renderTranslation(guiGraphics);
        this.renderCipher(guiGraphics);
        this.renderMaterialSlots(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public boolean keyPressed(int key, int scancode, int mods) {
        switch (key) {
            case InputConstants.KEY_LEFT:
                this.cipherSlot = Math.clamp(this.cipherSlot-1, 0, 25);
                break;
            case InputConstants.KEY_RIGHT:
                this.cipherSlot = Math.clamp(this.cipherSlot+1, 0, 25);
                break;
            case InputConstants.KEY_UP:
                cipherState.set(this.cipherSlot, cipherState.get(this.cipherSlot)-1 < 0 ? 25 : cipherState.get(this.cipherSlot)-1);
                break;
            case InputConstants.KEY_DOWN:
                cipherState.set(this.cipherSlot, cipherState.get(this.cipherSlot)+1 > 25 ? 0 : cipherState.get(this.cipherSlot)+1);
                break;
            case InputConstants.KEY_RETURN:
                PacketDistributor.sendToServer(
                        new CipherData(BlockPos.containing(this.menu.posData.get(0), this.menu.posData.get(1), this.menu.posData.get(2)), this.cipherState));
        }
        return super.keyPressed(key, scancode, mods);
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
        //Render Lost Page
        if (this.menu.getSlot(0).getItem().is(TagRegistry.Items.RESEARCH_TEXT)) {
            guiGraphics.blit(
                    RenderType::guiTextured, Images.LOST_PAGE.getImage(),
                    Images.LOST_PAGE.getLeftPos(this.width) - 40, Images.LOST_PAGE.getTopPos(this.height) - 66,
                    0f, 0f,
                    Images.LOST_PAGE.getWidth(), Images.LOST_PAGE.getHeight(),
                    78,106
            );

            //Render Research Text
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5F, 0.5F, 0.5F);
            List<String> researchText = this.menu.getSlot(0).getItem().get(DataComponentRegistry.RESEARCH_TEXT).researchText();
            int x = Images.LOST_PAGE.getLeftPos(this.width*2) - 114;
            int y = Images.LOST_PAGE.getTopPos(this.height*2) - 180;

            for (int i = 0; i < researchText.size(); i++) {
                //Split into lines that will fit onto the page
                List<FormattedText> splitText = font.getSplitter().splitLines(researchText.get(i),147, ENCHANTING_TABLE);
                for (int j = 0; j < splitText.size(); j++) {
                    guiGraphics.drawString(font, Component.literal(splitText.get(j).getString()).withStyle(ENCHANTING_TABLE), x, y, 0x665A48, false);
                    y += 9;
                }
            }
            guiGraphics.pose().popPose();
        }
    }

    private void renderTranslation(GuiGraphics guiGraphics) {
        //Render Translation Page
        if (this.menu.getSlot(0).getItem().is(TagRegistry.Items.TRANSLATION_TEXT)) {
            guiGraphics.blit(
                    RenderType::guiTextured, Images.RESEARCH_PAGE.getImage(),
                    Images.RESEARCH_PAGE.getLeftPos(this.width) + 40, Images.RESEARCH_PAGE.getTopPos(this.height) - 66,
                    0f, 0f,
                    Images.RESEARCH_PAGE.getWidth(), Images.RESEARCH_PAGE.getHeight(),
                    78,106
            );
            //Render Translation Text
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(0.5F, 0.5F, 0.5F);
            List<String> researchText = this.menu.getSlot(0).getItem().get(DataComponentRegistry.RESEARCH_TEXT).researchText();
            int x = Images.RESEARCH_PAGE.getLeftPos(this.width*2) + 48;
            int y = Images.RESEARCH_PAGE.getTopPos(this.height*2) - 180;

            for (int i = 0; i < researchText.size(); i++) {
                //Split into lines that will fit onto the page
                List<FormattedCharSequence> splitText = font.split(FormattedText.of(researchText.get(i)), 147);
                for (int j = 0; j < splitText.size(); j++) {
                    guiGraphics.drawString(font, splitText.get(j), x, y, 0x000000, false);
                    y += 9;
                }
            }
            guiGraphics.pose().popPose();
        }
    }

    private void renderCipher(GuiGraphics guiGraphics) {
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
                                Images.SLOT.getLeftPos(this.width) + 97, Images.SLOT.getTopPos(this.height) - 46 + (m.get().index()-1) * 26,
                                0f, 0f,
                                Images.SLOT.getWidth(), Images.SLOT.getHeight(),
                                18, 18
                        );
                        guiGraphics.blit(
                                RenderType::guiTextured, Images.MATERIAL_BAR.getImage(),
                                Images.MATERIAL_BAR.getLeftPos(this.width) + 97, Images.MATERIAL_BAR.getTopPos(this.height) - 34 + (m.get().index()-1) * 26,
                                0f, 4f,
                                Images.MATERIAL_BAR.getWidth(), Images.MATERIAL_BAR.getHeight(),
                                26, 8, m.get().color()
                        );
                        guiGraphics.blit(
                                RenderType::guiTextured, Images.MATERIAL_BAR.getImage(),
                                Images.MATERIAL_BAR.getLeftPos(this.width) + 97, Images.MATERIAL_BAR.getTopPos(this.height) - 34 + (m.get().index()-1) * 26,
                                0f, 0f,
                                Images.MATERIAL_BAR.getWidth()*this.menu.getResearchMaterialAmount(m.get().index())/100, Images.MATERIAL_BAR.getHeight(),
                                26, 8, m.get().color()
                        );
                    }
                }
        );
    }
}
