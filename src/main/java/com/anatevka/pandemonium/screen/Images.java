package com.anatevka.pandemonium.screen;

import com.anatevka.pandemonium.Pandemonium;
import net.minecraft.resources.ResourceLocation;

public class Images {
    public static final GuiImage ESCRITOIRE_GUI = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/escritoire_gui.png"),
            232, 256);
    public static final GuiImage RESEARCH_CIPHER = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/cipher.png"),
            170,54);
    public static final GuiImage LOST_PAGE = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/lost_page.png"),
            78,106);
    public static final GuiImage RESEARCH_PAGE = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/research_page.png"),
            78,106);
    public static final GuiImage SLOT = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/slot.png"),
            18,18);
    public static final GuiImage MATERIAL_BAR = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/material_bar.png"),
            18,18);
    public static final GuiImage CIPHER_SLOT = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/cipher_slot_selected.png"),
            8,54);
    public static final GuiImage TEXT_SCROLLER = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/text_scroller.png"),
            6,42);
    public static final GuiImage RUNES = new GuiImage(ResourceLocation.fromNamespaceAndPath(Pandemonium.MODID,
            "textures/gui/escritoire/runes.png"),
            156,8);


    public static class GuiImage {
        private final ResourceLocation image;
        private final int width;
        private final int height;

        public GuiImage(ResourceLocation image, int width, int height){
            this.image = image;
            this.width = width;
            this.height = height;
        }
        public ResourceLocation getImage(){return image;}
        public int getWidth(){return width;}
        public int getHeight(){return height;}
        public int getLeftPos(int screenWidth){
            return ((screenWidth - this.width) / 2);
        }
        public int getTopPos(int screenHeight){
            return ((screenHeight - this.height) / 2);
        }
    }
}