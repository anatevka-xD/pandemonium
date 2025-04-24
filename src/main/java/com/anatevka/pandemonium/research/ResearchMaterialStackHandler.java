package com.anatevka.pandemonium.research;

import com.anatevka.pandemonium.registry.ResearchRegistry;
import net.minecraft.core.NonNullList;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Iterator;

public class ResearchMaterialStackHandler {
    protected NonNullList<ResearchMaterialStack> stacks;

    public ResearchMaterialStackHandler() {
        this(1);
    }
    public ResearchMaterialStackHandler(NonNullList<ResearchMaterialStack> stacks) {
        this.stacks = stacks;
    }
    public ResearchMaterialStackHandler(int size) {
        stacks = NonNullList.withSize(size, ResearchMaterialStack.EMPTY);
        final Iterator<DeferredHolder<ResearchMaterial,
                                ? extends ResearchMaterial>> researchMaterialIterator = ResearchRegistry.RESEARCH_MATERIALS.getEntries().iterator();
        while (researchMaterialIterator.hasNext()) {
            ResearchMaterial researchMaterial = researchMaterialIterator.next().get();
            if (researchMaterial.index() >= 0) {
                if (getStackInSlot(researchMaterial.index()).getResearchMaterial() == ResearchRegistry.EMPTY.get()) {
                    setStackInSlot(researchMaterial.index(), new ResearchMaterialStack(0, researchMaterial));
                }
            }
        }
    }

    public void setSize(int size) {
        this.stacks = NonNullList.withSize(size, ResearchMaterialStack.EMPTY);
    }
    public int getSize() {
        return this.stacks.size();
    }

    public ResearchMaterialStack getStackInSlot(int slot) {
        return (ResearchMaterialStack) this.stacks.get(slot);
    }
    public ResearchMaterial getStackMaterial(int slot) {
        return (ResearchMaterial) this.stacks.get(slot).getResearchMaterial();
    }
    public void setStackInSlot(int i, ResearchMaterialStack stack) {
        stacks.set(i, stack);
    }

    public void increaseStackSize(int i, int amount) {this.stacks.get(i).increaseAmount(amount);}
    public void decreaseStackSize(int i, int amount) {this.stacks.get(i).decreaseAmount(amount);}

    public void setStackSize(int i, int amount) {this.stacks.get(i).setAmount(amount);}
    public int getStackSize(int i) {return this.stacks.get(i).getAmount();}
}
