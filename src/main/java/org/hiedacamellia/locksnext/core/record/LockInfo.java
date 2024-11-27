package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.hiedacamellia.locksnext.registries.LNAttachment;

public record LockInfo(Cuboid6i lockedArea, LockedFace lockedFace, Lock lock, ItemStack itemStack) {
    public static final Codec<LockInfo> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Cuboid6i.CODEC.fieldOf("cuboid6i").forGetter(LockInfo::lockedArea),
                    LockedFace.CODEC.fieldOf("lockedFace").forGetter(LockInfo::lockedFace),
                    Lock.CODEC.fieldOf("lock").forGetter(LockInfo::lock),
                    ItemStack.CODEC.fieldOf("itemStack").forGetter(LockInfo::itemStack)
            ).apply(instance, LockInfo::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf,LockInfo> STREAM_CODEC = StreamCodec.composite(
            Cuboid6i.STREAM_CODEC, LockInfo::lockedArea,
            LockedFace.STREAM_CODEC, LockInfo::lockedFace,
            Lock.STREAM_CODEC, LockInfo::lock,
            ItemStack.STREAM_CODEC, LockInfo::itemStack,
            LockInfo::new
    );

    public LockInfo(){
        this(new Cuboid6i(), new LockedFace(), new Lock(0L,false,"modern"), ItemStack.EMPTY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LockInfo lockInfo = (LockInfo) obj;
        return lockedArea.equals(lockInfo.lockedArea) && lockedFace.equals(lockInfo.lockedFace) && lock.equals(lockInfo.lock) && ItemStack.isSameItem(itemStack,lockInfo.itemStack);
    }

    public static LockInfo fromEntity(Entity entity){
        return entity.getData(LNAttachment.LOCK_INFO);
    }

    public static void setEntity(Entity entity, LockInfo lockInfo){
        entity.setData(LNAttachment.LOCK_INFO,lockInfo);
    }
}
