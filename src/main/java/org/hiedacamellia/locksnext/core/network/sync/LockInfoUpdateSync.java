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

public record LockInfoUpdateSync (LockInfo oldInfo, LockInfo newInfo)implements CustomPacketPayload  {

    public static final StreamCodec<RegistryFriendlyByteBuf,LockInfoUpdateSync> STREAM_CODEC = StreamCodec.composite(
            LockInfo.STREAM_CODEC,LockInfoUpdateSync::oldInfo,
            LockInfo.STREAM_CODEC,LockInfoUpdateSync::newInfo,
            LockInfoUpdateSync::new
    );

    public static final CustomPacketPayload.Type<LockInfoUpdateSync> TYPE = new CustomPacketPayload.Type<>(LNMain.loc("lock_update"));
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LockInfoUpdateSync packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            LevelUtil.updateLockInfo(level,packet.newInfo(),packet.oldInfo());
        });
    }
}
