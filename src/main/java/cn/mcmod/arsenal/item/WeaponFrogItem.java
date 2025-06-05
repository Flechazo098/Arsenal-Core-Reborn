package cn.mcmod.arsenal.item;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.compat.curios.CuriosCapProvider;
import cn.mcmod.arsenal.compat.curios.ItemHandlerCapProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

public class WeaponFrogItem extends Item {
    public WeaponFrogItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        initAttachments(stack);
    }

    private void initAttachments(ItemStack stack) {
        if (!stack.hasData(ItemHandlerCapProvider.ITEM_HANDLER)) {
            stack.setData(ItemHandlerCapProvider.ITEM_HANDLER, new ItemStackHandler(1));
        }

        if (ArsenalCore.curiosLoaded) {
            CuriosCapProvider.attachCurio(stack);
        }
    }

    public static ItemStackHandler getInventory(ItemStack stack) {
        return ItemHandlerCapProvider.getInventory(stack);
    }
}