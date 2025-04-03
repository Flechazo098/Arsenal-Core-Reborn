
package cn.mcmod.arsenal.api.tier;

import cn.mcmod.arsenal.api.WeaponFeature;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class BlankTier extends WeaponTier {
    public BlankTier(String unlocName, String modId, int miningLevel, int maxUses, float efficiency, float baseDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        super(unlocName, modId, miningLevel, maxUses, efficiency, baseDamage, enchantability, repairMaterial, (WeaponFeature)null);
    }

    public BlankTier(String unlocName, String modId, int miningLevel, int maxUses, float efficiency, float baseDamage, int enchantability, ResourceLocation tagName) {
        this(unlocName, modId, miningLevel, maxUses, efficiency, baseDamage, enchantability, () -> Ingredient.of(ItemTags.create(tagName)));
    }

    public BlankTier(String unlocName, int miningLevel, int maxUses, float efficiency, float baseDamage, int enchantability, ResourceLocation tagName) {
        this(unlocName, "arsenal_core", miningLevel, maxUses, efficiency, baseDamage, enchantability, () -> Ingredient.of(ItemTags.create(tagName)));
    }


    public BlankTier(String unlocName, String modId, Tier itemTier, int miningLevel, ResourceLocation tagName) {
        this(unlocName, modId, miningLevel, itemTier.getUses(), itemTier.getSpeed(), itemTier.getAttackDamageBonus(), itemTier.getEnchantmentValue(), () -> Ingredient.of(ItemTags.create(tagName)));
    }

    public BlankTier(String unlocName, Tier itemTier, int miningLevel, ResourceLocation tagName) {
        this(unlocName, "arsenal_core", miningLevel, itemTier.getUses(), itemTier.getSpeed(), itemTier.getAttackDamageBonus(), itemTier.getEnchantmentValue(), () -> Ingredient.of(ItemTags.create(tagName)));
    }

    public BlankTier(String unlocName, int miningLevel, int maxUses, float efficiency, float baseDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this(unlocName, "arsenal_core", miningLevel, maxUses, efficiency, baseDamage, enchantability, repairMaterial);
    }


    public BlankTier(String unlocName, String modId, Tier itemTier, int miningLevel, Supplier<Ingredient> repairMaterial) {
        this(unlocName, modId, miningLevel, itemTier.getUses(), itemTier.getSpeed(), itemTier.getAttackDamageBonus(), itemTier.getEnchantmentValue(), repairMaterial);
    }

    public BlankTier(String unlocName, Tier itemTier, int miningLevel, Supplier<Ingredient> repairMaterial) {
        this(unlocName, "arsenal_core", itemTier, miningLevel, repairMaterial);
    }
}