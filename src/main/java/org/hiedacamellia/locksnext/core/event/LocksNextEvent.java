package org.hiedacamellia.locksnext.core.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.hiedacamellia.locksnext.client.screen.TestScreen;
import org.hiedacamellia.locksnext.registries.LocksNextMenu;

@EventBusSubscriber
public class LocksNextEvent {
    @SubscribeEvent
    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LocksNextMenu.TEST_MENU.get(), TestScreen::new);
    }
}
