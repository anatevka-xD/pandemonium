package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ItemLike;

import java.util.*;

public class LostPageItem extends Item {
    public LostPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        if (stack.get(DataComponentRegistry.CIPHER_DATA).targetState().get(0) == 0) {
            Collections.shuffle(stack.get(DataComponentRegistry.CIPHER_DATA).targetState());
            stack.set(
                    DataComponentRegistry.CIPHER_DATA,
                    new CipherStateComponent(
                            new ArrayList<>(CipherStateComponent.DEFAULT_LIST),
                            new ArrayList<>(stack.get(DataComponentRegistry.CIPHER_DATA).targetState())
                    ));
        }
        return super.getDefaultAttributeModifiers(stack);
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (stack.get(DataComponentRegistry.CIPHER_DATA) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).state().toString()));
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.CIPHER_DATA).targetState().toString()));
        }
        if (stack.get(DataComponentRegistry.RESEARCH_TEXT) != null) {
            tooltipComponents.add(Component.literal(stack.get(DataComponentRegistry.RESEARCH_TEXT).researchText().toString()));
        }
    }
}
