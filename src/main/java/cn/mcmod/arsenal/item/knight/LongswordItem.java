//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.item.knight;

import cn.mcmod.arsenal.api.tier.WeaponTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LongswordItem extends ArmingSwordItem {
    public LongswordItem(WeaponTier tier, ItemStack sheathItem, Item.Properties builderIn) {
        super(tier, 6, -2.6F, sheathItem, builderIn);
    }

    public LongswordItem(WeaponTier tier, ItemStack sheathItem) {
        super(tier, 6, -2.6F, sheathItem, (new Item.Properties()).stacksTo(1));
    }
}
