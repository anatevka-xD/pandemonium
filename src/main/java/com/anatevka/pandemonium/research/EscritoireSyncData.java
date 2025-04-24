package com.anatevka.pandemonium.research;

import com.anatevka.pandemonium.block.entity.EscritoireBlockEntity;
import net.minecraft.world.inventory.ContainerData;

public class EscritoireSyncData implements ContainerData {
    private final EscritoireBlockEntity te;

    public EscritoireSyncData(EscritoireBlockEntity te)
    {
        this.te = te;
    }

    @Override
    public int get(int i) {
        System.out.println("Oke dokey");
        return te.researchMaterialStackHandler.getStackInSlot(i).getAmount();
    }
    @Override
    public void set(int i, int i1) {
    }
    @Override
    public int getCount() {
        return 0;
    }

}
