package com.anatevka.pandemonium.item;

import net.minecraft.world.item.Item;

public class ResearchPageItem extends Item {
    public ResearchPageItem(Properties properties) {
        super(properties);
    }

    /*public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (stack.get(DataComponentRegistry.CIPHER_DATA) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).state().toString()));
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).targetState().toString()));
        }
        if (stack.get(DataComponentRegistry.RESEARCH_TEXT) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.RESEARCH_TEXT).researchText().toString()));
        }
    }*/
}
