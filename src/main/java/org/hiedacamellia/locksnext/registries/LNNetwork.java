package org.hiedacamellia.locksnext.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.network.pin2server.ModernLockPin;
import org.hiedacamellia.locksnext.core.network.resp2client.ModernLockResp;
import org.hiedacamellia.locksnext.core.network.sync.LockInfoAddSync;
import org.hiedacamellia.locksnext.core.network.sync.LockInfoRemoveSync;
import org.hiedacamellia.locksnext.core.network.sync.LockInfoUpdateSync;
import org.hiedacamellia.locksnext.core.network.sync.LockStorageAllSync;

@EventBusSubscriber(modid = LNMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public class LNNetwork {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0.0");
        registrar.playToClient(
                LockStorageAllSync.TYPE,
                LockStorageAllSync.STREAM_CODEC,
                LockStorageAllSync::handle);
        registrar.playToClient(
                LockInfoAddSync.TYPE,
                LockInfoAddSync.STREAM_CODEC,
                LockInfoAddSync::handle);
        registrar.playToClient(
                LockInfoRemoveSync.TYPE,
                LockInfoRemoveSync.STREAM_CODEC,
                LockInfoRemoveSync::handle);
        registrar.playToClient(
                LockInfoUpdateSync.TYPE,
                LockInfoUpdateSync.STREAM_CODEC,
                LockInfoUpdateSync::handle);
        registrar.playToServer(
                ModernLockPin.TYPE,
                ModernLockPin.STREAM_CODEC,
                ModernLockPin::handle);
        registrar.playToClient(
                ModernLockResp.TYPE,
                ModernLockResp.STREAM_CODEC,
                ModernLockResp::handle);


    }
}
