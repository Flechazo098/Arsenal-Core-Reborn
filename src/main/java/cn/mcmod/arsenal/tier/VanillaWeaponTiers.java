package cn.mcmod.arsenal.tier;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.api.tier.BlankTier;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.item.feature.XuanyuanFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.Tags.Items;

public final class VanillaWeaponTiers {
    public static WeaponTier WOOD;
    public static WeaponTier STONE;
    public static WeaponTier IRON;
    public static WeaponTier GOLD;
    public static WeaponTier DIAMOND;
    public static WeaponTier NETHERITE;
    public static WeaponTier COPPER;
    public static WeaponTier LAPIS;
    public static WeaponTier MAXIMUM_POWER;

    public VanillaWeaponTiers() {
    }

    // 静态初始化方法，使用默认值而不依赖配置
    public static synchronized void init() {
        // 避免重复初始化
        if (WOOD != null) return;

        WOOD = new BlankTier("wooden", Tiers.WOOD, 0, ItemTags.PLANKS.location());
        STONE = new BlankTier("stone", Tiers.STONE, 1, new ResourceLocation("forge:cobblestone"));
        IRON = new BlankTier("iron", Tiers.IRON, 2, new ResourceLocation("forge:ingots/iron"));
        GOLD = new BlankTier("golden", Tiers.GOLD, 0, new ResourceLocation("forge:ingots/gold"));
        DIAMOND = new BlankTier("diamond", Tiers.DIAMOND, 3, new ResourceLocation("forge:gems/diamond"));
        NETHERITE = new BlankTier("netherite", Tiers.NETHERITE, 4, new ResourceLocation("forge:ingots/netherite"));
        COPPER = new BlankTier("copper", "arsenal_core", 1, 200, 5.0F, 1.5F, 8, new ResourceLocation("forge:ingots/copper"));
        LAPIS = new BlankTier("lapis_lazuli", "arsenal_core", 2, 200, 2.0F, 2.0F, 40, Items.GEMS_LAPIS.location());

        // 对于依赖配置的部分，使用安全的默认值
        float maxPowerDamage = 8.0F;  // 默认值
        try {
            if (ArsenalConfig.MAXIMUM_POWER_DAMAGE != null) {
                maxPowerDamage = ArsenalConfig.MAXIMUM_POWER_DAMAGE.get().floatValue();
            }
        } catch (Exception e) {
            // 配置未加载，使用默认值
        }

        MAXIMUM_POWER = (new WeaponTier("maximum_power", "arsenal_core", 5, -1, 8.0F, maxPowerDamage, 50,
                new ResourceLocation("forge:gems/diamond"), new XuanyuanFeature())).setSpecial();
    }
}