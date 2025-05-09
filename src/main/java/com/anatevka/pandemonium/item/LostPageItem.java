package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class LostPageItem extends Item {
    public LostPageItem(Properties properties) {
        this(properties, new ArrayList<>(CipherStateComponent.DEFAULT_LIST), ResearchTextComponent.DEFAULT_TEXT);
    }

    public LostPageItem(Properties properties, List<Integer> cipherState, List<String> researchText) {
        super(properties.component(DataComponentRegistry.RESEARCH_TEXT.get(), new ResearchTextComponent(researchText)));
    }

    /*@Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (stack.get(DataComponentRegistry.CIPHER_DATA) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).cipherState().toString()));
        }
        if (stack.get(DataComponentRegistry.RESEARCH_TEXT) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.RESEARCH_TEXT).researchText().toString()));
        }
    }*/
}
