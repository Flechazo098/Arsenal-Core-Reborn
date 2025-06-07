package cn.mcmod.arsenal.tier;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.api.tier.BlankTier;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.item.feature.XuanyuanFeature;
import java.util.function.Supplier;

import cn.mcmod.arsenal.util.Lazy;
import com.voxelutopia.ultramarine.data.ModTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.Tags.Items;

public final class VanillaWeaponTiers {
    public static final Supplier<WeaponTier> WOOD = new Lazy<>(() -> new BlankTier("wooden", Tiers.WOOD, ItemTags.PLANKS.location()));
    public static final Supplier<WeaponTier> STONE = new Lazy<>(() -> new BlankTier("stone", Tiers.STONE, new ResourceLocation("forge:cobblestone")));
    public static final Supplier<WeaponTier> IRON = new Lazy<>(() -> new BlankTier("iron", Tiers.IRON, new ResourceLocation("forge:ingots/iron")));
    public static final Supplier<WeaponTier> GOLD = new Lazy<>(() -> new BlankTier("golden", Tiers.GOLD, new ResourceLocation("forge:ingots/gold")));
    public static final Supplier<WeaponTier> DIAMOND = new Lazy<>(() -> new BlankTier("diamond", Tiers.DIAMOND, new ResourceLocation("forge:gems/diamond")));
    public static final Supplier<WeaponTier> NETHERITE = new Lazy<>(() -> new BlankTier("netherite", Tiers.NETHERITE, new ResourceLocation("forge:ingots/netherite")));
    public static final Supplier<WeaponTier> COPPER = new Lazy<>(() -> new BlankTier("copper", "arsenal_core", 1, 200, 5.0F, 1.5F, 8, new ResourceLocation("forge:ingots/copper")));
    public static final Supplier<WeaponTier> LAPIS = new Lazy<>(() -> new BlankTier("lapis_lazuli", "arsenal_core", 2, 200, 2.0F, 2.0F, 40, Items.GEMS_LAPIS.location()));

    public static final Supplier<WeaponTier> BLUE_AND_WHITE_PORCELAIN = new Lazy<>(() -> new BlankTier("blue_and_white_porcelain", ModTiers.BLUE_AND_WHITE_PORCELAIN, new ResourceLocation("forge:blues_and_white_porcelain")));
    public static final Supplier<WeaponTier> MAXIMUM_POWER = new Lazy<>(() -> (new WeaponTier("maximum_power", "arsenal_core", 5, -1, 8.0F, (float) ArsenalConfig.maximum_power_damage, 50, new ResourceLocation("forge:gems/diamond"), new XuanyuanFeature())).setSpecial());

}