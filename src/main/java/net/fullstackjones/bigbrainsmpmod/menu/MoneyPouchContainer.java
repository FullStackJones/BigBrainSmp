package net.fullstackjones.bigbrainsmpmod.menu;

import net.fullstackjones.bigbrainsmpmod.data.ModDataComponents;
import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
import net.fullstackjones.bigbrainsmpmod.item.custom.MoneyPouchItem;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import static net.fullstackjones.bigbrainsmpmod.item.ModItems.*;
import static net.fullstackjones.bigbrainsmpmod.menu.ModContainers.MoneyPouchMenu;

public class MoneyPouchContainer extends AbstractContainerMenu {

    protected final Inventory playerInv;
    private final MoneyPouchContainerData moneyContainer;
    protected final int inventoryColumns = 9;
    protected final int inventoryRows = 4;
    protected final int slotSize = 18;


    public MoneyPouchContainer(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory,  new MoneyPouchContainerData(4,new MoneyPouchData(4,0,0,0,0)));
    }

    public MoneyPouchContainer(int id, Inventory playerInventory, MoneyPouchContainerData moneyContainer) {
        super(MoneyPouchMenu.get(), id);
        this.playerInv = playerInventory;
        this.moneyContainer = moneyContainer;

        for (int k = 0; k < inventoryRows; k++) {
            for (int l = 0; l < inventoryColumns; l++) {
                if (k > 0) {
                    this.addSlot(new Slot(playerInventory, l + k * inventoryColumns, 8 + l * slotSize, 23 + k * slotSize));
                } else {
                    this.addSlot(new Slot(playerInventory, l + k * inventoryColumns, 8 + l * slotSize, 23 + (4 * slotSize) + 4));
                }
            }
        }

        this.addSlot(new MoneyPouchSlot(moneyContainer, 0, 8, 7, PINKCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(moneyContainer, 1, 8 + (slotSize), 7, GOLDCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(moneyContainer, 2, 8 + (2 * slotSize), 7, SILVERCOIN.toStack()));
        this.addSlot(new MoneyPouchSlot(moneyContainer, 3, 8 + (3 * slotSize), 7, COPPERCOIN.toStack()));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        // The quick moved slot stack
        ItemStack quickMovedStack = ItemStack.EMPTY;
        return quickMovedStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof MoneyPouchItem) {
                ((MoneyPouchItem) stack.getItem()).setMoneyPouchData(stack, moneyContainer.getMoneyPouchData());
            }
        }
    }
}