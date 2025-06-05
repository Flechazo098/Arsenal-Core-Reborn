//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.net;

import cn.mcmod.arsenal.ArsenalCore;

import java.util.Random;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DrawSwordPacket(String message) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsenalCore.MODID, "draw_sword");

    public DrawSwordPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(32767));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}