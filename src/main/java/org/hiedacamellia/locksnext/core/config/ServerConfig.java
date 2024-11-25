package org.hiedacamellia.locksnext.core.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.hiedacamellia.locksnext.LocksNext;
import org.hiedacamellia.locksnext.core.enums.LockType;

import java.util.List;


@EventBusSubscriber(modid = LocksNext.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<List<LockType>> LOCK_TYPE =
            BUILDER.comment("LocksTypes that are enabled")
                    .comment("将启用的锁类型")
                    .comment("EASY: A QTE mini game.")
                    .comment("MEDIUM: Classic mini game.")
                    .comment("HARD: A difficult mini game.")
                    .comment("EASY: QTE小游戏。")
                    .comment("MEDIUM: 经典小游戏。")
                    .comment("HARD: 困难的小游戏。")
                    .define("lock_type", List.of(LockType.HARD));


    public static final ModConfigSpec SPEC = BUILDER.build();


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }
}
