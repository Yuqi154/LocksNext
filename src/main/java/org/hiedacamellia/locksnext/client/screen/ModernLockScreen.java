package org.hiedacamellia.locksnext.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.common.menu.ModernLockMenu;
import org.hiedacamellia.locksnext.core.enums.LockType;
import org.hiedacamellia.locksnext.core.network.pin2server.ModernLockPin;
import org.joml.Quaternionf;

public class ModernLockScreen extends AbstractContainerScreen<ModernLockMenu> {
    public final Level level;
    public final Player entity;

    public static final ResourceLocation LOCK = LNMain.loc("textures/gui/lock.png");
    public static final ResourceLocation LOCK_PICK = LNMain.loc("textures/gui/lock_pick.png");


    private float lock_pick_angle;
    private float lock_pick_speed;
    private float rotate_angle;
    public BlockPos lockPos;

    private final static int KEY_UP = Minecraft.getInstance().options.keyUp.getKey().getValue();
    private final static int KEY_DOWN = Minecraft.getInstance().options.keyDown.getKey().getValue();
    private final static int KEY_USE = Minecraft.getInstance().options.keyUse.getKey().getValue();

    private int center_x, center_y;

    public ModernLockScreen(ModernLockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.level = menu.level;
        this.entity = menu.entity;
        this.lockPos = menu.lockPos;
        this.lock_pick_angle =0;
        this.rotate_angle =0;
        this.imageWidth = 48;
        this.imageHeight = 48;
        this.lock_pick_speed=0;
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
        guiGraphics.blit(LOCK, leftPos, topPos, 0, 0, 48, 48, 48, 96);
        guiGraphics.blit(LOCK, leftPos, topPos, 0, 48, 48, 48, 48, 96);
        pose.pushPose();
        pose.rotateAround(new Quaternionf().rotateZ((float) (this.lock_pick_angle*Math.PI/180)), center_x, center_y, 0);
        guiGraphics.blit(LOCK_PICK, center_x-2 ,center_y-3, 0, 0, 56, 6, 56, 6);
        pose.popPose();
        RenderSystem.setShaderColor(1.0F,0.4F,1.0F,1.0F);
        pose.rotateAround(new Quaternionf().rotateZ((float) (this.rotate_angle*Math.PI/180)), center_x, center_y, 0);
        guiGraphics.blit(LOCK_PICK, center_x-2 ,center_y-2, 0, 0, 38, 4, 56, 6);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        pose.popPose();
    }

    @Override
    public void containerTick() {
        //扭动开锁器
        if(this.rotate_angle>0) {
            if(this.menu.available<this.rotate_angle)
                this.rotate_angle = this.menu.available;
            if(lockPos!=null)
                PacketDistributor.sendToServer(new ModernLockPin(lockPos,this.lock_pick_angle,this.rotate_angle));
            this.rotate_angle -= 1.5F;
        }
        if(this.rotate_angle<0.5)
            this.rotate_angle=0;


        //撬锁器的旋转
        if(this.lock_pick_speed<0.5&&this.lock_pick_speed>-0.5) {
            this.lock_pick_speed = 0;
            return;
        }
        this.lock_pick_angle+=this.lock_pick_speed;
        if(this.lock_pick_angle>360)
            this.lock_pick_angle-=360;
        if(this.lock_pick_angle<0)
            this.lock_pick_angle+=360;

        if(this.lock_pick_speed>=0.5)
            this.lock_pick_speed-=0.5f;
        if(this.lock_pick_speed<=-0.5)
            this.lock_pick_speed+=0.5f;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode== KEY_DOWN){
            if(this.lock_pick_speed<5)
                this.lock_pick_speed=5f;
            return true;
        }
        if(keyCode== KEY_UP){
            if(this.lock_pick_speed>-5)
                this.lock_pick_speed=-5f;
            return true;
        }
        if(keyCode== KEY_USE){
            this.rotate_angle+=4F;
            return true;
        }


        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
