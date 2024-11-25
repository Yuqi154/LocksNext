package org.hiedacamellia.locksnext.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.record.LockStorage;
import org.hiedacamellia.locksnext.registries.LocksNextAttachment;

import java.util.List;


public class LevelUtil {

    public static boolean checkLocked(Level level, BlockPos blockPos){
        LevelChunk chunk = level.getChunkAt(blockPos);
        LockStorage data = chunk.getData(LocksNextAttachment.LOCK_STORAGE);
        List<LockInfo> infos = data.infos();
        if(infos.isEmpty())
            return false;
        for(LockInfo info : infos){
            if(info.lockedArea().contains(blockPos))
                return true;
        }
        return false;
    }

    public static void updateLockInfo(Level level,LockInfo newInfo,LockInfo oldInfo){
        LevelChunk chunk = level.getChunkAt(newInfo.lockedArea().centerPos());
        LockStorage data = chunk.getData(LocksNextAttachment.LOCK_STORAGE);
        List<LockInfo> infos = data.infos();
        infos.remove(oldInfo);
        infos.add(newInfo);
        chunk.setData(LocksNextAttachment.LOCK_STORAGE,data);
    }

    public static void removeLockInfo(Level level,LockInfo info){
        LevelChunk chunk = level.getChunkAt(info.lockedArea().centerPos());
        LockStorage data = chunk.getData(LocksNextAttachment.LOCK_STORAGE);
        List<LockInfo> infos = data.infos();
        infos.remove(info);
        chunk.setData(LocksNextAttachment.LOCK_STORAGE,data);
    }

    public static void addLockInfo(Level level,LockInfo info){
        LevelChunk chunk = level.getChunkAt(info.lockedArea().centerPos());
        LockStorage data = chunk.getData(LocksNextAttachment.LOCK_STORAGE);
        List<LockInfo> infos = data.infos();
        infos.add(info);
        chunk.setData(LocksNextAttachment.LOCK_STORAGE,data);
    }
}
