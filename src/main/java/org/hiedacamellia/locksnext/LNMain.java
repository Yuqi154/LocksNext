package org.hiedacamellia.locksnext;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.hiedacamellia.locksnext.core.config.ServerConfig;
import org.hiedacamellia.locksnext.registries.LNAttachment;
import org.hiedacamellia.locksnext.registries.LNMenu;
import org.slf4j.Logger;

@Mod(LNMain.MODID)
public class LNMain {
    public static final String MODID = "locksnext";
    private static final Logger LOGGER = LogUtils.getLogger();
    public LNMain(IEventBus modEventBus, ModContainer modContainer) {

        LNAttachment.register(modEventBus);
        LNMenu.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
