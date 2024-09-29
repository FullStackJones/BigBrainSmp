package net.fullstackjones.bigbrainsmpmod.item;

import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.fullstackjones.bigbrainsmpmod.Config;
import net.fullstackjones.bigbrainsmpmod.item.custom.CoinItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BigBrainSmpMod.MODID);
    public static final DeferredItem<Item>[] COINS = new DeferredItem[4];

    static {
        final String[] CoinTypes = new String[]{"coppercoin", "silvercoin", "goldcoin", "pinkcoin"};
        // todo: config file values are always null need fix this.
        final int[] CoinValues = Config.coinValues != null ? Config.coinValues : new int[]{1, 9, 81, 729};

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
