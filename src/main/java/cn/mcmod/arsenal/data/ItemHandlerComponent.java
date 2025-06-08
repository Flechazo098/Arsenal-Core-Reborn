package cn.mcmod.arsenal.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class ItemHandlerComponent {


    public record ItemHandlerData(CompoundTag data) {
        public ItemHandlerData (CompoundTag data) {
            this.data = data.copy();
        }

        @Override
        public CompoundTag data () {
            return this.data.copy();
        }

        @Override
        public boolean equals (Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ItemHandlerData that = (ItemHandlerData) obj;
            return Objects.equals(data, that.data);
        }

    }

    public static final Codec<ItemHandlerData> ITEM_HANDLER_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    CompoundTag.CODEC.fieldOf("data").forGetter(ItemHandlerData::data)
            ).apply(instance, ItemHandlerData::new)
    );
    public static final StreamCodec<FriendlyByteBuf, ItemHandlerData> ITEM_HANDLER_DATA_STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.COMPOUND_TAG, ItemHandlerData::data,
                    ItemHandlerData::new
            );

}
