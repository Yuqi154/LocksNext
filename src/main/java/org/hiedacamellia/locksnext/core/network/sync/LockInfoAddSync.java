package org.hiedacamellia.locksnext.core.network.sync;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.util.LevelUtil;

public record LockInfoAddSync (LockInfo lockInfo) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf,LockInfoAddSync> STREAM_CODEC = StreamCodec.composite(
            LockInfo.STREAM_CODEC,LockInfoAddSync::lockInfo,
            LockInfoAddSync::new
    );

    public static final Type<LockInfoAddSync> TYPE = new Type<>(LNMain.loc("lock_add"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LockInfoAddSync packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            LevelUtil.addLockInfo(level,packet.lockInfo());
        });
    }
}
