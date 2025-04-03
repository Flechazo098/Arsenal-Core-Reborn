//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.api.tier;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Set;

public class WeaponTierRegistry {
    private static final BiMap<String, WeaponTier> TIER_REG = HashBiMap.create();
    // 添加公共访问的 WEAPON_TIERS 字段
    public static final BiMap<String, WeaponTier> WEAPON_TIERS = TIER_REG;

    public WeaponTierRegistry() {
    }

    public static void register(String name, WeaponTier tier) {
        if (TIER_REG.containsKey(name)) {
            throw new IllegalArgumentException(String.format("The name %s has been registered twice.", name));
        } else {
            TIER_REG.put(name, tier);
        }
    }

    public static void register(WeaponTier tier) {
        register(tier.getUnlocalizedName(), tier);
    }

    public static void registerAll(WeaponTier... tiers) {
        for(WeaponTier tier : tiers) {
            register(tier.getUnlocalizedName(), tier);
        }

    }

    public static Set<WeaponTier> getWeaponTiers() {
        return TIER_REG.values();
    }

    public static Set<String> getTierName() {
        return TIER_REG.keySet();
    }

    public static WeaponTier getWeaponTier(String name) {
        return (WeaponTier)TIER_REG.get(name);
    }

    public static String getTierName(WeaponTier tier) {
        return (String)TIER_REG.inverse().get(tier);
    }
}
