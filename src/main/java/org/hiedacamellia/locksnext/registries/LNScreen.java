package org.hiedacamellia.locksnext.registries;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.client.screen.ModernLockScreen;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = LNMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public class LNScreen {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LNMenu.TEST_MENU.get(), ModernLockScreen::new);
    }
}
