package org.hiedacamellia.locksnext.core.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.util.LevelUtil;

public record LockInfoRemoveSync(LockInfo lockInfo) implements CustomPacketPayload {

    public static final StreamCodec<RegistryFriendlyByteBuf,LockInfoRemoveSync> STREAM_CODEC = StreamCodec.composite(
            LockInfo.STREAM_CODEC,LockInfoRemoveSync::lockInfo,
            LockInfoRemoveSync::new
    );

    public static final CustomPacketPayload.Type<LockInfoRemoveSync> TYPE = new CustomPacketPayload.Type<>(LNMain.loc("lock_remove"));
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LockInfoRemoveSync packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            LevelUtil.removeLockInfo(level,packet.lockInfo());
        });
    }
}
