package org.hiedacamellia.locksnext;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.hiedacamellia.locksnext.core.config.Config;
import org.hiedacamellia.locksnext.core.config.ServerConfig;
import org.hiedacamellia.locksnext.registries.LocksNextAttachment;
import org.hiedacamellia.locksnext.registries.LocksNextMenu;
import org.slf4j.Logger;

@Mod(LocksNext.MODID)
public class LocksNext {
    public static final String MODID = "locksnext";
    private static final Logger LOGGER = LogUtils.getLogger();
    public LocksNext(IEventBus modEventBus, ModContainer modContainer) {

        LocksNextAttachment.register(modEventBus);
        LocksNextMenu.register(modEventBus);
        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
