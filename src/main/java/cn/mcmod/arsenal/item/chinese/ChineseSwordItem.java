//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.item.chinese;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.api.WeaponFeature;
import cn.mcmod.arsenal.api.tier.IWeaponTiered;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ChineseSwordItem extends SwordItem implements IDrawable, IWeaponTiered {
    private final WeaponTier tier;
    private final ItemStack sheath;

    public ChineseSwordItem(WeaponTier tier, int attackDamageIn, float attackSpeedIn, ItemStack sheathItem, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.tier = tier;
        this.sheath = sheathItem;
    }

    public ChineseSwordItem(WeaponTier tier, int attackDamageIn, float attackSpeedIn, ItemStack sheathItem) {
        this(tier, attackDamageIn, attackSpeedIn, sheathItem, new Properties().stacksTo(1));
    }

    public ChineseSwordItem(WeaponTier tier, ItemStack sheathItem, Properties builderIn) {
        this(tier, 4, -1.8F, sheathItem, builderIn);
    }

    public ChineseSwordItem(WeaponTier tier, ItemStack sheathItem) {
        // 同样移除 tab 方法调用
        this(tier, 4, -1.8F, sheathItem, new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stackIn, Level levelIn, List<Component> tooltipIn, TooltipFlag flagIn) {
        super.appendHoverText(stackIn, levelIn, tooltipIn, flagIn);
        Component tierText = Component.translatable("tooltip.arsenal.tiers")
                .append(Component.translatable("tier.arsenal." + this.getWeaponTier(stackIn).getUnlocalizedName()));
        tooltipIn.add(tierText);

        if (this.getFeature(stackIn) != null) {
            Component featureText = Component.translatable("tooltip.arsenal.feature." + this.getWeaponTier(stackIn).getFeature().getName())
                    .withStyle(ChatFormatting.GOLD);
            tooltipIn.add(featureText);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ArsenalConfig.NORMAL_SWORD_FOIL.get() && super.isFoil(stack);
    }

    public ItemStack getSheath(ItemStack stack) {
        return this.sheath;
    }

    public WeaponTier getWeaponTier(ItemStack stack) {
        return this.tier;
    }

    public WeaponFeature getFeature(ItemStack stack) {
        return this.getWeaponTier(stack).getFeature();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (this.getFeature(stack) != null) {
            this.getFeature(stack).inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
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
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (handIn == InteractionHand.MAIN_HAND) {
            ItemStack off_hand = playerIn.getItemInHand(InteractionHand.OFF_HAND);
            if (off_hand.getItem().canPerformAction(itemstack, ToolActions.SHIELD_BLOCK)) {
                playerIn.startUsingItem(InteractionHand.OFF_HAND);
                return InteractionResultHolder.consume(itemstack);
            }
        }

        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stackIn) {
        return 72000;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }
}