package cn.mcmod.arsenal.client;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.net.DrawSwordPacket;
import cn.mcmod.arsenal.net.NetPacketHandler;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyDrawSword {
    // 使用默认值初始化键映射以防止空指针异常
    public static KeyMapping KEY = new KeyMapping("key.draw_sword", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Type.MOUSE, 1, "key.category.arsenal");

    public KeyDrawSword() {
    }

    // 添加键映射注册事件处理程序
    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class KeyRegistry {
        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            // 注册已初始化的 key
            event.register(KEY);
        }
    }

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        // 空检查不再是必需的，但为了安全起见，会保留
        if (KEY != null && KEY.consumeClick() && ArsenalCore.curiosLoaded) {
            NetPacketHandler.INSTANCE.sendToServer(new DrawSwordPacket("Reika Battou"));
        }
    }
}