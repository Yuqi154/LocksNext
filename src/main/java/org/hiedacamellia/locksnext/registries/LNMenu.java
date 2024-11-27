package org.hiedacamellia.locksnext.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.common.menu.*;

public class LNMenu {
    private static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(BuiltInRegistries.MENU, LNMain.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ModernLockMenu>> MODERN = MENU_TYPE.register("modern",  () -> IMenuTypeExtension.create(ModernLockMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ClassicLockMenu>> CLASSIC = MENU_TYPE.register("classic",  () -> IMenuTypeExtension.create(ClassicLockMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<SimpleLockMenu>> SIMPLE = MENU_TYPE.register("simple",  () -> IMenuTypeExtension.create(SimpleLockMenu::new));

    public static void register(IEventBus modEventBus) {
        MENU_TYPE.register(modEventBus);
    }
}
