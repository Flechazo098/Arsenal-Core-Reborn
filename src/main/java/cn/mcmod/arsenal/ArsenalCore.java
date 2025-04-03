package cn.mcmod.arsenal;

import cn.mcmod.arsenal.api.ArsenalAPI;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.api.tier.WeaponTierRegistry;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.item.SwordSheathItem;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import cn.mcmod.arsenal.item.knight.ArmingSwordItem;
import cn.mcmod.arsenal.item.knight.LongswordItem;
import cn.mcmod.arsenal.item.rapier.RapierItem;
import cn.mcmod.arsenal.item.rapier.SmallswordItem;
import cn.mcmod.arsenal.net.NetPacketHandler;
import cn.mcmod.arsenal.tier.VanillaWeaponTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(Constants.MODID)
public class ArsenalCore {
    public static boolean curiosLoaded = false;
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean configLoaded = false;

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Constants.MODID);

    public static final RegistryObject<CreativeModeTab> WEAPON_GROUP = CREATIVE_MODE_TABS.register("arsenal_core",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.arsenal_core"))
                    .icon(() -> {
                        // Use a safer approach to get the icon
                        try {
                            // First check if config is loaded and tiers are initialized
                            if (configLoaded && VanillaWeaponTiers.IRON != null) {
                                // Try to get the iron ancient sword, but have fallbacks
                                if (ItemRegistry.ANCIENT_SWORD != null &&
                                        ItemRegistry.ANCIENT_SWORD.containsKey(VanillaWeaponTiers.IRON) &&
                                        ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.IRON) != null) {
                                    return new ItemStack(ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.IRON).get());
                                }

                                // Try other weapons if ancient sword isn't available
                                if (ItemRegistry.CHINESE_SWORD != null &&
                                        ItemRegistry.CHINESE_SWORD.containsKey(VanillaWeaponTiers.IRON) &&
                                        ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.IRON) != null) {
                                    return new ItemStack(ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.IRON).get());
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error("Error getting creative tab icon", e);
                        }

                        // Fallback to a vanilla item if our items aren't available yet
                        return new ItemStack(net.minecraft.world.item.Items.IRON_SWORD);
                    })
                    .displayItems((parameters, output) -> {
                        // Only add weapons if they're registered
                        if (configLoaded) {
                            addWeaponsToTab(output);
                        }
                    })
                    .build());
    public ArsenalCore() {
        curiosLoaded = ModList.get().isLoaded("curios");

        // 先注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ArsenalConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ArsenalConfig.CLIENT_CONFIG);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 添加配置加载事件监听器
        modEventBus.addListener(this::onConfigLoading);
        modEventBus.addListener(this::setup);

        // 添加创造模式物品栏内容构建事件监听器
        modEventBus.addListener(this::buildContents);

        // 添加数据生成事件监听器
        modEventBus.addListener(this::gatherData);

        // 注册物品和创造模式标签
        ItemRegistry.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        CuriosApi.registerCurioPredicate(new ResourceLocation(Constants.MODID, "weapon_frog"), (slotResult -> true));
        // 注册到Forge的事件总线
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
    }

    // 数据生成事件处理
    private void gatherData(net.minecraftforge.data.event.GatherDataEvent event) {
        // 确保在数据生成阶段也初始化武器等级
        if (!configLoaded) {
            LOGGER.info("Arsenal Core: Initializing weapon tiers for data generation");
            VanillaWeaponTiers.init();
            registerVanillaTiers();
            configLoaded = true;
        }
    }

    // 配置加载事件处理
    private void onConfigLoading(ModConfigEvent.Loading event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON) {
            LOGGER.info("Arsenal Core: Common config loaded");
            // 初始化武器等级
            VanillaWeaponTiers.init();
            // 注册武器等级
            registerVanillaTiers();
            // 初始化武器注册表
            ArsenalAPI.initializeWeaponRegistries();
            // 更新武器等级
            ArsenalAPI.updateWeaponTiers();
            configLoaded = true;
        }
    }
    private void registerVanillaTiers() {
        WeaponTierRegistry.registerAll(VanillaWeaponTiers.WOOD,
                VanillaWeaponTiers.STONE,
                VanillaWeaponTiers.IRON,
                VanillaWeaponTiers.GOLD,
                VanillaWeaponTiers.DIAMOND,
                VanillaWeaponTiers.NETHERITE,
                VanillaWeaponTiers.MAXIMUM_POWER);
    }
        private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetPacketHandler::registerMessage);
    }

    private void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            // 添加非隐藏的武器到战斗物品栏
            for (Item item : ForgeRegistries.ITEMS) {
                if (item instanceof SwordSheathItem && !((SwordSheathItem) item).isHidden()) {
                    event.accept(item);
                }
            }
        } else if (event.getTabKey() == WEAPON_GROUP.getKey()) {
            // 添加所有武器到自定义物品栏
            addWeaponsToTab(event);
        }
    }

    // 添加武器到物品栏的辅助方法
    private static void addWeaponsToTab (CreativeModeTab.Output output) {
        // 添加所有武器和剑鞘
        WeaponTierRegistry.WEAPON_TIERS.values().forEach(tier -> {
            if (ItemRegistry.CHINESE_SWORD.containsKey(tier) && ItemRegistry.CHINESE_SWORD.get(tier) != null) {
                output.accept(ItemRegistry.CHINESE_SWORD.get(tier).get());
                output.accept(ItemRegistry.CHINESE_SWORD_SHEATH.get(tier).get());
            }

            if (ItemRegistry.ANCIENT_SWORD.containsKey(tier) && ItemRegistry.ANCIENT_SWORD.get(tier) != null) {
                output.accept(ItemRegistry.ANCIENT_SWORD.get(tier).get());
                output.accept(ItemRegistry.ANCIENT_SWORD_SHEATH.get(tier).get());
            }

            if (ItemRegistry.RAPIER.containsKey(tier) && ItemRegistry.RAPIER.get(tier) != null) {
                output.accept(ItemRegistry.RAPIER.get(tier).get());
                output.accept(ItemRegistry.RAPIER_SCABBARD.get(tier).get());
            }

            if (ItemRegistry.SMALLSWORD.containsKey(tier) && ItemRegistry.SMALLSWORD.get(tier) != null) {
                output.accept(ItemRegistry.SMALLSWORD.get(tier).get());
                output.accept(ItemRegistry.SMALLSWORD_SCABBARD.get(tier).get());
            }

            if (ItemRegistry.ARMING_SWORD.containsKey(tier) && ItemRegistry.ARMING_SWORD.get(tier) != null) {
                output.accept(ItemRegistry.ARMING_SWORD.get(tier).get());
                output.accept(ItemRegistry.ARMING_SWORD_SCABBARD.get(tier).get());
            }

            if (ItemRegistry.LONGSWORD.containsKey(tier) && ItemRegistry.LONGSWORD.get(tier) != null) {
                output.accept(ItemRegistry.LONGSWORD.get(tier).get());
                output.accept(ItemRegistry.LONGSWORD_SCABBARD.get(tier).get());
            }
        });

        // 添加特殊武器
        if (ItemRegistry.XUANYUANJIAN != null) {
            output.accept(ItemRegistry.XUANYUANJIAN.get());
            output.accept(ItemRegistry.XUANYUANJIAN_SHEATH.get());
        }

        if (ItemRegistry.WEAPON_FROG != null) {
            output.accept(ItemRegistry.WEAPON_FROG.get());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static boolean isConfigLoaded() {
        return configLoaded;
    }
}