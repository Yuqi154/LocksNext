package org.hiedacamellia.locksnext.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.hiedacamellia.locksnext.core.record.LockInfo;


public class LevelUtil {

    public static boolean checkLocked(Level level, BlockPos blockPos){
        LevelChunk chunk = level.getChunkAt(blockPos);
        return ChunkUtil.checkLocked(chunk,blockPos);
    }

    public static void updateLockInfo(Level level,LockInfo newInfo,LockInfo oldInfo){
        LevelChunk chunk = level.getChunkAt(newInfo.lockedArea().centerPos());
        ChunkUtil.updateLockInfo(chunk,newInfo,oldInfo);
    }

    public static void removeLockInfo(Level level,LockInfo info){
        LevelChunk chunk = level.getChunkAt(info.lockedArea().centerPos());
        ChunkUtil.removeLockInfo(chunk,info);
    }

    public static void addLockInfo(Level level,LockInfo info){
        LevelChunk chunk = level.getChunkAt(info.lockedArea().centerPos());
        ChunkUtil.addLockInfo(chunk,info);
    }
}
