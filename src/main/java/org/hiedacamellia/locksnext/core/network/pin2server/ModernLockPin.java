package org.hiedacamellia.locksnext.core.network.pin2server;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.core.network.resp2client.ModernLockResp;
import org.hiedacamellia.locksnext.core.record.Lock;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.util.LevelUtil;
import org.hiedacamellia.locksnext.core.util.LineUtil;

public record ModernLockPin(BlockPos blockPos, float angle, float rotated) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, ModernLockPin> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ModernLockPin::blockPos,
            ByteBufCodecs.FLOAT, ModernLockPin::angle,
            ByteBufCodecs.FLOAT, ModernLockPin::rotated,
            ModernLockPin::new
    );

    public static final Type<ModernLockPin> TYPE = new Type<>(LNMain.loc("modern_pin"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ModernLockPin packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player instanceof ServerPlayer serverPlayer) {
                ServerLevel serverLevel = serverPlayer.serverLevel();
                LockInfo lockInfo = LevelUtil.getLocked(serverLevel, packet.blockPos());
                Lock lock = lockInfo.lock();
                if (lock.lockType() != LockType.MODERN) return;
                double pickAngle = lock.extraData().getDouble("angle");
                double available = LineUtil.getAvailable(Math.abs(pickAngle - packet.angle));
                boolean broke = false;
                if (packet.rotated >= available)
                    broke = RandomSource.create(lock.id()).nextDouble() < 0.1;
                boolean open = packet.rotated > 180.0;
                context.listener().send(new ModernLockResp(open, broke, (float) available));
            }
        });
    }

}
