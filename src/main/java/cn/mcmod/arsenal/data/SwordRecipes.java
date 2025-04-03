//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.tier.VanillaWeaponTiers;

import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.registries.RegistryObject;

public class SwordRecipes extends RecipeProvider {
    public SwordRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // 确保 VanillaWeaponTiers 已初始化
        if (! ArsenalCore.isConfigLoaded()) {
            VanillaWeaponTiers.init();
        }
        // 添加 WEAPON_FROG 配方
        if (ItemRegistry.WEAPON_FROG != null) {
            ItemRegistry.WEAPON_FROG.get();
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.WEAPON_FROG.get())
                    .pattern("I I")
                    .pattern("ILI")
                    .pattern(" I ")
                    .define('I', Ingredient.of(Items.LEATHER))
                    .define('L', Ingredient.of(Items.RODS_WOODEN))
                    .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STICK))
                    .save(consumer);
        }
        // 添加 XUANYUANJIAN 配方，增加安全检查
        if (ItemRegistry.XUANYUANJIAN != null) {
            ItemRegistry.XUANYUANJIAN.get();
            if (VanillaWeaponTiers.NETHERITE != null && ItemRegistry.ANCIENT_SWORD != null && ItemRegistry.ANCIENT_SWORD.containsKey(VanillaWeaponTiers.NETHERITE) && ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.NETHERITE) != null && ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.NETHERITE).get() != null) {

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.XUANYUANJIAN.get())
                        .pattern("IDI")
                        .pattern("SLS")
                        .pattern("ISI")
                        .define('I', Ingredient.of(Items.STORAGE_BLOCKS_DIAMOND))
                        .define('L', Ingredient.of(ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.NETHERITE).get()))
                        .define('D', Ingredient.of(net.minecraft.world.item.Items.DRAGON_EGG))
                        .define('S', Ingredient.of(Items.NETHER_STARS))
                        .unlockedBy("ingredient", has(net.minecraft.world.item.Items.DRAGON_EGG))
                        .save(consumer);
            }
        }

        // 为中国剑添加安全检查
        if (safeCheckTier(VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.WOOD)) {

            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.WOOD).get(),
                    Ingredient.of(ItemTags.PLANKS),
                    Ingredient.of(net.minecraft.world.item.Items.WOODEN_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.STONE)) {
            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.STONE).get(),
                    Ingredient.of(Tags.Items.COBBLESTONE),
                    Ingredient.of(net.minecraft.world.item.Items.STONE_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.IRON)) {
            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.IRON).get(),
                    Ingredient.of(Tags.Items.INGOTS_IRON),
                    Ingredient.of(net.minecraft.world.item.Items.IRON_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.GOLD)) {
            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.GOLD).get(),
                    Ingredient.of(Tags.Items.INGOTS_GOLD),
                    Ingredient.of(net.minecraft.world.item.Items.GOLDEN_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND)) {
            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.DIAMOND).get(),
                    Ingredient.of(Tags.Items.GEMS_DIAMOND),
                    Ingredient.of(net.minecraft.world.item.Items.DIAMOND_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE)) {
            this.registerChineseSwordRecipes(consumer,
                    ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.CHINESE_SWORD_SHEATH.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.ANCIENT_SWORD_SHEATH.get(VanillaWeaponTiers.NETHERITE).get(),
                    Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                    Ingredient.of(net.minecraft.world.item.Items.DIAMOND_SWORD));
        }

        if (safeCheckTier(VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.WOOD)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.WOOD).get(),
                    Ingredient.of(ItemTags.PLANKS),
                    Ingredient.of(net.minecraft.world.item.Items.WOODEN_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.STONE)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.STONE).get(),
                    Ingredient.of(Tags.Items.COBBLESTONE),
                    Ingredient.of(net.minecraft.world.item.Items.STONE_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.IRON)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.IRON).get(),
                    Ingredient.of(Tags.Items.INGOTS_IRON),
                    Ingredient.of(net.minecraft.world.item.Items.IRON_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.GOLD)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.GOLD).get(),
                    Ingredient.of(Tags.Items.INGOTS_GOLD),
                    Ingredient.of(net.minecraft.world.item.Items.GOLDEN_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.DIAMOND).get(),
                    Ingredient.of(Tags.Items.GEMS_DIAMOND),
                    Ingredient.of(net.minecraft.world.item.Items.DIAMOND_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE)) {
            this.registerRapierSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.NETHERITE).get(),
                    Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                    Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.WOOD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.WOOD)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.WOOD).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.WOOD).get(),
                    Ingredient.of(ItemTags.PLANKS),
                    Ingredient.of(net.minecraft.world.item.Items.WOODEN_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.STONE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.STONE)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.STONE).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.STONE).get(),
                    Ingredient.of(Tags.Items.COBBLESTONE),
                    Ingredient.of(net.minecraft.world.item.Items.STONE_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.IRON) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.IRON)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.IRON).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.IRON).get(),
                    Ingredient.of(Tags.Items.INGOTS_IRON),
                    Ingredient.of(net.minecraft.world.item.Items.IRON_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.GOLD) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.GOLD)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.GOLD).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.GOLD).get(),
                    Ingredient.of(Tags.Items.INGOTS_GOLD),
                    Ingredient.of(net.minecraft.world.item.Items.GOLDEN_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.DIAMOND)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.DIAMOND).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.DIAMOND).get(),
                    Ingredient.of(Tags.Items.GEMS_DIAMOND),
                    Ingredient.of(net.minecraft.world.item.Items.DIAMOND_SWORD)
            );
        }

        if (safeCheckTier(VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD_SHEATH, VanillaWeaponTiers.NETHERITE)) {
            this.registerArmingSwordRecipes(
                    consumer,
                    ItemRegistry.RAPIER.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.RAPIER_SCABBARD.get(VanillaWeaponTiers.NETHERITE).get(),
                    ItemRegistry.SMALLSWORD_SCABBARD.get(VanillaWeaponTiers.NETHERITE).get(),
                    Ingredient.of(Tags.Items.INGOTS_NETHERITE),
                    Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SWORD)
            );
        }

        // Add null checks for netheriteSmithing calls
        if (safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.CHINESE_SWORD, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.CHINESE_SWORD.get(VanillaWeaponTiers.NETHERITE).get());
        }

        if (safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ANCIENT_SWORD, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.ANCIENT_SWORD.get(VanillaWeaponTiers.NETHERITE).get());
        }

        if (safeCheckItem(ItemRegistry.RAPIER, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.RAPIER, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.RAPIER.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.RAPIER.get(VanillaWeaponTiers.NETHERITE).get());
        }

        if (safeCheckItem(ItemRegistry.SMALLSWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.SMALLSWORD, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.SMALLSWORD.get(VanillaWeaponTiers.NETHERITE).get());
        }

        if (safeCheckItem(ItemRegistry.ARMING_SWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.ARMING_SWORD, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.ARMING_SWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.ARMING_SWORD.get(VanillaWeaponTiers.NETHERITE).get());
        }

        if (safeCheckItem(ItemRegistry.LONGSWORD, VanillaWeaponTiers.DIAMOND) &&
                safeCheckItem(ItemRegistry.LONGSWORD, VanillaWeaponTiers.NETHERITE)) {
            netheriteSmithing(consumer, ItemRegistry.LONGSWORD.get(VanillaWeaponTiers.DIAMOND).get(),
                    RecipeCategory.COMBAT, ItemRegistry.LONGSWORD.get(VanillaWeaponTiers.NETHERITE).get());
        }
    }

    // 添加辅助方法来检查武器等级是否安全
    private boolean safeCheckTier(WeaponTier tier) {
        return tier != null;
    }

    // 添加辅助方法来检查物品映射是否安全
    private <T> boolean safeCheckItem(Map<WeaponTier, RegistryObject<T>> itemMap, WeaponTier tier) {
        if (itemMap == null ||
                ! itemMap.containsKey(tier) ||
                itemMap.get(tier) == null) return false;
        itemMap.get(tier).get();
        return true;
    }

    private void registerChineseSwordRecipes(Consumer<FinishedRecipe> consumer, ItemLike out_chinese, ItemLike out_ancient, ItemLike sheath_chinese, ItemLike sheath_ancient, Ingredient material, Ingredient sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_ancient)
                .pattern("  I")
                .pattern(" L ")
                .pattern("PS ")
                .define('I', material)
                .define('L', Ingredient.of(ItemTags.LOGS))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .define('S', Ingredient.of(Tags.Items.STRING))
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_chinese)
                .pattern(" IL")
                .pattern(" L ")
                .pattern("PS ")
                .define('I', material)
                .define('L', Ingredient.of(ItemTags.LOGS))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .define('S', Ingredient.of(Tags.Items.STRING))
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_ancient)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_ancient)
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_chinese)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_chinese)
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);
    }

    private void registerRapierSwordRecipes(Consumer<FinishedRecipe> consumer, ItemLike out_chinese, ItemLike out_ancient, ItemLike sheath_chinese, ItemLike sheath_ancient, Ingredient material, Ingredient sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_ancient)
                .pattern("  I")
                .pattern(" L ")
                .pattern("PS ")
                .define('I', material)
                .define('L', Ingredient.of(ItemTags.LOGS))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .define('S', Ingredient.of(Tags.Items.LEATHER))
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_chinese)
                .pattern(" IL")
                .pattern(" L ")
                .pattern("PS ")
                .define('I', material)
                .define('L', Ingredient.of(ItemTags.LOGS))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .define('S', Ingredient.of(Tags.Items.LEATHER))
                .unlockedBy("ingredient", has(Items.STRING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_ancient)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_ancient)
                .unlockedBy("ingredient", has(Items.STRING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_chinese)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_chinese)
                .unlockedBy("ingredient", has(Items.STRING))
                .save(consumer);
    }

    private void registerArmingSwordRecipes(Consumer<FinishedRecipe> consumer, ItemLike out_chinese, ItemLike out_ancient, ItemLike sheath_chinese, ItemLike sheath_ancient, Ingredient material, Ingredient sword) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_ancient)
                .pattern(" IL")
                .pattern(" L ")
                .pattern("P  ")
                .define('I', material)
                .define('L', Ingredient.of(Tags.Items.LEATHER))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sheath_chinese)
                .pattern("  I")
                .pattern(" L ")
                .pattern("P  ")
                .define('I', material)
                .define('L', Ingredient.of(Tags.Items.LEATHER))
                .define('P', Ingredient.of(ItemTags.PLANKS))
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_ancient)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_ancient)
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, out_chinese)
                .pattern(" S")
                .pattern("B ")
                .define('B', sword)
                .define('S', sheath_chinese)
                .unlockedBy("ingredient", has(net.minecraft.world.item.Items.STRING))
                .save(consumer);
    }
}
