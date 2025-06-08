package cn.mcmod.arsenal.client;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.data.ComponentRegistry;
import cn.mcmod.arsenal.data.WeaponProgressComponent;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.net.DrawSwordPacket;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import cpw.mods.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(Dist.CLIENT)
public class KeyDrawSword {
    public static final KeyMapping DRAW_SWORD_KEY =new KeyMapping("key.draw_sword", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Type.MOUSE, 1, "key.category.arsenal");

    public static final KeyMapping XUANYUAN_KEY = new KeyMapping("key.xuanyuan_skill_1", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, "key.category.arsenal");

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (DRAW_SWORD_KEY.isDown() && ArsenalCore.curiosLoaded) {
            PacketDistributor.sendToServer(new DrawSwordPacket("Reika Battou"));
        }

        if (XUANYUAN_KEY.consumeClick()) {
            ItemStack mainHand = mc.player.getMainHandItem();
            if (mainHand.is(ItemRegistry.XUANYUANJIAN.get())) {
                WeaponProgressComponent.WeaponProgress progress = mainHand.get(ComponentRegistry.WEAPON_PROGRESS.get());
                if (progress != null && progress.level() >= 3) {
                    // TODO: 创建并发送轩辕剑技能包
                    // PacketDistributor.sendToServer(new XuanyuanSkillPacket());
                }
            }
        }
    }
}