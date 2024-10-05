package net.fullstackjones.bigbrainsmpmod.menu;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MoneyPouchSlot extends SlotItemHandler {
    private final int maxStackSize;
    private final ItemStack itemType;

    public MoneyPouchSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, int maxStackSize, ItemStack itemType) {
        super(itemHandler, index, xPosition, yPosition);
        this.maxStackSize = maxStackSize;
        this.itemType = itemType;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return this.maxStackSize;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ItemStack.isSameItem(stack, this.itemType);
    }

    public ItemStack getItemType() {
        return itemType;
    }
}
