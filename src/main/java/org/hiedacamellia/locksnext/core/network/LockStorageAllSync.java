package org.hiedacamellia.locksnext.core.network;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.record.LockStorage;

import java.util.List;

public record LockStorageAllSync(int x,int y, List<LockInfo> infos) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf,LockStorageAllSync> STREAM_CODEC =StreamCodec.composite(
            ByteBufCodecs.INT,LockStorageAllSync::x,
            ByteBufCodecs.INT,LockStorageAllSync::y,
            ByteBufCodecs.fromCodec(Codec.list(LockInfo.CODEC)),LockStorageAllSync::infos,
            LockStorageAllSync::new
    );

    public LockStorageAllSync(ChunkPos pos, List<LockInfo> infos) {
        this(pos.x, pos.z, infos);
    }

    public static final Type<LockStorageAllSync> TYPE = new Type<>(LNMain.loc("lock_storage_all_sync"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LockStorageAllSync packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            LevelChunk chunk = level.getChunk(packet.x, packet.y);
            LockStorage.setChunk(chunk,new LockStorage(packet.infos()));
        });
    }
}
