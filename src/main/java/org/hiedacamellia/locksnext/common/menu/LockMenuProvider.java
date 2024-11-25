package org.hiedacamellia.locksnext.common.menu;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LockMenuProvider implements MenuProvider {
    LockType lockType;
    Component displayName;

    public LockMenuProvider(LockType lockType,Component displayName) {
        this.lockType = lockType;
        this.displayName = displayName;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new TestMenu(i, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeEnum(this.lockType));
    }
}
