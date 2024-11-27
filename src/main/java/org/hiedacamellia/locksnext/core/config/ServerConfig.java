package org.hiedacamellia.locksnext.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.hiedacamellia.locksnext.core.enums.LockType;

import java.util.List;


public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<LockType>> LOCK_TYPE =
            BUILDER.comment("LocksTypes that are enabled")
                    .comment("将启用的锁类型")
                    .comment("SIMPLE: A QTE mini game.")
                    .comment("CLASSIC: Classic mini game.")
                    .comment("MODERN: A modern unlock mini game.")
                    .comment("SIMPLE: QTE小游戏。")
                    .comment("CLASSIC: 经典小游戏。")
                    .comment("MODERN: 现代的开锁小游戏。")
                    .define("lock_type", List.of(LockType.MODERN));

    public static final ModConfigSpec.ConfigValue<List<String>> LOCKED_WHEN_GEN =
            BUILDER.comment("Blocks that will be locked when generated")
                    .comment("生成时会被锁定的方块")
                    .define("locked_when_gen", List.of("*.chest"));

    public static final ModConfigSpec.IntValue CLASSIC_MIN_LENGTH =
            BUILDER.comment("The minimum length of the classic lock")
                    .comment("经典锁的最小长度")
                    .defineInRange("classic_min_length", 4, 1, 10);

    public static final ModConfigSpec.IntValue CLASSIC_MAX_LENGTH =
            BUILDER.comment("The maximum length of the classic lock")
                    .comment("经典锁的最大长度")
                    .defineInRange("classic_max_length", 8, 1, 10);

    public static final ModConfigSpec SPEC = BUILDER.build();

}
