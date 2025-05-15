package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherStateComponent;
import com.anatevka.pandemonium.component.ResearchTextComponent;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import com.anatevka.pandemonium.registry.ItemRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LostPageItem extends Item {
    public LostPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ItemRegistry.LOST_PAGE) && stack.get(DataComponentRegistry.CIPHER_DATA).targetState().isEmpty()) {
            List<Integer> randomCipherState = new ArrayList<>(CipherStateComponent.DEFAULT_LIST);
            Collections.shuffle(randomCipherState, new Random());
            stack.set(DataComponentRegistry.CIPHER_DATA, new CipherStateComponent(
                    new ArrayList<>(CipherStateComponent.DEFAULT_LIST), randomCipherState));
        }
        List<Integer> cipherState = stack.get(DataComponentRegistry.CIPHER_DATA).targetState();
        List<String> researchText = stack.get(DataComponentRegistry.RESEARCH_TEXT).researchText();

        for (int i = 0; i < researchText.size(); i++) {
            researchText.set(i, ResearchTextComponent.encryptString(researchText.get(i), cipherState));
        }

        return super.use(level, player, hand);
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
