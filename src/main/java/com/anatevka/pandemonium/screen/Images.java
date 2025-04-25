package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.resources.ResourceLocation;

public class Images {
    public static final GuiImage ESCRITOIRE_GUI = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/escritoire_gui.png"),
            232, 256, 0,0);
    public static final GuiImage RESEARCH_CIPHER = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "escritoire/cipher"),
            168,54, 0, 14);
    public static final GuiImage LOST_PAGE = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "escritoire/lost_page"),
            78,106,-40,-66);
    public static final GuiImage RESEARCH_PAGE = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "escritoire/research_page"),
            78,106,40, -66);
    public static final GuiImage SLOT = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "escritoire/slot"),
            18,18,0, 0);
    public static final GuiImage SLOT_UNUSED = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "escritoire/slot_unused"),
            18,18,0, 0);
    public static final GuiImage MATERIAL_BAR = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/material_bar.png"),
            26,8,0, 0);
    public static final GuiImage MATERIAL_PAPER = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/slot_paper.png"),
            18,18,0, 0);
    public static final GuiImage MATERIAL_INK = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/slot_ink.png"),
            18,18,0, 0);
    public static final GuiImage MATERIAL_AMETHYST = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/slot_amethyst.png"),
            18,18,0, 0);
    public static final GuiImage CIPHER_SLOT = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/cipher_slot_selected.png"),
            7,54,0, 0);
    public static final GuiImage TEXT_SCROLLER = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/sprites/escritoire/text_scroller.png"),
            6,41,0, 0);


    public static class GuiImage {
        private final ResourceLocation image;
        private final int width;
        private final int height;
        private final int leftPos;
        private final int topPos;

        public GuiImage(ResourceLocation image, int width, int height, int leftPos, int topPos){
            this.image = image;
            this.width = width;
            this.height = height;
            this.leftPos = leftPos;
            this.topPos = topPos;
        }
        public ResourceLocation getImage(){return image;}
        public int getWidth(){return width;}
        public int getHeight(){return height;}
        public int getLeftPos(int screenWidth){
            return ((screenWidth - this.width) / 2) + leftPos;
        }
        public int getTopPos(int screenHeight){
            return ((screenHeight - this.height) / 2) + topPos;
        }
    }
}

