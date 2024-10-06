package net.fullstackjones.bigbrainsmpmod.item;

import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
import net.fullstackjones.bigbrainsmpmod.item.custom.MoneyPouchItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.fullstackjones.bigbrainsmpmod.data.ModDataComponents.MONEYPOUCH_DATA;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BigBrainSmpMod.MODID);

    public static final DeferredItem<Item> COPPERCOIN = ITEMS.register(
            "coppercoin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SILVERCOIN = ITEMS.register(
            "silvercoin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLDCOIN = ITEMS.register(
            "goldcoin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PINKCOIN = ITEMS.register(
            "pinkcoin",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MONEY_POUCH = ITEMS.register(
            "moneypouch",
            () -> new MoneyPouchItem(new Item.Properties().component(MONEYPOUCH_DATA.value(),
                    new MoneyPouchData(4, 0, 0 ,0, 0))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
