package cn.mcmod.arsenal.item;

import cn.mcmod.arsenal.api.ArsenalAPI;
import cn.mcmod.arsenal.api.tier.BlankTier;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.api.tier.WeaponTierRegistry;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import cn.mcmod.arsenal.item.chinese.XuanyuanjianItem;
import cn.mcmod.arsenal.item.knight.ArmingSwordItem;
import cn.mcmod.arsenal.item.knight.LongswordItem;
import cn.mcmod.arsenal.item.rapier.RapierItem;
import cn.mcmod.arsenal.item.rapier.SmallswordItem;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<WeaponFrogItem> WEAPON_FROG;
    public static final Map<WeaponTier, RegistryObject<Item>> CHINESE_SWORD_SHEATH;
    public static final Map<WeaponTier, RegistryObject<Item>> CHINESE_SWORD;
    public static final Map<WeaponTier, RegistryObject<Item>> ANCIENT_SWORD_SHEATH;
    public static final Map<WeaponTier, RegistryObject<Item>> ANCIENT_SWORD;
    public static final Map<WeaponTier, RegistryObject<Item>> RAPIER_SCABBARD;
    public static final Map<WeaponTier, RegistryObject<Item>> RAPIER;
    public static final Map<WeaponTier, RegistryObject<Item>> SMALLSWORD_SCABBARD;
    public static final Map<WeaponTier, RegistryObject<Item>> SMALLSWORD;
    public static final Map<WeaponTier, RegistryObject<Item>> ARMING_SWORD_SCABBARD;
    public static final Map<WeaponTier, RegistryObject<Item>> ARMING_SWORD;
    public static final Map<WeaponTier, RegistryObject<Item>> LONGSWORD_SCABBARD;
    public static final Map<WeaponTier, RegistryObject<Item>> LONGSWORD;
    public static final RegistryObject<Item> XUANYUANJIAN_SHEATH;
    public static final RegistryObject<ChineseSwordItem> XUANYUANJIAN;

    public ItemRegistry() {
    }

    public static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }

    // 创建一个临时的 WeaponTier 对象用于注册
    private static final WeaponTier DUMMY_TIER = new BlankTier(
            "dummy",
            "arsenal_core",
            0, // 挖掘等级
            100, // 最大使用次数
            1.0F, // 效率
            1.0F, // 基础伤害
            0, // 附魔能力
            new ResourceLocation("forge:ingots/iron") // 修复材料标签
    );

    // 修改预注册方法，使用临时的 WeaponTier
    private static void registerAllWeapons() {
        // 为每种武器类型和每个可能的等级预先注册物品
        for (String tierName : new String[]{"wooden", "stone", "iron", "golden", "diamond", "netherite"}) {
            // 中国剑
            ITEMS.register(tierName + "_chinese_sword_sheath", SwordSheathItem::new);
            ITEMS.register(tierName + "_chinese_sword", () -> new ChineseSwordItem(DUMMY_TIER, ItemStack.EMPTY));

            // 古代剑
            ITEMS.register(tierName + "_ancient_sword_sheath", SwordSheathItem::new);
            ITEMS.register(tierName + "_ancient_sword", () -> new AncientSwordItem(DUMMY_TIER, ItemStack.EMPTY));

            // 刺剑
            ITEMS.register(tierName + "_rapier_scabbard", SwordSheathItem::new);
            ITEMS.register(tierName + "_rapier", () -> new RapierItem(DUMMY_TIER, ItemStack.EMPTY));

            // 小剑
            ITEMS.register(tierName + "_smallsword_scabbard", SwordSheathItem::new);
            ITEMS.register(tierName + "_smallsword", () -> new SmallswordItem(DUMMY_TIER, ItemStack.EMPTY));

            // 骑士剑
            ITEMS.register(tierName + "_arming_sword_scabbard", SwordSheathItem::new);
            ITEMS.register(tierName + "_arming_sword", () -> new ArmingSwordItem(DUMMY_TIER, ItemStack.EMPTY));

            // 长剑
            ITEMS.register(tierName + "_longsword_scabbard", SwordSheathItem::new);
            ITEMS.register(tierName + "_longsword", () -> new LongswordItem(DUMMY_TIER, ItemStack.EMPTY));
        }
    }


    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "arsenal_core");
        WEAPON_FROG = ITEMS.register("weapon_frog", WeaponFrogItem::new);

        // 预先注册所有可能的武器
        registerAllWeapons();

        // 直接注册的特殊武器
        XUANYUANJIAN_SHEATH = ITEMS.register("xuanyuanjian_sheath", () -> new SwordSheathItem(true));
        XUANYUANJIAN = ITEMS.register("xuanyuanjian", XuanyuanjianItem::new);

        // 创建映射关系，稍后填充
        CHINESE_SWORD_SHEATH = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_chinese_sword_sheath";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        CHINESE_SWORD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_chinese_sword";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        ANCIENT_SWORD_SHEATH = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_ancient_sword_sheath";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        ANCIENT_SWORD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_ancient_sword";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });
        RAPIER_SCABBARD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_rapier_scabbard";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        RAPIER = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_rapier";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        SMALLSWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_smallsword_scabbard";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        SMALLSWORD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_smallsword";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        ARMING_SWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_arming_sword_scabbard";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        ARMING_SWORD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_arming_sword";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        LONGSWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_longsword_scabbard";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

        LONGSWORD = ArsenalAPI.registerWeaponsAllTier((tier) -> {
            String registryName = tier.getUnlocalizedName() + "_longsword";
            return ITEMS.getEntries().stream()
                    .filter(entry -> entry.getId().getPath().equals(registryName))
                    .findFirst()
                    .orElse(null);
        });

    }

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        // 只处理物品注册表的映射
        if (event.getRegistry() != ForgeRegistries.ITEMS) {
            return;
        }

        for (MissingMappingsEvent.Mapping<Item> mapping : event.getAllMappings(ForgeRegistries.ITEMS.getRegistryKey())) {
            String path = mapping.getKey().getPath();
            // 处理特定的物品映射
            if (path.endsWith("_chinese_sword") || path.endsWith("_chinese_sword_sheath") ||
                    path.endsWith("_ancient_sword") || path.endsWith("_ancient_sword_sheath") ||
                    path.endsWith("_rapier") || path.endsWith("_rapier_scabbard") ||
                    path.endsWith("_smallsword") || path.endsWith("_smallsword_scabbard") ||
                    path.endsWith("_arming_sword") || path.endsWith("_arming_sword_scabbard") ||
                    path.endsWith("_longsword") || path.endsWith("_longsword_scabbard")) {

                // 尝试找到对应的新物品
                ITEMS.getEntries().stream()
                        .filter(entry -> entry.getId().getPath().equals(path))
                        .findFirst()
                        .ifPresent(obj -> mapping.remap(obj.get()));
            }
        }
    }
}