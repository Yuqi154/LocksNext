package org.hiedacamellia.locksnext.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.record.LockStorage;

import javax.annotation.Nullable;
import java.util.List;

public class ChunkUtil {
    public static boolean checkLocked(LevelChunk chunk, BlockPos blockPos){
        LockInfo lockInfo = getLocked(chunk, blockPos);
        if(lockInfo == null)
            return false;
        return lockInfo.lock().isLocked();
    }

    public static @Nullable LockInfo getLocked(LevelChunk chunk, BlockPos blockPos){
        LockStorage data = LockStorage.fromChunk(chunk);
        List<LockInfo> infos = data.infos();
        if(infos.isEmpty())
            return null;
        for(LockInfo info : infos){
            if(info.lockedArea().contains(blockPos))
                return info;
        }
        return null;
    }

    public static void updateLockInfo(LevelChunk chunk,LockInfo newInfo,LockInfo oldInfo){
        LockStorage data = LockStorage.fromChunk(chunk);
        List<LockInfo> infos = data.infos();
        infos.remove(oldInfo);
        infos.add(newInfo);
        LockStorage.setChunk(chunk,data);
    }

    public static void removeLockInfo(LevelChunk chunk,LockInfo info){
        LockStorage data = LockStorage.fromChunk(chunk);
        List<LockInfo> infos = data.infos();
        infos.remove(info);
        LockStorage.setChunk(chunk,data);
    }

    public static void addLockInfo(LevelChunk chunk,LockInfo info){
        LockStorage data = LockStorage.fromChunk(chunk);
        List<LockInfo> infos = data.infos();
        infos.add(info);
        LockStorage.setChunk(chunk,data);
    }
}
