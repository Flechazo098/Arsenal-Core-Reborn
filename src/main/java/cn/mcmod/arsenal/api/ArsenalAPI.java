package cn.mcmod.arsenal.api;

import cn.mcmod.arsenal.api.tier.IWeaponTiered;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.api.tier.WeaponTierRegistry;
import cn.mcmod.arsenal.item.ItemRegistry;
import com.google.common.collect.Maps;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public final class ArsenalAPI {
    private static final Logger LOGGER = LogManager.getLogger();

    // 存储武器等级与物品的映射关系
    private static final Map<String, WeaponRegistryInfo<?>> WEAPON_REGISTRY_INFO = Maps.newHashMap();

    public ArsenalAPI() {
    }

    /**
     * 注册所有等级的武器，返回一个空Map，将在武器等级初始化后填充
     */
    public static <V> Map<WeaponTier, V> registerWeaponsAllTier(Function<WeaponTier, V> valueMapper) {
        return registerWeaponsAllTier((tier) -> !tier.isSpecial(), valueMapper);
    }

    /**
     * 注册符合条件的武器等级，返回一个空Map，将在武器等级初始化后填充
     */
    public static <V> Map<WeaponTier, V> registerWeaponsAllTier(Predicate<WeaponTier> keyPredicate, Function<WeaponTier, V> valueMapper) {
        // 创建一个空Map，稍后填充
        Map<WeaponTier, V> resultMap = Maps.newHashMap();

        // 记录注册信息
        String registryKey = "weapon_" + System.nanoTime();
        WEAPON_REGISTRY_INFO.put(registryKey, new WeaponRegistryInfo<>(resultMap, keyPredicate, valueMapper));

        return resultMap;
    }

    /**
     * 在武器等级初始化后调用此方法，填充之前创建的空Map
     */
    public static void initializeWeaponRegistries() {
        LOGGER.info("初始化武器注册表...");

        if (WeaponTierRegistry.getWeaponTiers().isEmpty()) {
            LOGGER.warn("武器等级注册表为空，无法初始化武器注册表！");
            return;
        }

        // 填充所有注册的Map
        for (WeaponRegistryInfo<?> info : WEAPON_REGISTRY_INFO.values()) {
            fillWeaponMap(info);
        }

        LOGGER.info("武器注册表初始化完成，共处理 {} 个武器类型", WEAPON_REGISTRY_INFO.size());
    }

    /**
     * 填充武器Map
     */
    @SuppressWarnings("unchecked")
    private static <V> void fillWeaponMap(WeaponRegistryInfo<?> info) {
        WeaponRegistryInfo<V> typedInfo = (WeaponRegistryInfo<V>) info;
        Map<WeaponTier, V> resultMap = typedInfo.resultMap;

        for (WeaponTier tier : WeaponTierRegistry.getWeaponTiers()) {
            if (typedInfo.keyPredicate.test(tier)) {
                try {
                    // 这里不会创建新的注册，只是获取已经注册的对象
                    V value = typedInfo.valueMapper.apply(tier);
                    resultMap.put(tier, value);
                    LOGGER.debug("已关联武器等级: {}", tier.getUnlocalizedName());
                } catch (Exception e) {
                    LOGGER.error("关联武器等级 {} 时出错: {}", tier.getUnlocalizedName(), e.getMessage());
                }
            }
        }
    }

    /**
     * 存储武器注册信息的内部类
     */
    private static class WeaponRegistryInfo<V> {
        final Map<WeaponTier, V> resultMap;
        final Predicate<WeaponTier> keyPredicate;
        final Function<WeaponTier, V> valueMapper;

        WeaponRegistryInfo(Map<WeaponTier, V> resultMap, Predicate<WeaponTier> keyPredicate, Function<WeaponTier, V> valueMapper) {
            this.resultMap = resultMap;
            this.keyPredicate = keyPredicate;
            this.valueMapper = valueMapper;
        }
    }

    /**
     * 更新已注册武器的等级
     */
    public static void updateWeaponTiers() {
        LOGGER.info("更新武器等级...");

        // 遍历所有已注册的武器
        for (RegistryObject<Item> entry : ItemRegistry.ITEMS.getEntries()) {
            Item item = entry.get();
            if (item instanceof IWeaponTiered weaponItem) {
                String path = entry.getId().getPath();
                int underscoreIndex = path.indexOf("_");
                if (underscoreIndex > 0) {
                    String tierName = path.substring(0, underscoreIndex);
                    WeaponTier tier = WeaponTierRegistry.getWeaponTier(tierName);
                    if (tier != null) {
                        try {
                            // 使用反射获取所有字段，包括父类的字段
                            Field tierField = null;
                            Class<?> currentClass = item.getClass();

                            // 搜索类层次结构中的 tier 字段
                            while (currentClass != null && tierField == null) {
                                try {
                                    for (Field field : currentClass.getDeclaredFields()) {
                                        if (field.getType() == WeaponTier.class ||
                                                field.getType() == Tier.class) {
                                            tierField = field;
                                            break;
                                        }
                                    }
                                    currentClass = currentClass.getSuperclass();
                                } catch (Exception e) {
                                    currentClass = currentClass.getSuperclass();
                                }
                            }

                            if (tierField != null) {
                                tierField.setAccessible(true);
                                tierField.set(item, tier);
                                LOGGER.debug("已更新武器 {} 的等级为 {}", path, tierName);
                            } else {
                                // 如果找不到字段，尝试使用其他方法更新等级
                                if (item instanceof TieredItem tieredItem) {
                                    // 使用反射获取 TieredItem 中的 tier 字段
                                    Field tieredItemField = TieredItem.class.getDeclaredField("tier");
                                    tieredItemField.setAccessible(true);
                                    tieredItemField.set(tieredItem, tier);
                                    LOGGER.debug("通过 TieredItem 更新武器 {} 的等级为 {}", path, tierName);
                                } else {
                                    LOGGER.warn("无法找到武器 {} 的等级字段", path);
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error("更新武器 {} 的等级时出错: {}", path, e.getMessage());
                            // 打印详细堆栈信息以便调试
                            if (LOGGER.isDebugEnabled()) {
                                StringWriter sw = new StringWriter();
                                e.printStackTrace(new PrintWriter(sw));
                                LOGGER.debug("详细错误: {}", sw.toString());
                            }
                        }
                    } else {
                        LOGGER.warn("找不到等级 {} 对应的 WeaponTier", tierName);
                    }
                }
            }
        }

        LOGGER.info("武器等级更新完成");
    }
}