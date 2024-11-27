package org.hiedacamellia.locksnext.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.hiedacamellia.locksnext.LocksNext;
import org.hiedacamellia.locksnext.common.menu.TestMenu;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class TestScreen extends AbstractContainerScreen<TestMenu> {
    public final Level level;
    public final Player entity;
    public final LockType lockType;

    public static final ResourceLocation LOCK = LocksNext.loc("textures/gui/lock.png");
    public static final ResourceLocation LOCK_PICK = LocksNext.loc("textures/gui/lock_pick.png");


    private int lock_pick_angle;
    private int rotate_angle;

    private final static int KEY_UP = Minecraft.getInstance().options.keyUp.getKey().getValue();
    private final static int KEY_DOWN = Minecraft.getInstance().options.keyDown.getKey().getValue();
    private final static int KEY_USE = Minecraft.getInstance().options.keyUse.getKey().getValue();

    private int center_x, center_y;

    public TestScreen(TestMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.level = menu.level;
        this.entity = menu.entity;
        this.lockType = menu.lockType;
        this.lock_pick_angle =0;
        this.rotate_angle =0;
        this.imageWidth = 48;
        this.imageHeight = 48;
    }
    @Override
    public void init(){
        super.init();
        this.center_x = this.leftPos + this.imageWidth / 2;
        this.center_y = this.topPos + this.imageHeight / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        guiGraphics.blit(LOCK, leftPos, topPos, 0, 0, 48, 48, 48, 96);
        guiGraphics.blit(LOCK, leftPos, topPos, 0, 48, 48, 48, 48, 96);
        pose.popPose();
        pose.pushPose();
        pose.rotateAround(new Quaternionf().rotateZ((float) (lock_pick_angle*Math.PI/180)), center_x, center_y, 0);
        guiGraphics.blit(LOCK_PICK, center_x ,center_y-3, 0, 0, 56, 6, 56, 6);
        pose.popPose();
    }

    @Override
    public void containerTick() {
        this.rotate_angle--;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode== KEY_UP){
            this.lock_pick_angle++;
            return true;
        }
        if(keyCode== KEY_DOWN){
            this.lock_pick_angle--;
            return true;
        }
        if(keyCode== KEY_USE){
            this.rotate_angle+=2;
            return true;
        }


        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
