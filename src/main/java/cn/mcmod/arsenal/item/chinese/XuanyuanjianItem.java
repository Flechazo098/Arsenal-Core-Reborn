package cn.mcmod.arsenal.item.chinese;

import cn.mcmod.arsenal.ArsenalConfig;
import cn.mcmod.arsenal.ArsenalCore;
import cn.mcmod.arsenal.api.tier.BlankTier;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.item.ItemRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class XuanyuanjianItem extends ChineseSwordItem {
    // 创建一个静态的临时 WeaponTier 对象，避免每次都创建新对象
    private static final WeaponTier TEMP_TIER = new BlankTier(
            "xuanyuanjian",
            "arsenal_core",
            5, // 挖掘等级
            -1, // 最大使用次数（-1表示无限）
            8.0F, // 效率
            5.0F, // 基础伤害
            50, // 附魔能力
            new ResourceLocation("forge:gems/diamond") // 修复材料标签
    );

    public XuanyuanjianItem() {
        // 使用临时的 WeaponTier 对象
        super(TEMP_TIER, 5, -1.8F, new ItemStack(ItemRegistry.XUANYUANJIAN_SHEATH.get()));
    }

    // 重写 getWeaponTier 方法，在实际需要时再获取 WeaponTier
    @Override
    public WeaponTier getWeaponTier(ItemStack stack) {
        // 只有在配置加载后才返回实际的 WeaponTier
        if (ArsenalCore.isConfigLoaded() && cn.mcmod.arsenal.tier.VanillaWeaponTiers.MAXIMUM_POWER != null) {
            return cn.mcmod.arsenal.tier.VanillaWeaponTiers.MAXIMUM_POWER;
        }
        // 返回临时的 WeaponTier
        return TEMP_TIER;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stackIn, Level levelIn, List<Component> tooltipIn, TooltipFlag flagIn) {
        super.appendHoverText(stackIn, levelIn, tooltipIn, flagIn);
        tooltipIn.add(Component.translatable("tooltip.arsenal.xuanyuanjian.not_max").withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    public boolean isFoil(ItemStack stackIn) {
        // 确保配置已加载
        if (!ArsenalCore.isConfigLoaded()) {
            return false;
        }
        return ArsenalConfig.NORMAL_SWORD_FOIL.get() && ArsenalConfig.XUANYUANJIAN_FOIL.get();
    }
}