package org.hiedacamellia.locksnext.core.network.resp2client;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.locksnext.LNMain;
import org.hiedacamellia.locksnext.common.menu.ModernLockMenu;

public record ModernLockResp(boolean open,boolean broke,float available) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf,ModernLockResp> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,ModernLockResp::open,
            ByteBufCodecs.BOOL,ModernLockResp::broke,
            ByteBufCodecs.FLOAT,ModernLockResp::available,
            ModernLockResp::new
    );

    public static final Type<ModernLockResp> TYPE = new Type<>(LNMain.loc("modern_resp"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ModernLockResp packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            AbstractContainerMenu containerMenu = context.player().containerMenu;
            if(containerMenu instanceof ModernLockMenu menu){
                menu.handleResp(packet);
            }
        });
    }
}
