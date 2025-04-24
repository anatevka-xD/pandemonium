package com.anatevka.pandemonium.research;

import com.anatevka.pandemonium.registry.ResearchRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.DataComponentUtil;

public final class ResearchMaterialStack {
    private int amount;
    private static final int maxAmount = 100;
    private ResearchMaterial researchMaterial;
    public static final ResearchMaterialStack EMPTY = new ResearchMaterialStack(0, ResearchRegistry.EMPTY.get());

    public ResearchMaterialStack(int amount, ResearchMaterial researchMaterial){
        this.amount = amount;
        this.researchMaterial = researchMaterial;
    }

    public ResearchMaterial getResearchMaterial() {
        return researchMaterial;
    }

    public int getAmount(){
        return amount;
    }

    public void increaseAmount(int amount) {
        this.amount = Math.clamp(this.amount + amount, 0, maxAmount);
    }
    public void decreaseAmount(int amount) {
        this.amount = Math.clamp(this.amount - amount, 0, maxAmount);
    }
    public void setAmount(int amount) {
        this.amount = Math.clamp(amount, 0, maxAmount);
    }
}
