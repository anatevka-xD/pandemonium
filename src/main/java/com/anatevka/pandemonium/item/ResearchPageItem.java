package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchPageItem extends Item {
    public ResearchPageItem(Properties properties) {
        super(properties);
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
