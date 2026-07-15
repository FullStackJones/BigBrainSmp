package net.fullstackjones.bigbraincurrency.menu;

import net.fullstackjones.bigbraincurrency.Config;
import net.fullstackjones.bigbraincurrency.entities.BrainBankBlockEntity;
import net.fullstackjones.bigbraincurrency.registration.ModBlocks;
import net.fullstackjones.bigbraincurrency.registration.ModItems;
import net.fullstackjones.bigbraincurrency.registration.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.time.LocalDateTime;

public class BrainBankMenu extends AbstractContainerMenu {
    private static final int INVENTORY_X = 8;
    private static final int INVENTORY_Y = 68;
    private static final int INVENTORY_HOTBAR_Y = 144;
    private static final int BANK_SLOT_X = 62;
    private static final int BANK_SLOT_Y = 23;

    protected final int playerInventoryColumns = 9;
    protected final int playerInventoryRows = 4;

    protected final int slotSize = 18;

    protected final Inventory playerInventory;
    public final BrainBankBlockEntity blockEntity;
    private final Level level;
    private final Container brainBankInventory;

    private int numberOfCoins = 0;

    public BrainBankMenu(int containerId, Inventory inventory, BlockEntity brainBank) {
        super(ModMenus.BRAINBANKMENU.get(), containerId);
        this.blockEntity = ((BrainBankBlockEntity) brainBank);
        this.playerInventory = inventory;
        this.level = inventory.player.level();
        this.brainBankInventory = new SimpleContainer(1) {
            @Override
            public ItemStack removeItem(int pIndex, int pCount) {
                BrainBankMenu.this.blockEntity.setLastDistribution(LocalDateTime.now());
                return super.removeItem(pIndex, pCount);
            }

            @Override
            public void setItem(int index, ItemStack stack) {
                super.setItem(index, stack);
            }

            @Override
            public boolean canPlaceItem(int slot, ItemStack stack) {
                return false;
            }
        };

        addPlayerInventory();
        addBrainBankInventory();
        checkDistributionReady();
    }

    public BrainBankMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public void checkDistributionReady() {
        if (this.brainBankInventory.isEmpty() && blockEntity.nextDistributionAvailable()) {
            numberOfCoins = Config.DIST_AMOUNT.get();
            this.brainBankInventory.setItem(0, ModItems.COPPERCOIN.toStack(numberOfCoins));
        }
    }

    private void addPlayerInventory(){
        for (int k = 0; k < playerInventoryRows; k++) {
            for (int l = 0; l < playerInventoryColumns; l++) {
                if (k > 0) {
                    this.addSlot(
                        new Slot(
                            playerInventory,
                            l + k * playerInventoryColumns,
                            INVENTORY_X + l * slotSize,
                            INVENTORY_Y + k * slotSize
                        )
                    );
                } else {
                    this.addSlot(
                        new Slot(
                            playerInventory,
                            l,
                            INVENTORY_X + l * slotSize,
                            INVENTORY_HOTBAR_Y
                        )
                    );
                }
            }
        }
    }

    private void addBrainBankInventory(){
        this.addSlot(new Slot(brainBankInventory, 0, BANK_SLOT_X + slotSize, BANK_SLOT_Y){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {

        // We only care if pIndex is the same slot as the one we're removing from the bank.
        if (pIndex != TE_INVENTORY_FIRST_SLOT_INDEX) {
            return ItemStack.EMPTY;
        }

        // Check the item is a valid stack to remove.
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();
        if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
            return ItemStack.EMPTY;
        } else {
            // Something moved. Update the last distribution.
            BrainBankMenu.this.blockEntity.setLastDistribution(LocalDateTime.now());
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.BRAINBANK_BLOCK.get());
    }
}
