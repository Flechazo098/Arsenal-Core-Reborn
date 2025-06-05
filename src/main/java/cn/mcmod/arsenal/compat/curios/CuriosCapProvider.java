package cn.mcmod.arsenal.compat.curios;

import cn.mcmod.arsenal.ArsenalCore;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

public class CuriosCapProvider {
    public static final DeferredRegister<AttachmentType<?>> CURIO_ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ArsenalCore.MODID);

    public static final Supplier<AttachmentType<CuriosWrapper>> CURIO = CURIO_ATTACHMENT_TYPES.register(
            "curio", () -> AttachmentType.builder((holder) -> new CuriosWrapper(ItemStack.EMPTY))
                    .serialize(CuriosWrapper.CODEC)
                    .build());


    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        CURIO_ATTACHMENT_TYPES.register(modEventBus);
        ItemHandlerCapProvider.register(modEventBus);
    }

    public static void attachCurio(ItemStack stack) {
        if (!stack.hasData(CURIO)) {
            stack.setData(CURIO, new CuriosWrapper(stack));
        }
    }

    public static ICurio getCurio(ItemStack stack) {
        if (!stack.hasData(CURIO)) {
            attachCurio(stack);
        }
        return stack.getData(CURIO);
    }
}