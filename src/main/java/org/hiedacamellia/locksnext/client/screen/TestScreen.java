package org.hiedacamellia.locksnext.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.common.menu.TestMenu;
import org.hiedacamellia.locksnext.core.enums.LockType;

public class TestScreen extends AbstractContainerScreen<TestMenu> {
    public final Level level;
    public final Player entity;
    public final LockType lockType;




    public TestScreen(TestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.level = menu.level;
        this.entity = menu.entity;
        this.lockType = menu.lockType;
    }
    @Override
    public void init(){
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        PoseStack pose = guiGraphics.pose();

    }
}
