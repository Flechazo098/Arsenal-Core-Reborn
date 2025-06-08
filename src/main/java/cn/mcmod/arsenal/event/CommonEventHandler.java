package cn.mcmod.arsenal.event;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.item.feature.XuanyuanFeature;
import cn.mcmod.arsenal.util.EnchantmentUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = ArsenalCore.MODID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            EnchantmentUtil.init(serverLevel.registryAccess());
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) return;
        if (!(event.getEntity() instanceof LivingEntity killed)) return;

        ItemStack heldItem = player.getMainHandItem();
        if (!heldItem.is(ItemRegistry.XUANYUANJIAN.get())) return;

        XuanyuanFeature feature = new XuanyuanFeature();
        feature.onKillEntity(heldItem, killed, player);
    }
}