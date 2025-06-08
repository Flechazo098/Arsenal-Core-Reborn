package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.ArsenalCore;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ArsenalCore.MODID);


    public static final Supplier<DataComponentType<ItemHandlerComponent.ItemHandlerData>> ITEM_HANDLER_COMPONENT =
            ComponentRegistry.DATA_COMPONENT_TYPES.register("item_handler",
                    () -> DataComponentType.<ItemHandlerComponent.ItemHandlerData>builder()
                            .persistent(ItemHandlerComponent.ITEM_HANDLER_DATA_CODEC)
                            .networkSynchronized(ItemHandlerComponent.ITEM_HANDLER_DATA_STREAM_CODEC)
                            .build());


    public static final Supplier<DataComponentType<WeaponProgressComponent.WeaponProgress>> WEAPON_PROGRESS =
            ComponentRegistry.DATA_COMPONENT_TYPES.register("weapon_progress",
                    () -> DataComponentType.<WeaponProgressComponent.WeaponProgress>builder()
                            .persistent(WeaponProgressComponent.WeaponProgress.CODEC)
                            .networkSynchronized(WeaponProgressComponent.WeaponProgress.STREAM_CODEC)
                            .build()
            );

    public static final Supplier<DataComponentType<ReceivedComponent.XuanyuanData>> XUANYUAN_DATA =
            ComponentRegistry.DATA_COMPONENT_TYPES.register("xuanyuan_data",
                    () -> DataComponentType.<ReceivedComponent.XuanyuanData>builder()
                            .persistent(ReceivedComponent.XuanyuanData.CODEC)
                            .networkSynchronized(ReceivedComponent.XuanyuanData.STREAM_CODEC)
                            .build()
            );


    public static void register(IEventBus modBus) {
        DATA_COMPONENT_TYPES.register(modBus);
    }
}