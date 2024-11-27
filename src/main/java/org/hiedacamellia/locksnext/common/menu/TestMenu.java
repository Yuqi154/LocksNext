package org.hiedacamellia.locksnext.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.registries.LNMenu;

public class TestMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player entity;
    public final LockType lockType;

    public TestMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(LNMenu.TEST_MENU.get(), id);
        this.level = inv.player.level();
        this.entity = inv.player;
        if (extraData != null)
            this.lockType = extraData.readEnum(LockType.class);
        else
            this.lockType = LockType.HARD;
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
