package org.hiedacamellia.locksnext.core.util;

import net.minecraft.world.entity.Entity;
import org.hiedacamellia.locksnext.core.record.Lock;
import org.hiedacamellia.locksnext.core.record.LockInfo;

public class EntityUtil {

    public static boolean checkLocked(Entity entity){
        LockInfo data = LockInfo.fromEntity(entity);
        Lock lock = data.lock();
        return lock.isLocked();
    }

    public static void setLock(Entity entity, Lock lock){
        LockInfo data = LockInfo.fromEntity(entity);
        data = new LockInfo(data.lockedArea(),data.lockedFace(),lock,data.itemStack());
        LockInfo.setEntity(entity,data);
    }

    public static void setLock(Entity entity, boolean locked){
        LockInfo data = LockInfo.fromEntity(entity);
        Lock lock = data.lock();
        lock = lock.changeLocked(locked);
        data = new LockInfo(data.lockedArea(),data.lockedFace(),lock,data.itemStack());
        LockInfo.setEntity(entity,data);
    }
}
