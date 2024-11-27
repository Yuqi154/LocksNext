package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.PacketDistributor;
import org.hiedacamellia.locksnext.core.network.sync.LockStorageAllSync;
import org.hiedacamellia.locksnext.registries.LNAttachment;

import java.util.ArrayList;
import java.util.List;

public record LockStorage(List<LockInfo> infos) {
    public static final Codec<LockStorage> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.list(LockInfo.CODEC).fieldOf("infos").forGetter(LockStorage::infos)
            ).apply(instance, LockStorage::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf,LockStorage> STREAM_CODEC = StreamCodec.composite(
           ByteBufCodecs.fromCodec(Codec.list(LockInfo.CODEC)), LockStorage::infos, LockStorage::new
    );

    public LockStorage(){
        this(new ArrayList<>());
    }

    public static LockStorage fromChunk(LevelChunk chunk){
        return chunk.getData(LNAttachment.LOCK_STORAGE);
    }
    public static void setChunk(LevelChunk chunk, LockStorage storage){
        chunk.setData(LNAttachment.LOCK_STORAGE, storage);
    }

    public void sync(LevelChunk chunk){
        sync(chunk.getPos());
    }
    public void sync(ChunkPos pos){
        PacketDistributor.sendToAllPlayers(new LockStorageAllSync(pos, this.infos()));
    }
}
