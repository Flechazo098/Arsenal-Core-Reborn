//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.net;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.item.WeaponFrogItem;
import java.util.function.Supplier;
import java.util.Random;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class DrawSwordPacket {
    private final String message;
    private static final Random RANDOM = new Random();

    public DrawSwordPacket(FriendlyByteBuf buffer) {
        this.message = buffer.readUtf(32767);
    }

    public DrawSwordPacket(String message) {
        this.message = message;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (ArsenalCore.curiosLoaded) {
                CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
                    // 获取 belt 槽位的物品栏
                    itemHandler.getStacksHandler("belt").ifPresent(stacksHandler -> {
                        IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                        for(int i = 0; i < stackHandler.getSlots(); ++i) {
                            if (stackHandler.getStackInSlot(i).getItem() instanceof WeaponFrogItem) {
                                stackHandler.getStackInSlot(i).getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                                    if (cap.getStackInSlot(0).isEmpty()) {
                                        this.sheathSword(player, cap);
                                    } else {
                                        drawSword(player, cap.getStackInSlot(0));
                                        cap.extractItem(0, 1, false);
                                    }
                                });
                                return;
                            }
                        }
                    });
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private void sheathSword(ServerPlayer player, IItemHandler handler) {
        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IDrawable) {
            handler.insertItem(0, mainHandItem.copy(), false);
            mainHandItem.shrink(1);
            Holder<SoundEvent> soundHolder = ForgeRegistries.SOUND_EVENTS.getHolder(
                    ForgeRegistries.SOUND_EVENTS.getResourceKey(SoundEvents.ITEM_PICKUP).get()).get();
            player.connection.send(new ClientboundSoundPacket(
                    soundHolder,
                    SoundSource.PLAYERS,
                    player.getX(), player.getY(), player.getZ(),
                    1.0F, 1.0F,
                    RANDOM.nextLong()));
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
                Holder<SoundEvent> soundHolder = ForgeRegistries.SOUND_EVENTS.getHolder(
                        ForgeRegistries.SOUND_EVENTS.getResourceKey(SoundEvents.PLAYER_ATTACK_SWEEP).get()).get();
                player.connection.send(new ClientboundSoundPacket(
                        soundHolder,
                        SoundSource.PLAYERS,
                        player.getX(), player.getY(), player.getZ(),
                        1.0F, 1.0F,
                        RANDOM.nextLong()));
            }
        }
    }
}