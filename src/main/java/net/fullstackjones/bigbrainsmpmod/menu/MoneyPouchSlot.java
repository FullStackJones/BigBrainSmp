package net.fullstackjones.bigbrainsmpmod.menu;

import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
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
    public boolean mayPlace(ItemStack stack) {
        // Replace `ALLOWED_ITEM` with the specific item type you want to allow
        return stack.is(this.coinType.getItem()) ;
    }
}
