package net.fullstackjones.bigbrainsmpmod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import static net.fullstackjones.bigbrainsmpmod.item.ModItems.*;
import static net.fullstackjones.bigbrainsmpmod.menu.ModContainers.MoneyPouchMenu;

public class MoneyPouchContainer extends AbstractContainerMenu {

    protected final Inventory playerInv;
    protected final int inventoryColumns = 9;
    protected final int inventoryRows = 4;
    protected final int slotSize = 18;

    public MoneyPouchContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new ItemStackHandler(4),  new SimpleContainerData(4));
    }

    public MoneyPouchContainer(int id, Inventory playerInventory, IItemHandler dataInventory, ContainerData dataMultiple) {
        super(MoneyPouchMenu.get(), id);
        this.playerInv = playerInventory;

        for (int k = 0; k < inventoryRows; k++) {
            for (int l = 0; l < inventoryColumns; l++) {
                if(k > 0){
                    this.addSlot(new Slot(playerInventory,  l + k * inventoryColumns, 8 + l * slotSize, 23 + k * slotSize));
                }
                else{
                    this.addSlot(new Slot(playerInventory,  l + k * inventoryColumns,  8 + l * slotSize, 23 + (4 * slotSize) + 4));
                }
            }
        }

        this.addSlot(new MoneyPouchSlot(dataInventory, 0, 8, 7,128, PINKCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(dataInventory, 1, 8 + (slotSize), 7,128, GOLDCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(dataInventory, 2, 8 + (2 * slotSize), 7,128, SILVERCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(dataInventory, 3, 8 + (3 * slotSize), 7,128, COPPERCOIN.toStack()));

        this.addDataSlots(dataMultiple);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        // The quick moved slot stack
        ItemStack quickMovedStack = ItemStack.EMPTY;
        // The quick moved slot
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        // If the slot is in the valid range and the slot is not empty
        if (quickMovedSlot != null && quickMovedSlot.hasItem()) {
            // Get the raw stack to move
            ItemStack rawStack = quickMovedSlot.getItem();
            // Set the slot stack to a copy of the raw stack
            quickMovedStack = rawStack.copy();

            // If the quick move was performed on the data inventory result slot
            if (quickMovedSlotIndex == 0) {
                // Try to move the result slot into the player inventory/hotbar
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    // If cannot move, no longer quick move
                    return ItemStack.EMPTY;
                }

                // Perform logic on result slot quick move
                this.slots.get(0).onQuickCraft(rawStack, quickMovedStack);
            }
            // Else if the quick move was performed on the player inventory or hotbar slot
            else if (quickMovedSlotIndex >= 5 && quickMovedSlotIndex < 41) {
                // Try to move the inventory/hotbar slot into the data inventory input slots
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    // If cannot move and in player inventory slot, try to move to hotbar
                    if (quickMovedSlotIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            // If cannot move, no longer quick move
                            return ItemStack.EMPTY;
                        }
                    }
                    // Else try to move hotbar into player inventory slot
                    else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        // If cannot move, no longer quick move
                        return ItemStack.EMPTY;
                    }
                }
            }
            // Else if the quick move was performed on the data inventory input slots, try to move to player inventory/hotbar
            else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
                // If cannot move, no longer quick move
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                // If the raw stack has completely moved out of the slot, set the slot to the empty stack
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                // Otherwise, notify the slot that that the stack count has changed
                quickMovedSlot.setChanged();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                // If the raw stack was not able to be moved to another slot, no longer quick move
                return ItemStack.EMPTY;
            }
            // Execute logic on what to do post move with the remaining stack
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack; // Return the slot stack
    }
}