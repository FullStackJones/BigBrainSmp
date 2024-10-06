package net.fullstackjones.bigbrainsmpmod.menu;

import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
import net.fullstackjones.bigbrainsmpmod.item.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MoneyPouchSlot extends Slot {
    private final ItemStack coinType;

    public MoneyPouchSlot(Container container, int index, int x, int y, ItemStack coinType) {
        super(container, index, x, y);
        this.coinType = coinType;
    }

    @Override
    public int getMaxStackSize() {
        return 128;
    }
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 128;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(this.coinType.getItem()) ;
    }
}
