package org.hiedacamellia.locksnext.core.util;

import net.minecraft.world.entity.Entity;
import org.hiedacamellia.locksnext.core.record.Lock;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.registries.LocksNextAttachment;

public class EntityUtil {

    public static boolean checkLocked(Entity entity){
        LockInfo data = entity.getData(LocksNextAttachment.LOCK_INFO);
        Lock lock = data.lock();
        return lock.isLocked();
    }

    public static void setLock(Entity entity, Lock lock){
        LockInfo data = entity.getData(LocksNextAttachment.LOCK_INFO);
        data = new LockInfo(data.lockedArea(),data.lockedFace(),lock,data.itemStack());
        entity.setData(LocksNextAttachment.LOCK_INFO,data);
    }

    public static void setLock(Entity entity, boolean locked){
        LockInfo data = entity.getData(LocksNextAttachment.LOCK_INFO);
        Lock lock = data.lock();
        lock = new Lock(lock.id(),locked,lock.combo(),lock.length());
        data = new LockInfo(data.lockedArea(),data.lockedFace(),lock,data.itemStack());
        entity.setData(LocksNextAttachment.LOCK_INFO,data);
    }
}
