package org.hiedacamellia.locksnext.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.common.menu.TestMenu;

public class LNMenu {
    private static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(BuiltInRegistries.MENU, LNMain.MODID);
    // For some DeferredRegister<MenuType<?>> REGISTER
    public static final DeferredHolder<MenuType<?>, MenuType<TestMenu>> TEST_MENU = MENU_TYPE.register("test_menu",  () -> IMenuTypeExtension.create(TestMenu::new));

    public static void register(IEventBus modEventBus) {
        MENU_TYPE.register(modEventBus);
    }
}
