package org.hiedacamellia.locksnext.core.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.hiedacamellia.locksnext.LocksNext;
import org.hiedacamellia.locksnext.client.screen.TestScreen;
import org.hiedacamellia.locksnext.registries.LocksNextMenu;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = LocksNext.MODID, bus = EventBusSubscriber.Bus.MOD)
public class LocksNextEvent {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LocksNextMenu.TEST_MENU.get(), TestScreen::new);
    }
}
