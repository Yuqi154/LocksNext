package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public record Lock(long id, boolean isLocked, byte[] combo,byte length) {

    public static final Codec<Lock> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.LONG.fieldOf("id").forGetter(Lock::id),
                    Codec.BOOL.fieldOf("isLocked").forGetter(Lock::isLocked),
                    Codec.list(Codec.BYTE).fieldOf("combo").forGetter(Lock::comboList),
                    Codec.BYTE.fieldOf("length").forGetter(Lock::length)
            ).apply(instance, Lock::new)
    );

    public static final StreamCodec<ByteBuf,Lock> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, Lock::id,
            ByteBufCodecs.BOOL, Lock::isLocked,
            ByteBufCodecs.BYTE_ARRAY, Lock::combo,
            ByteBufCodecs.BYTE, Lock::length,
            Lock::new
    );

    public Lock(long id, boolean isLocked, List<Byte> combo, byte length) {
        this(id, isLocked, comboListToArray(combo), length);
    }

    public List<Byte> comboList() {
        List<Byte> list = new ArrayList<>();
        for (byte b : combo) {
            list.add(b);
        }
        return list;
    }

    public static byte[] comboListToArray(List<Byte> list) {
        byte[] array = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }


    public static Lock create(long id, byte length) {
        RandomSource randomSource = RandomSource.create(id);
        byte[] combo = new byte[length];
        for(byte i=0;i<length;i++){
            combo[i]=i;
        }
        for(byte i=0;i<length;i++){
            byte j = (byte) randomSource.nextInt(length);
            byte temp = combo[i];
            combo[i] = combo[j];
            combo[j] = temp;
        }
        return new Lock(id, true, combo, length);
    }
}
