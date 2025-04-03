
package cn.mcmod.arsenal.item.feature;

import cn.mcmod.arsenal.api.WeaponFeature;
import java.util.function.Consumer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class XuanyuanFeature extends WeaponFeature {
    public XuanyuanFeature() {
        super("maximum_power");
    }

    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    }

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living) {
            int fire_level = 1 + EnchantmentHelper.getFireAspect(player);
            living.setSecondsOnFire(4 * fire_level);
            if (living.getMobType() == MobType.UNDEAD) {
                float smite_level = Math.max(5.0F, 7.5F * (float)stack.getEnchantmentLevel(Enchantments.SMITE));
                living.hurt(player.damageSources().playerAttack(player), smite_level);
            }
        }

        return false;
    }

    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return -1;
    }
}