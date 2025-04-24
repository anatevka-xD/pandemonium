package com.anatevka.pandemonium.research;

import com.anatevka.pandemonium.registry.ResearchRegistry;

public final class ResearchMaterialStack {
    private int amount;
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
}
