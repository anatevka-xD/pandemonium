package com.anatevka.pandemonium.item;

import com.anatevka.pandemonium.component.CipherData;
import com.anatevka.pandemonium.registry.DataComponentRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class ResearchPageItem extends Item {
    public ResearchPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.getItemInHand(hand).set(DataComponentRegistry.CIPHER_DATA, new CipherData(0,
                Arrays.asList(1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)));
        return super.use(level, player, hand);
    }
}
