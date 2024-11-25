package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import org.hiedacamellia.locksnext.core.enums.LockType;

import java.util.ArrayList;
import java.util.List;

public record Lock(long id, boolean isLocked, LockType lockType, CompoundTag extraData) {

    public static final Codec<Lock> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.LONG.fieldOf("id").forGetter(Lock::id),
                    Codec.BOOL.fieldOf("isLocked").forGetter(Lock::isLocked),
                    Codec.STRING.fieldOf("lockType").forGetter(Lock::type),
                    CompoundTag.CODEC.fieldOf("extraData").forGetter(Lock::extraData)
            ).apply(instance, Lock::new)
    );

    public static final StreamCodec<ByteBuf, Lock> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG, Lock::id,
            ByteBufCodecs.BOOL, Lock::isLocked,
            ByteBufCodecs.STRING_UTF8, Lock::type,
            ByteBufCodecs.COMPOUND_TAG, Lock::extraData,
            Lock::new
    );

    public Lock(long id, boolean isLocked, String lockType) {
        this(id, isLocked, lockType, new CompoundTag());
    }

    public Lock(long id, boolean isLocked, String lockType, CompoundTag compoundTag) {
        this(id, isLocked, LockType.valueOf(lockType), compoundTag);
    }

    public Lock changeLocked(boolean locked) {
        return new Lock(id, locked, lockType, extraData);
    }

    public String type() {
        return lockType.getName();
    }

    public static Lock createNormal(long id, byte length) {
        return new Lock(id, true, LockType.NORMAL, summonCombo(id, length));
    }

    public static Lock createEasy(long id, byte length) {
        return new Lock(id, true, LockType.NORMAL, summonCombo(id, length));
    }

    public static Lock createHard(long id){
        RandomSource randomSource = RandomSource.create(id);
        CompoundTag extraData = new CompoundTag();
        extraData.putDouble("angle",randomSource.nextDouble()*360);
        return new Lock(id,true,LockType.HARD,extraData);
    }

    public static CompoundTag summonCombo(long id, byte length){
        RandomSource randomSource = RandomSource.create(id);
        CompoundTag extraData = new CompoundTag();
        byte[] combo = new byte[length];
        for (byte i = 0; i < length; i++) {
            combo[i] = i;
        }
        for (byte i = 0; i < length; i++) {
            byte j = (byte) randomSource.nextInt(length);
            byte temp = combo[i];
            combo[i] = combo[j];
            combo[j] = temp;
        }
        extraData.putByteArray("combo", combo);
        extraData.putByte("length", length);
        return extraData;
    }
}
