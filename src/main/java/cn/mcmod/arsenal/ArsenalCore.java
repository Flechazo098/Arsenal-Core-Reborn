//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal;

import cn.mcmod.arsenal.api.tier.WeaponTierRegistry;
import cn.mcmod.arsenal.compat.curios.CuriosCapProvider;
import cn.mcmod.arsenal.item.ArsenalCreativeModTab;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.net.DrawSwordPacket;
import cn.mcmod.arsenal.tier.VanillaWeaponTiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(ArsenalCore.MODID)
public class ArsenalCore {
    public static final String MODID = "arsenal_core";
    public static boolean curiosLoaded = false;
    private static final Logger LOGGER = LogManager.getLogger();

    public ArsenalCore(IEventBus modEventBus) {
        curiosLoaded = ModList.get().isLoaded("curios");
        this.registerVanillaTiers();
        modEventBus.addListener(this::registerPayloadHandlers);
        ItemRegistry.ITEMS.register(modEventBus);
        CuriosCapProvider.register(modEventBus);
        ArsenalCreativeModTab.CREATIVE_MODE_TABS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ArsenalConfig.SPEC);
    }

    @SubscribeEvent
    public void registerPayloadHandlers (final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(MODID)
                .versioned("1.0");

        registrar.play(DrawSwordPacket.ID, DrawSwordPacket::new, handler -> handler
                .server(DrawSwordPacket::handle)
        );
    }
    private void registerVanillaTiers() {
        WeaponTierRegistry.registerAll(
                VanillaWeaponTiers.WOOD.get(),
                VanillaWeaponTiers.STONE.get(),
                VanillaWeaponTiers.IRON.get(),
                VanillaWeaponTiers.GOLD.get(),
                VanillaWeaponTiers.DIAMOND.get(),
                VanillaWeaponTiers.NETHERITE.get(),
                VanillaWeaponTiers.MAXIMUM_POWER.get());
    }


    public static Logger getLogger() {
        return LOGGER;
    }
}