package org.hiedacamellia.locksnext.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.hiedacamellia.locksnext.core.record.Lock;
import org.hiedacamellia.locksnext.core.record.LockInfo;

public class GenUtil {

    public static void GenLock(Level level, Block block, BlockPos pos){
        String descriptionId = block.getDescriptionId();
        RandomSource randomSource = level.getRandom().fork();
        //TODO
        Lock lock = LockUtil.genLock(randomSource.nextLong());
        LockInfo lockInfo = LockInfoUtil.create(level, pos,lock);
        if(lockInfo==null) return;
        LevelUtil.addLockInfo(level,lockInfo);
    }
    public static void GenLock(Level level, BlockPos pos){
        Block block = level.getBlockState(pos).getBlock();
        GenLock(level, block, pos);
    }

}
