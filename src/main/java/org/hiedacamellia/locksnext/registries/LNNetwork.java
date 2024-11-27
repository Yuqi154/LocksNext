package org.hiedacamellia.locksnext.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.network.LockStorageAllSync;

@EventBusSubscriber(modid = LNMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public class LNNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");
        registrar.playToClient(
                LockStorageAllSync.TYPE,
                LockStorageAllSync.STREAM_CODEC,
                LockStorageAllSync::handle);


    }
}
