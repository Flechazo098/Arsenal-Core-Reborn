package cn.mcmod.arsenal.item.chinese;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.data.ComponentRegistry;
import cn.mcmod.arsenal.data.WeaponProgressComponent;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.tier.VanillaWeaponToolMaterials;
import java.util.List;

import cn.mcmod.arsenal.util.ArsenalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class XuanyuanjianItem extends ChineseSwordItem {
    public XuanyuanjianItem(Item.Properties properties) {
        super(VanillaWeaponToolMaterials.MAXIMUM_POWER.get(), 5, -1.8F, new ItemStack(ItemRegistry.XUANYUANJIAN_SHEATH.get()), properties.stacksTo(1));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        WeaponProgressComponent.WeaponProgress progress = stack.get(ComponentRegistry.WEAPON_PROGRESS.get());

        if (progress != null && progress.level() >= 3) {
            long currentTime = level.getGameTime();

            if (currentTime - progress.lastUseTime() >= (progress.level() >= 4 ? 300 : 400)) {
                if (progress.level() >= 5) {
                    triggerTianGuangJianYu(level, player);
                } else {
                    triggerBasicSkill(level, player);
                }

                stack.set(ComponentRegistry.WEAPON_PROGRESS.get(),
                        progress.setLastUseTime(currentTime));

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(level, player, hand);
    }

    private void triggerTianGuangJianYu(Level level, Player player) {
        // TODO: 实现天光剑狱技能
        ArsenalUtil.sendServerMessage(player, Component.translatable("arsenal.weapon.skill.tianguangjianyu")
                .withStyle(ChatFormatting.GOLD));
    }

    private void triggerBasicSkill(Level level, Player player) {
        // TODO: 实现基础技能
        ArsenalUtil.sendServerMessage(player, Component.translatable("arsenal.weapon.skill.jianqibaofa")
                .withStyle(ChatFormatting.YELLOW));
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        WeaponProgressComponent.WeaponProgress progress = stack.get(ComponentRegistry.WEAPON_PROGRESS.get());
        int level = progress != null ? progress.level() : 0;

        float attackSpeed = WeaponProgressComponent.ATTACK_SPEEDS[Math.min(level, WeaponProgressComponent.ATTACK_SPEEDS.length - 1)];
        int attackDamage = WeaponProgressComponent.ATTACK_DAMAGES[Math.min(level, WeaponProgressComponent.ATTACK_DAMAGES.length - 1)];

        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage - 1, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean isCombineRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stackIn, Item.TooltipContext pContext, List<Component> tooltipIn, TooltipFlag flagIn) {
        super.appendHoverText(stackIn, pContext, tooltipIn, flagIn);

        WeaponProgressComponent.WeaponProgress progress = stackIn.get(ComponentRegistry.WEAPON_PROGRESS.get());
        if (progress != null) {
            int level = progress.level();
            int kills = progress.kills();

            tooltipIn.add(Component.translatable("arsenal.weapon.level.prefix", level)
                    .withStyle(ChatFormatting.GOLD));
            tooltipIn.add(Component.translatable("arsenal.weapon.kills", kills)
                    .withStyle(ChatFormatting.GREEN));

            if (level < WeaponProgressComponent.LEVEL_THRESHOLDS.length - 1) {
                int nextThreshold = WeaponProgressComponent.LEVEL_THRESHOLDS[level + 1];
                tooltipIn.add(Component.translatable("arsenal.weapon.next_stage", kills, nextThreshold)
                        .withStyle(ChatFormatting.GRAY));
            } else {
                tooltipIn.add(Component.translatable("arsenal.weapon.level.max")
                        .withStyle(ChatFormatting.DARK_PURPLE));
            }
        } else {
            tooltipIn.add(Component.translatable("arsenal.weapon.level.prefix", 0)
                    .withStyle(ChatFormatting.GRAY));
        }

        tooltipIn.add(Component.translatable("tooltip.arsenal.xuanyuanjian.not_max")
                .withStyle(ChatFormatting.DARK_RED));
    }


    @Override
    public boolean isFoil(ItemStack stackIn) {
        WeaponProgressComponent.WeaponProgress progress = stackIn.get(ComponentRegistry.WEAPON_PROGRESS.get());
        return ArsenalConfig.normal_sword_foil && ArsenalConfig.xuanyuanjian_foil &&
                (progress != null && progress.level() >= 3);
    }
}