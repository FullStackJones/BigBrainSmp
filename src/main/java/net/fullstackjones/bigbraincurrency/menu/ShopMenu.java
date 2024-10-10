package net.fullstackjones.bigbraincurrency.menu;

import net.fullstackjones.bigbraincurrency.item.ModItems;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static net.fullstackjones.bigbraincurrency.menu.ModContainers.SHOPMENU;

public class ShopMenu  extends AbstractContainerMenu {
    protected final int inventoryColumns = 9;
    protected final int inventoryRows = 3;

    protected final int playerInventoryColumns = 9;
    protected final int playerInventoryRows = 4;

    protected final int slotSize = 18;

    protected final Inventory playerInv;
    protected final SimpleContainer shopInv;
    protected int playerInventoryStartY = 0;
    protected int ShopInventoryStartY = 0;

    public ShopMenu(int pContainerId, Inventory playerInventory) {
        this(SHOPMENU.get(), pContainerId, playerInventory);
    }

    public ShopMenu(@Nullable MenuType<?> menuType, int containerId, Inventory playerInventory) {
        super(SHOPMENU.get(), containerId);
        this.playerInv = playerInventory;
        this.shopInv =  new SimpleContainer(27);;
        addShopSaleSlots();
        addShopInventory();
        addPlayerInventory();

    }

    private void addPlayerInventory(){
        for (int k = 0; k < playerInventoryRows; k++) {
            for (int l = 0; l < playerInventoryColumns; l++) {
                if (k > 0) {
                    this.addSlot(new Slot(playerInv, l + k * inventoryColumns, 8 + l * slotSize, playerInventoryStartY + k * slotSize));
                } else {
                    this.addSlot(new Slot(playerInv, l + k * inventoryColumns, 8 + l * slotSize, playerInventoryStartY + (4 * slotSize) + 4));
                }
            }
        }
    }

    private void addShopInventory() {
        for (int k = 0; k < inventoryRows; k++) {
            for (int l = 0; l < inventoryColumns; l++) {
                this.addSlot(new Slot(shopInv, l + k * inventoryColumns, 8 + l * slotSize, ShopInventoryStartY + 3 + k * slotSize));
                playerInventoryStartY = ShopInventoryStartY + 30 + k * slotSize  - 1;
            }
        }
    }

    private void addShopSaleSlots() {
        var shopSaleSlots = new SimpleContainer(9);;
        shopSaleSlots.addItem(ModItems.COPPERCOIN.toStack());
        var saleslotY = slotSize - 2;
        this.addSlot(new Slot(shopSaleSlots, 0 , 8, saleslotY));
        for (int l = 5; l < 9; l++) {
            this.addSlot(new Slot(shopSaleSlots, l - 4, 8 + l * slotSize, saleslotY));
        }
        for (int l = 5; l < 9; l++) {
            this.addSlot(new Slot(shopSaleSlots, l, 8 + l * slotSize, saleslotY + this.slotSize + 22));
        }

        ShopInventoryStartY = 39 + saleslotY + this.slotSize;
    }
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
