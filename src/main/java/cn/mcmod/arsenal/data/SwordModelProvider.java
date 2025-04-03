//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.api.tier.WeaponTierRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SwordModelProvider extends ItemModelProvider {
    public SwordModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public SwordModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "arsenal_core", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        WeaponTierRegistry.getWeaponTiers().forEach((tier) -> {
            if (!tier.isSpecial()) {
                this.registerSwordModel(tier.getUnlocalizedName());
            }
        });
    }


    public void registerSwordModel(String name) {
        this.ancientSword(name);
        this.chineseSword(name);
        this.rapier(name);
        this.smallsword(name);
        this.arming_sword(name);
        this.longsword(name);
    }

    public void ancientSword(String name) {
        this.withExistingParent(name + "_ancient_sword_blocking", new ResourceLocation("arsenal_core", "item/ancient_sword_blocking")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_ancient_sword", new ResourceLocation("arsenal_core", "item/ancient_sword")).texture("0", "arsenal:item/" + name + "_sword").override().predicate(new ResourceLocation("blocking"), 1.0F).model(this.getExistingFile(new ResourceLocation("arsenal_core", "item/" + name + "_ancient_sword_blocking")));
        this.withExistingParent(name + "_ancient_sword_sheath", new ResourceLocation("arsenal_core", "item/ancient_sword_sheath")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }

    public void chineseSword(String name) {
        this.withExistingParent(name + "_chinese_sword_blocking", new ResourceLocation("arsenal_core", "item/chinese_sword_blocking")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_chinese_sword", new ResourceLocation("arsenal_core", "item/chinese_sword")).texture("0", "arsenal:item/" + name + "_sword").override().predicate(new ResourceLocation("blocking"), 1.0F).model(this.getExistingFile(new ResourceLocation("arsenal_core", "item/" + name + "_chinese_sword_blocking")));
        this.withExistingParent(name + "_chinese_sword_sheath", new ResourceLocation("arsenal_core", "item/chinese_sword_sheath")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }

    public void rapier(String name) {
        this.withExistingParent(name + "_rapier", new ResourceLocation("arsenal_core", "item/rapier")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_rapier_scabbard", new ResourceLocation("arsenal_core", "item/rapier_scabbard")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }

    public void smallsword(String name) {
        this.withExistingParent(name + "_smallsword", new ResourceLocation("arsenal_core", "item/smallsword")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_smallsword_scabbard", new ResourceLocation("arsenal_core", "item/smallsword_scabbard")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }

    public void arming_sword(String name) {
        this.withExistingParent(name + "_arming_sword", new ResourceLocation("arsenal_core", "item/arming_sword")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_arming_sword_scabbard", new ResourceLocation("arsenal_core", "item/arming_sword_scabbard")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }

    public void longsword(String name) {
        this.withExistingParent(name + "_longsword", new ResourceLocation("arsenal_core", "item/longsword")).texture("0", "arsenal:item/" + name + "_sword");
        this.withExistingParent(name + "_longsword_scabbard", new ResourceLocation("arsenal_core", "item/longsword_scabbard")).texture("0", "arsenal:item/" + name + "_sword").texture("1", "minecraft:block/stripped_dark_oak_log");
    }
}
