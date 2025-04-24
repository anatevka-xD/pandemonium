package com.anatevka.pandemonium.research;

import net.minecraft.core.NonNullList;

public class ResearchMaterialStackHandler {
    protected NonNullList<ResearchMaterialStack> stacks;

    public ResearchMaterialStackHandler() {
        this(1);
    }

    public ResearchMaterialStackHandler(int size) {
        this.stacks = NonNullList.withSize(size, ResearchMaterialStack.EMPTY);
    }

    public ResearchMaterialStackHandler(NonNullList<ResearchMaterialStack> stacks) {
        this.stacks = stacks;
    }

    public void setSize(int size) {
        this.stacks = NonNullList.withSize(size, ResearchMaterialStack.EMPTY);
    }

    public int getSlots() {
        return this.stacks.size();
    }

    public ResearchMaterialStack getStackInSlot(int slot) {
        return (ResearchMaterialStack) this.stacks.get(slot);
    }

    public void setStackInSlot(int i, ResearchMaterialStack stack) {
        stacks.set(i, stack);
    }
}
