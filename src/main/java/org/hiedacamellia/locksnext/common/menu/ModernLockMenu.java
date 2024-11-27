package org.hiedacamellia.locksnext.common.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.core.network.resp2client.ModernLockResp;
import org.hiedacamellia.locksnext.registries.LNMenu;

public class ModernLockMenu extends AbstractContainerMenu {
    public final Level level;
    public final Player entity;
    public boolean open;
    public boolean broke;
    public float available;
    public BlockPos lockPos;

    public ModernLockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(LNMenu.MODERN.get(), id);
        this.level = inv.player.level();
        this.entity = inv.player;
        this.open = false;
        this.broke = false;
        this.available = 10;
        if(extraData!=null){
            this.lockPos = extraData.readBlockPos();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void handleResp(ModernLockResp resp){
        this.open = resp.open();
        this.broke = resp.broke();
        this.available = resp.available();
    }
}
