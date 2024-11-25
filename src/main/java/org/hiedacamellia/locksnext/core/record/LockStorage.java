package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

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
}
