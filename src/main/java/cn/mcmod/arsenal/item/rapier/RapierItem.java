//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.item.rapier;

import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.api.WeaponFeature;
import cn.mcmod.arsenal.api.tier.IWeaponTiered;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RapierItem extends TieredItem implements Vanishable, IDrawable, IWeaponTiered {
    private final WeaponTier tier;
    private ItemStack sheath;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public RapierItem(WeaponTier tier, int attackDamageIn, float attackSpeedIn, ItemStack sheathItem, Properties prop) {
        super(tier, prop);
        this.tier = tier;
        this.sheath = sheathItem;
        this.attackDamage = Math.max((float)attackDamageIn + tier.getAttackDamageBonus(), tier.getAttackDamageBonus());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedIn, Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public RapierItem(WeaponTier tier, ItemStack sheathItem, Properties prop) {
        this(tier, 2, -1.25F, sheathItem, prop);
    }

    public RapierItem(WeaponTier tier, ItemStack sheathItem) {
        this(tier, 2, -1.25F, sheathItem, (new Properties()).stacksTo(1));
    }

    public float getDamage() {
        return this.attackDamage;
    }

    public ItemStack getSheath(ItemStack stack) {
        return this.sheath;
    }

    public WeaponTier getWeaponTier(ItemStack stack) {
        return this.tier;
    }

    public int getMaxDamage(ItemStack stack) {
        return (int)((float)this.getWeaponTier(stack).getUses() * 0.85F);
    }

    public WeaponFeature getFeature(ItemStack stack) {
        return this.getWeaponTier(stack).getFeature();
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON && enchantment != Enchantments.SWEEPING_EDGE || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
        ItemStack itemStackIn = player.getItemInHand(handIn);
        player.startUsingItem(handIn);
        if (!player.isShiftKeyDown()) {
            float f = 0.25F;
            float motionX = Mth.sin(player.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float)Math.PI) * f;
            float motionZ = -Mth.cos(player.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float)Math.PI) * f;
            player.push(motionX, 0.2F, motionZ);
        } else {
            float f = 0.5F;
            float motionX = Mth.sin(player.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float)Math.PI) * f;
            float motionZ = -Mth.cos(player.getYRot() / 180.0F * (float)Math.PI) * Mth.cos(player.getXRot() / 180.0F * (float)Math.PI) * f;
            player.push(motionX, 0.1F, motionZ);
        }

        player.getCooldowns().addCooldown(this, 20);
        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        return InteractionResultHolder.sidedSuccess(itemStackIn, world.isClientSide());
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (entity) -> {
            entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    // 添加 ItemStack 敏感版本的方法
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

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

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        boolean result = super.onLeftClickEntity(stack, player, entity);
        if (this.getFeature(stack) == null) {
            return result;
        } else {
            return result || this.getFeature(stack).onLeftClickEntity(stack, player, entity);
        }
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (this.getFeature(stack) != null) {
            this.getFeature(stack).inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (this.getFeature(stack) != null) {
            int feature_damage = this.getFeature(stack).damageItem(stack, amount, entity, onBroken);
            return super.damageItem(stack, amount, entity, onBroken) + feature_damage;
        } else {
            return super.damageItem(stack, amount, entity, onBroken);
        }
    }

    public void DoStingAttack(ItemStack stack, LivingEntity attacker, LivingEntity target) {
        if (stack.getItem() instanceof TieredItem rapier) {
            DoStingAttack(stack, rapier.getTier().getAttackDamageBonus(), EnchantmentHelper.getDamageBonus(stack, target.getMobType()), attacker, target);
        }
    }

    public static void DoStingAttack(ItemStack stack, float baseDamage, float exDamage, LivingEntity attacker, LivingEntity target) {
        if (!target.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            return;
        }

        boolean isPlayer = attacker instanceof Player;
        float f = baseDamage;
        float f1 = exDamage;

        if (isPlayer) {
            float f2 = ((Player)attacker).getAttackStrengthScale(0.5F);
            f = baseDamage * (0.2F + f2 * f2 * 0.8F);
            f1 = exDamage * f2;
        }

        f += f1;
        boolean flag5 = isPlayer
                ? target.hurt(attacker.damageSources().playerAttack((Player)attacker), f)
                : target.hurt(attacker.damageSources().mobAttack(attacker), f);

        if (flag5) {
            target.invulnerableTime = 0;
            target.level().playSound(null, target, SoundEvents.PLAYER_ATTACK_CRIT, target.getSoundSource(),
                    Math.max(1.0F, f), (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.5F + 1.0F);
        }
    }

}