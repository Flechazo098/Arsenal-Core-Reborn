package cn.mcmod.arsenal.item;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.data.ComponentRegistry;
import cn.mcmod.arsenal.compat.curios.CuriosCapProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

public class WeaponFrogItem extends Item {
    public WeaponFrogItem() {
        super(new Properties().stacksTo(1));
    }

    public static void initAttachments(ItemStack stack) {
        ItemStackHandler handler = stack.get(ComponentRegistry.ITEM_HANDLER_COMPONENT.get());
        if (handler == null) {
            stack.set(ComponentRegistry.ITEM_HANDLER_COMPONENT.get(), new ItemStackHandler(1));
        }
        if (ArsenalCore.curiosLoaded) {
            CuriosCapProvider.attachCurio(stack);
        }
    }

    public static ItemStackHandler getInventory(ItemStack stack) {
        ItemStackHandler handler = stack.get(ComponentRegistry.ITEM_HANDLER_COMPONENT.get());
        if (handler == null) {
            handler = new ItemStackHandler(1);
            stack.set(ComponentRegistry.ITEM_HANDLER_COMPONENT.get(), handler);
        }
        return handler;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        initAttachments(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!stack.has(ComponentRegistry.ITEM_HANDLER_COMPONENT.get())) {
            initAttachments(stack);
        }
    }
}