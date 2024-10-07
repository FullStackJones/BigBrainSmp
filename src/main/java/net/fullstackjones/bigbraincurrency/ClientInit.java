package net.fullstackjones.bigbraincurrency;

import net.fullstackjones.bigbraincurrency.menu.MoneyPouchScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static net.fullstackjones.bigbraincurrency.menu.ModContainers.MoneyPouchMenu;

public class ClientInit {

    public static void init(IEventBus modBus) {
        modBus.addListener(ClientInit::onRegisterMenuScreens);
    }

    private static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MoneyPouchMenu.get(), MoneyPouchScreen::new);
    }
}
