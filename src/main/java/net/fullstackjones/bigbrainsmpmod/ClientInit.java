package net.fullstackjones.bigbrainsmpmod;

import net.fullstackjones.bigbrainsmpmod.menu.MoneyPouchScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static net.fullstackjones.bigbrainsmpmod.menu.ModContainers.MoneyPouchMenu;

public class ClientInit {

    public static void init(IEventBus modBus) {
        modBus.addListener(ClientInit::onRegisterMenuScreens);
    }

    private static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MoneyPouchMenu.get(), MoneyPouchScreen::new);
    }
}
