package org.hiedacamellia.locksnext.common.menu;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.core.util.LevelUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class LockMenuProvider implements MenuProvider {
    LockType lockType;
    BlockPos lockPos;

    public LockMenuProvider(LockType lockType, BlockPos blockPos) {
        this.lockType = lockType;
        this.lockPos = blockPos;
    }

    public LockMenuProvider(Level level, BlockPos blockPos) {
        this.lockType = LevelUtil.getLocked(level, blockPos).lock().lockType();
        this.lockPos = blockPos;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return switch (lockType) {
            case CLASSIC -> Component.literal("Classic Lock");
            case SIMPLE -> Component.literal("Simple Lock");
            case MODERN -> Component.literal("Modern Lock");
            default -> Component.literal("Unknown Lock");
        };
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(lockPos);
        return switch (lockType) {
            case CLASSIC -> new ClassicLockMenu(i, inventory, buf);
            case SIMPLE -> new SimpleLockMenu(i, inventory, buf);
            case MODERN -> new ModernLockMenu(i, inventory, buf);
            default -> null;
        };
    }
}
