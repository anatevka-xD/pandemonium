package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherState;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ResearchPageItem extends Item {
    public ResearchPageItem(Properties properties) {
        this(properties, CipherState.DEFAULT_LIST);
    }
    public ResearchPageItem(Properties properties, List<Integer> cipherState) {
        super(properties.component(DataComponentRegistry.CIPHER_DATA.get(), new CipherState(CipherState.DEFAULT_LIST)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (stack.get(DataComponentRegistry.CIPHER_DATA.get()) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).cipherState().toString()));
        }
    }
}
