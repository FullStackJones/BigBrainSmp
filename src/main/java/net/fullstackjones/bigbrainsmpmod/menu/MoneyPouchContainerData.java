package net.fullstackjones.bigbrainsmpmod.menu;

import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import static net.fullstackjones.bigbrainsmpmod.item.ModItems.*;

public class MoneyPouchContainerData extends SimpleContainer {
    private MoneyPouchData moneyPouchData;

    public MoneyPouchContainerData(int size, MoneyPouchData moneyPouchData) {
        super(4);
        this.moneyPouchData = moneyPouchData;
        setItem(0, new ItemStack(PINKCOIN.get(), moneyPouchData.getPinkCoins()));
        setItem(1, new ItemStack(GOLDCOIN.get(), moneyPouchData.getGoldCoins()));
        setItem(2, new ItemStack(SILVERCOIN.get(), moneyPouchData.getSilverCoins()));
        setItem(3, new ItemStack(COPPERCOIN.get(), moneyPouchData.getCopperCoins()));
    }

    public MoneyPouchData getMoneyPouchData() {
        return moneyPouchData;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        // Update the MoneyPouchData with the new values
        moneyPouchData = moneyPouchData.withUpdatedCoins(getItem(0).getCount(), getItem(1).getCount(), getItem(2).getCount(), getItem(3).getCount());
    }

    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return 128;
    }

    @Override
    public int getMaxStackSize() {
        return 128;
    }
}
