package org.hiedacamellia.locksnext.registries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.record.LockStorage;

public class LNAttachment {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LNMain.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LockInfo>> LOCK_INFO = ATTACHMENT_TYPES.register(
            "lock_info", () -> AttachmentType.builder(LockInfo::new).build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LockStorage>> LOCK_STORAGE = ATTACHMENT_TYPES.register(
            "lock_storage", () -> AttachmentType.builder(()->new LockStorage()).build()
    );

    public static void register(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
