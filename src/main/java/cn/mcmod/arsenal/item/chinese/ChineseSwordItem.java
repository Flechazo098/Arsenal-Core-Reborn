package cn.mcmod.arsenal.item.chinese;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.api.WeaponFeature;
import cn.mcmod.arsenal.api.toolmaterial.IWeaponToolMaterial;
import cn.mcmod.arsenal.api.toolmaterial.WeaponToolMaterial;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class ChineseSwordItem extends SwordItem implements IDrawable, IWeaponToolMaterial {
    private final WeaponToolMaterial toolMaterial;
    private final ItemStack sheath;
    private final int attackDamage;
    private final float attackSpeed;

    public ChineseSwordItem(WeaponToolMaterial toolMaterial, int attackDamageIn, float attackSpeedIn, ItemStack sheathItem, Item.Properties props) {
        super(toolMaterial.getToolMaterial(), attackDamageIn, attackSpeedIn, toolMaterial.getToolMaterial().applySwordProperties(props, attackDamageIn, attackSpeedIn));
        this.toolMaterial = toolMaterial;
        this.sheath = sheathItem;
        this.attackDamage = attackDamageIn;
        this.attackSpeed = attackSpeedIn;
    }


    @Deprecated
    public ChineseSwordItem(WeaponToolMaterial toolMaterial, int attackDamageIn, float attackSpeedIn, ItemStack sheathItem) {
        this(toolMaterial, attackDamageIn, attackSpeedIn, sheathItem, new Item.Properties().stacksTo(1));
    }



    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, pContext, tooltip, flag);

        WeaponToolMaterial tier = getWeaponToolMaterial(stack);

        MutableComponent tierText = Component.translatable("tooltip.arsenal.tiers")
                .append(Component.translatable("tier.arsenal." + tier.getUnlocalizedName()));
        tooltip.add(tierText);

        WeaponFeature feature = getFeature(stack);
        if (feature != null) {
            MutableComponent featureText = Component.translatable("tooltip.arsenal.feature." + feature.getName())
                    .withStyle(ChatFormatting.GOLD);
            tooltip.add(featureText);
        }
    }


    @Override
    public boolean isFoil(ItemStack stack) {
        return ArsenalConfig.normal_sword_foil && super.isFoil(stack);
    }

    @Override
    public ItemStack getSheath(ItemStack stack) {
        return this.sheath;
    }

    @Override
    public WeaponToolMaterial getWeaponToolMaterial (ItemStack var1) {
        return this.toolMaterial;
    }

    @Override
    public WeaponFeature getFeature(ItemStack stack) {
        return this.getWeaponToolMaterial(stack).getFeature();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (this.getFeature(stack) != null) {
            this.getFeature(stack).inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        if (this.getFeature(stack) != null) {
            int feature_damage = this.getFeature(stack).damageItem(stack, amount, entity, onBroken);
            return super.damageItem(stack, amount, entity, onBroken) + feature_damage;
        } else {
            return super.damageItem(stack, amount, entity, onBroken);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        boolean result = super.onLeftClickEntity(stack, player, entity);
        if (this.getFeature(stack) == null) {
            return result;
        } else {
            return result || this.getFeature(stack).onLeftClickEntity(stack, player, entity);
        }
    }
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);

        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
            if (offhand.getItem().canPerformAction(stackInHand, ItemAbilities.SHIELD_BLOCK)) {
                player.startUsingItem(InteractionHand.OFF_HAND);
                return InteractionResult.CONSUME.heldItemTransformedTo(stackInHand);
            }
        }

        player.startUsingItem(hand);
        return InteractionResult.SUCCESS.heldItemTransformedTo(stackInHand);
    }


    @Override
    public ItemUseAnimation getUseAnimation(ItemStack p_43105_) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration (ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility toolAction) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }
}