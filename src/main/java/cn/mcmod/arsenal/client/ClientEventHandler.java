package cn.mcmod.arsenal.client;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.compat.curios.client.WeaponFrogRender;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(
        bus = Bus.MOD,
        value = {Dist.CLIENT}
)
public class ClientEventHandler {
    public ClientEventHandler() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemRegistry.ITEMS.getEntries().forEach(ClientEventHandler::registerBlockingProperties));
        if (ArsenalCore.curiosLoaded) {
            registerCuriosRenderer();
        }
    }

    // 删除此方法，因为它是多余的 - key 已经在 KeyDrawSword.KeyRegistry 中注册
    // @OnlyIn(Dist.CLIENT)
    // @SubscribeEvent
    // public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
    //     event.register(KeyDrawSword.KEY);
    // }

    private static void registerCuriosRenderer() {
        CuriosRendererRegistry.register((Item)ItemRegistry.WEAPON_FROG.get(), WeaponFrogRender::new);
    }

    private static void registerBlockingProperties(RegistryObject<Item> item) {
        if (item.get() instanceof AncientSwordItem || item.get() instanceof ChineseSwordItem) {
            ItemProperties.register(item.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, seed) ->
                    livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        }
    }
}