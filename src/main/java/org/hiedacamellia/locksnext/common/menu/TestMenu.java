package org.hiedacamellia.locksnext.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.registries.LocksNextMenu;

public class TestMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player entity;
    public final LockType lockType;

    public TestMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(LocksNextMenu.TEST_MENU.get(), id);
        this.level = inv.player.level();
        this.entity = inv.player;
        this.lockType = extraData.readEnum(LockType.class);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
