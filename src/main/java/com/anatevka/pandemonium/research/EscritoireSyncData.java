package com.anatevka.pandemonium.research;

import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.ContainerData;

public class EscritoireSyncData implements ContainerData {
    private final EscritoireBlockEntity te;

    public EscritoireSyncData(EscritoireBlockEntity te)
    {
        this.te = te;
    }

    @Override
    public int get(int i) {
        return te.researchMaterialStackHandler.getStackInSlot(i).getAmount();
    }
    @Override
    public void set(int i, int amount) {
        te.researchMaterialStackHandler.setStackInSlot(i, new ResearchMaterialStack(amount, te.researchMaterialStackHandler.getStackInSlot(i).getResearchMaterial()));
    }
    @Override
    public int getCount() {
        return te.researchMaterialStackHandler.getSize();
    }
}
