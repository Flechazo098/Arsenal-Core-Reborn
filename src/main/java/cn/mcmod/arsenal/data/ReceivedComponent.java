package cn.mcmod.arsenal.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public class ReceivedComponent {
    public record XuanyuanData(boolean receivedMessage) {
        public static final Codec<XuanyuanData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.BOOL.fieldOf("xuanyuan_receivedMessage").forGetter(XuanyuanData::receivedMessage)
                ).apply(instance, XuanyuanData::new)
        );

        public static final StreamCodec<FriendlyByteBuf, XuanyuanData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.BOOL,
                        XuanyuanData::receivedMessage,
                        XuanyuanData::new
                );
    }
}
