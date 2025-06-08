package cn.mcmod.arsenal.item.feature;

import cn.mcmod.arsenal.api.WeaponFeature;
import cn.mcmod.arsenal.data.ComponentRegistry;
import cn.mcmod.arsenal.data.ReceivedComponent;
import cn.mcmod.arsenal.data.WeaponProgressComponent;
import cn.mcmod.arsenal.util.EnchantmentUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class XuanyuanFeature extends WeaponFeature {
    public XuanyuanFeature() {
        super("maximum_power");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isClientSide && entityIn instanceof ServerPlayer player) {
            // 初始化接收消息组件
            ReceivedComponent.XuanyuanData data = stack.get(ComponentRegistry.XUANYUAN_DATA.get());
            if (data == null || !data.receivedMessage()) {
                player.sendSystemMessage(Component.translatable("get_xuanyuan_message").withStyle(ChatFormatting.AQUA));
                stack.set(ComponentRegistry.XUANYUAN_DATA.get(), new ReceivedComponent.XuanyuanData(true));
            }

            // 初始化武器进度组件
            WeaponProgressComponent.WeaponProgress progress = stack.get(ComponentRegistry.WEAPON_PROGRESS.get());
            if (progress == null) {
                stack.set(ComponentRegistry.WEAPON_PROGRESS.get(),
                        new WeaponProgressComponent.WeaponProgress(0, 0, 0, 0));
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!(entity instanceof LivingEntity living)) return false;

        WeaponProgressComponent.WeaponProgress progress = stack.get(ComponentRegistry.WEAPON_PROGRESS.get());
        if (progress == null) return false;

        // Lv.1+ 火焰伤害
        if (progress.level() >= 1) {
            Holder<Enchantment> fireAspect = EnchantmentUtil.getHolder(Enchantments.FIRE_ASPECT);
            int fireLevel = 1 + stack.getEnchantmentLevel(fireAspect);
            living.igniteForSeconds(4 * fireLevel);
        }

        // Lv.2+ 对亡灵额外伤害
        if (progress.level() >= 2 && living.getType().is(EntityTypeTags.UNDEAD)) {
            Holder<Enchantment> smite = EnchantmentUtil.getHolder(Enchantments.SMITE);
            int smiteLevel = stack.getEnchantmentLevel(smite);
            float smiteDamage = Math.max(5.0F, 7.5F * smiteLevel * (1 + progress.level() * 0.5F));

            DamageSource magic = living.level().damageSources().magic();
            living.hurt(magic, smiteDamage);
        }

        // Lv.3+ 范围攻击
        if (progress.level() >= 3) {
            // TODO: 实现范围攻击逻辑
        }

        // TODO: 剩下的等级技能，粒子等

        return false;
    }

    public void onKillEntity(ItemStack stack, LivingEntity killed, Player killer) {
        WeaponProgressComponent.WeaponProgress progress = stack.get(ComponentRegistry.WEAPON_PROGRESS.get());
        if (progress == null) {
            progress = new WeaponProgressComponent.WeaponProgress(0, 0, 0, 0);
        }

        // 增加击杀数
        WeaponProgressComponent.WeaponProgress newProgress = progress.addKills(1);

        // 检查是否需要升级
        int newLevel = WeaponProgressComponent.calculateLevel(newProgress.kills());
        if (newLevel > progress.level()) {
            newProgress = newProgress.setLevel(newLevel);

            // 发送升级消息
            if (killer instanceof ServerPlayer serverPlayer) {
                serverPlayer.sendSystemMessage(
                        Component.translatable("arsenal.weapon.level.upgrade", newLevel,
                                        Component.translatable("arsenal.weapon.level." + newLevel))
                                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
                );
            }
        }

        stack.set(ComponentRegistry.WEAPON_PROGRESS.get(), newProgress);
    }


    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        return -1; // 不会损坏
    }
}