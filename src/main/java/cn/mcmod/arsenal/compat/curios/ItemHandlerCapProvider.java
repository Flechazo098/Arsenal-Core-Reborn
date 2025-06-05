package cn.mcmod.arsenal.compat.curios;

import cn.mcmod.arsenal.ArsenalCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ItemHandlerCapProvider {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ArsenalCore.MODID);

    public static final Supplier<AttachmentType<ItemStackHandler>> ITEM_HANDLER = ATTACHMENT_TYPES.register(
            "item_handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build());

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }

    public static ItemStackHandler getInventory(ItemStack stack) {
        return stack.getData(ITEM_HANDLER);
    }

    public static boolean hasInventory(ItemStack stack) {
        return stack.hasData(ITEM_HANDLER);
    }
}