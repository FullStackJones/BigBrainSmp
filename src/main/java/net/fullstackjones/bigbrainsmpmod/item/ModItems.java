package net.fullstackjones.bigbrainsmpmod.item;

import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.fullstackjones.bigbrainsmpmod.item.custom.CoinItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BigBrainSmpMod.MODID);

    public static final DeferredItem<Item>[] COINS = new DeferredItem[4];
    private static final String[] CoinTypes = {"goldcoin", "silvercoin", "coppercoin", "pinkcoin"};
    private static final int[] CoinValues = {100, 75, 50, 25};

    static {
        for (int i = 0; i < CoinTypes.length; i++) {
            String coinType = CoinTypes[i];
            int coinValue = CoinValues[i];
            COINS[i] = ITEMS.register(
                    coinType,
                    () -> new Item(new CoinItem.Properties().coinType(coinType).coinValue(coinValue)));
        }
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
