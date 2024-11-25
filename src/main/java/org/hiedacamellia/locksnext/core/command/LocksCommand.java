package org.hiedacamellia.locksnext.core.command;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.Minecart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.hiedacamellia.locksnext.common.menu.LockMenuProvider;
import org.hiedacamellia.locksnext.core.enums.LockType;

@EventBusSubscriber
public class LocksCommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("locksnext")
                .then(Commands.literal("debug")
                        .then(Commands.literal("screen")
                                .then(Commands.literal("hard")
                                        .executes(context -> {
                                            Minecraft.getInstance().player.openMenu(
                                                    new LockMenuProvider(LockType.HARD, Component.literal("hard"))
                                            );
                                            return 0;
                                        })


                                )



                        )



                )
        );
    }
}
