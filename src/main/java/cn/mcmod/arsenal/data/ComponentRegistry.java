package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.ArsenalCore;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.mojang.serialization.Codec;

import java.util.function.Supplier;

public class ComponentRegistry {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ArsenalCore.MODID);

    public static final Codec<ItemStackHandler> ITEM_STACK_HANDLER_CODEC = Codec.unit(() -> new ItemStackHandler(1));
    public static final StreamCodec<FriendlyByteBuf, ItemStackHandler> ITEM_STACK_HANDLER_STREAM_CODEC = StreamCodec.unit(new ItemStackHandler(1));

    public static final Supplier<DataComponentType<ItemStackHandler>> ITEM_HANDLER_COMPONENT =
            DATA_COMPONENT_TYPES.register("item_handler",
                    () -> DataComponentType.<ItemStackHandler>builder()
                            .persistent(ITEM_STACK_HANDLER_CODEC)
                            .networkSynchronized(ITEM_STACK_HANDLER_STREAM_CODEC)
                            .build());

    public static void register(IEventBus modBus) {
        DATA_COMPONENT_TYPES.register(modBus);
    }
}