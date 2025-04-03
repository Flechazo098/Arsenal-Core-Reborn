

package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.api.WeaponTags;
import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import cn.mcmod.arsenal.item.knight.ArmingSwordItem;
import cn.mcmod.arsenal.item.knight.LongswordItem;
import cn.mcmod.arsenal.item.rapier.RapierItem;
import cn.mcmod.arsenal.item.rapier.SmallswordItem;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class SwordTagsProvider extends ItemTagsProvider {
    public SwordTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                             BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider.contentsGetter(), "arsenal_core", existingFileHelper);
    }

    @Override
    public String getName() {
        return "Arsenal Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ItemRegistry.ITEMS.getEntries().forEach(this::tagSword);
    }

    public void tagSword(RegistryObject<Item> item) {
        if (item.get() instanceof AncientSwordItem) {
            this.tag(WeaponTags.ANCIENT_SWORD).add(item.get());
        } else if (item.get() instanceof ChineseSwordItem) {
            this.tag(WeaponTags.CHINESE_SWORD).add(item.get());
        } else if (item.get() instanceof SmallswordItem) {
            this.tag(WeaponTags.SMALLSWORD).add(item.get());
        } else if (item.get() instanceof RapierItem) {
            this.tag(WeaponTags.RAPIER).add(item.get());
        } else if (item.get() instanceof LongswordItem) {
            this.tag(WeaponTags.LONGSWORD).add(item.get());
        } else if (item.get() instanceof ArmingSwordItem) {
            this.tag(WeaponTags.ARMING_SWORD).add(item.get());
        }
    }
}