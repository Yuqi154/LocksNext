package org.hiedacamellia.locksnext.core.util;

import net.minecraft.util.RandomSource;
import org.hiedacamellia.locksnext.core.config.ServerConfig;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.core.record.Lock;

import javax.annotation.Nullable;
import java.util.List;

public class LockUtil {

    public static @Nullable Lock genLock(long id){
        RandomSource randomSource = RandomSource.create(id);
        List<LockType> lockTypes = ServerConfig.LOCK_TYPE.get();
        LockType lockType = lockTypes.get(randomSource.nextInt(lockTypes.size()));
        return switch (lockType) {
            case CLASSIC ->  Lock.createClassic(id, genClassicLength(randomSource));
            case SIMPLE -> Lock.createSimple(id);
            case HARD -> Lock.createModern(id);
            default -> null;
        };
    }

    public static byte genClassicLength(RandomSource randomSource){
        int min = ServerConfig.CLASSIC_MIN_LENGTH.get();
        int max = ServerConfig.CLASSIC_MAX_LENGTH.get();
        return (byte) (randomSource.nextInt(max-min)+min);
    }

}
