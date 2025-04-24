package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.research.ResearchMaterial;
import com.anatevka.pandemonium.block.Escritoire;
import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.anatevka.pandemonium.registry.TagRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Iterator;

public class EscritoireScreen extends AbstractContainerScreen<EscritoireMenu> {
    public EscritoireScreen(EscritoireMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelX = 8;
        this.titleLabelY = 6 - 1000;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 1000;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
        }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        final ItemStack inputItem = this.getMenu().getSlot(36 + Escritoire.inputSlotIndex).getItem();
        final ItemStack outputItem = this.getMenu().getSlot(36 + Escritoire.outputSlotIndex).getItem();
        final Iterator<DeferredHolder<ResearchMaterial,
                ? extends ResearchMaterial>> researchMaterialIterator = ResearchRegistry.RESEARCH_MATERIALS.getEntries().iterator();

        graphics.blit(
                RenderType::guiTextured,
                Images.ESCRITOIRE_GUI.getImage(),
                Images.ESCRITOIRE_GUI.getLeftPos(this.width), Images.ESCRITOIRE_GUI.getTopPos(this.height),
                0, 0,
                Images.ESCRITOIRE_GUI.getWidth(), Images.ESCRITOIRE_GUI.getHeight(),
                256, 256
        );
        if (inputItem.is(TagRegistry.Items.RESEARCHABLE)) {
            graphics.blitSprite(RenderType::guiTextured,
                    Images.LOST_PAGE.getImage(),
                    Images.LOST_PAGE.getLeftPos(this.width), Images.LOST_PAGE.getTopPos(this.height),
                    Images.LOST_PAGE.getWidth(), Images.LOST_PAGE.getHeight());
        }
        if (outputItem.is(TagRegistry.Items.TRANSLATION)) {
            graphics.blitSprite(RenderType::guiTextured,
                    Images.RESEARCH_PAGE.getImage(),
                    Images.RESEARCH_PAGE.getLeftPos(this.width), Images.RESEARCH_PAGE.getTopPos(this.height),
                    Images.RESEARCH_PAGE.getWidth(), Images.RESEARCH_PAGE.getHeight());
        }
        if(outputItem.is(TagRegistry.Items.WANTS_CIPHER)) {
            graphics.blitSprite(RenderType::guiTextured,
                    Images.RESEARCH_CIPHER.getImage(),
                    Images.RESEARCH_CIPHER.getLeftPos(this.width), Images.RESEARCH_CIPHER.getTopPos(this.height),
                    Images.RESEARCH_CIPHER.getWidth(), Images.RESEARCH_CIPHER.getHeight());
        }
        while (researchMaterialIterator.hasNext()) {
            ResearchMaterial researchMaterial = researchMaterialIterator.next().get();
            if (researchMaterial.index() >= 0) {
                renderMaterialSlot(graphics, researchMaterial, outputItem.is(researchMaterial.tag()), -97, -49 + researchMaterial.index() * 26);
                renderMaterialBar(graphics, this.menu.getAmount(), -97, -35 + researchMaterial.index() * 26, researchMaterial.color());
            }
        }
    }

    public void renderMaterialSlot(GuiGraphics graphics, ResearchMaterial researchMaterial, boolean used, int leftOffset, int topOffset){
        graphics.blitSprite(RenderType::guiTextured,
                used ? researchMaterial.icon() : researchMaterial.unusedIcon(),
                Images.SLOT.getLeftPos(this.width) + leftOffset, Images.SLOT.getTopPos(this.height) + topOffset,
                Images.SLOT.getWidth(), Images.SLOT.getHeight());
        graphics.blit(RenderType::guiTextured,
                used ? researchMaterial.icon() : researchMaterial.unusedIcon(),
                Images.SLOT.getLeftPos(this.width) + leftOffset, Images.SLOT.getTopPos(this.height) + topOffset,
                0,0,
                Images.SLOT.getWidth(), Images.SLOT.getHeight(),
                18, 18);
    }

    public void renderMaterialBar(GuiGraphics graphics, int fillPercent, int leftOffset, int topOffset, int color){
        graphics.blit(RenderType::guiTextured,
                Images.MATERIAL_BAR.getImage(),
                Images.MATERIAL_BAR.getLeftPos(this.width) + leftOffset, Images.MATERIAL_BAR.getTopPos(this.height) + topOffset,
                0,4,
                Images.MATERIAL_BAR.getWidth(), Images.MATERIAL_BAR.getHeight()/2,
                26, 8, color);
        graphics.blit(RenderType::guiTextured,
                Images.MATERIAL_BAR.getImage(),
                Images.MATERIAL_BAR.getLeftPos(this.width) + leftOffset, Images.MATERIAL_BAR.getTopPos(this.height) + topOffset,
                0,0,
                Images.MATERIAL_BAR.getWidth()*fillPercent/100, Images.MATERIAL_BAR.getHeight()/2,
                26, 8, color);
    }
}
