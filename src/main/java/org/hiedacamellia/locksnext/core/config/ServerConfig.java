package org.hiedacamellia.locksnext.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.hiedacamellia.locksnext.core.enums.LockType;

import java.util.List;


public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<List<LockType>> LOCK_TYPE =
            BUILDER.comment("LocksTypes that are enabled")
                    .comment("将启用的锁类型")
                    .comment("SIMPLE: A QTE mini game.")
                    .comment("CLASSIC: Classic mini game.")
                    .comment("MODERN: A modern unlock mini game.")
                    .comment("SIMPLE: QTE小游戏。")
                    .comment("CLASSIC: 经典小游戏。")
                    .comment("MODERN: 现代的开锁小游戏。")
                    .define("lock_type", List.of(LockType.MODERN));


    public static final ModConfigSpec SPEC = BUILDER.build();

}
