package cn.mcmod.arsenal.net;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.item.WeaponFrogItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Random;

public class ServerPayloadHandler {

    private static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();
    private static final Random RANDOM = new Random();

    public static ServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleDrawSword(DrawSwordPacket packet, PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            ServerPlayer player = (ServerPlayer) context.player().orElse(null);
            if (player == null) return;

            if (ArsenalCore.curiosLoaded) {
                CuriosApi.getCuriosInventory(player).flatMap(inv -> inv.getStacksHandler("belt")).ifPresent(stacksHandler -> {
                    IDynamicStackHandler handler = stacksHandler.getStacks();
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack stack = handler.getStackInSlot(i);
                        if (stack.getItem() instanceof WeaponFrogItem) {
                            IItemHandler cap = stack.getCapability(Capabilities.ItemHandler.ITEM, null);
                            if (cap != null) {
                                if (cap.getStackInSlot(0).isEmpty()) {
                                    sheathSword(player, cap);
                                } else {
                                    drawSword(player, cap.getStackInSlot(0));
                                    cap.extractItem(0, 1, false);
                                }
                            }
                            break;
                        }
                    }
                });
            }
        }).exceptionally(e -> {
            context.packetHandler().disconnect(Component.literal("Error handling DrawSwordPacket: " + e.getMessage()));
            return null;
        });
    }


    private static void sheathSword(ServerPlayer player, net.neoforged.neoforge.items.IItemHandler handler) {
        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IDrawable) {
            handler.insertItem(0, mainHandItem.copy(), false);
            mainHandItem.shrink(1);
            SoundEvent soundEvent = SoundEvents.ITEM_BREAK;
            Holder<SoundEvent> soundHolder = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent);

            player.connection.send(new ClientboundSoundPacket(
                    soundHolder,
                    SoundSource.PLAYERS,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    1.0F,
                    1.0F,
                    RANDOM.nextLong()
            ));
        }
    }

    private static void drawSword(ServerPlayer player, ItemStack blade) {
        if (blade.getItem() instanceof IDrawable sword) {
            ItemStack mainHandItem = player.getMainHandItem().copy();
            player.setItemInHand(InteractionHand.MAIN_HAND, blade.copy());
            if (!mainHandItem.isEmpty() && !player.addItem(mainHandItem)) {
                player.drop(mainHandItem, false);
            }

            if (sword.drawSwordAttack(player, mainHandItem)) {
                SoundEvent soundEvent = SoundEvents.PLAYER_ATTACK_SWEEP;
                Holder<SoundEvent> soundHolder = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent);

                player.connection.send(new ClientboundSoundPacket(
                        soundHolder,
                        SoundSource.PLAYERS,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        1.0F,
                        1.0F,
                        RANDOM.nextLong()
                ));
            }
        }
    }
}