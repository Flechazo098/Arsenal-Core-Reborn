package cn.mcmod.arsenal.item;

import cn.mcmod.arsenal.api.ArsenalAPI;
import cn.mcmod.arsenal.api.tier.WeaponTier;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import cn.mcmod.arsenal.item.chinese.XuanyuanjianItem;
import cn.mcmod.arsenal.item.knight.ArmingSwordItem;
import cn.mcmod.arsenal.item.knight.LongswordItem;
import cn.mcmod.arsenal.item.rapier.RapierItem;
import cn.mcmod.arsenal.item.rapier.SmallswordItem;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "arsenal_core");
    public static final RegistryObject<WeaponFrogItem> WEAPON_FROG = ITEMS.register("weapon_frog", WeaponFrogItem::new);

    public static final Map<WeaponTier, RegistryObject<Item>> CHINESE_SWORD_SHEATH = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_chinese_sword_sheath", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> CHINESE_SWORD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_chinese_sword", 
                    () -> new ChineseSwordItem(tier, new ItemStack(CHINESE_SWORD_SHEATH.get(tier).get())))
    );

    public static final Map<WeaponTier, RegistryObject<Item>> ANCIENT_SWORD_SHEATH = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_ancient_sword_sheath", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> ANCIENT_SWORD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_ancient_sword", 
                    () -> new AncientSwordItem(tier, new ItemStack(ANCIENT_SWORD_SHEATH.get(tier).get())))
    );

    public static final Map<WeaponTier, RegistryObject<Item>> RAPIER_SCABBARD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_rapier_scabbard", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> RAPIER = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_rapier", 
                    () -> new RapierItem(tier, new ItemStack(RAPIER_SCABBARD.get(tier).get())))
    );

    public static final Map<WeaponTier, RegistryObject<Item>> SMALLSWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_smallsword_scabbard", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> SMALLSWORD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_smallsword", 
                    () -> new SmallswordItem(tier, new ItemStack(SMALLSWORD_SCABBARD.get(tier).get())))
    );

    public static final Map<WeaponTier, RegistryObject<Item>> ARMING_SWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_arming_sword_scabbard", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> ARMING_SWORD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_arming_sword", 
                    () -> new ArmingSwordItem(tier, new ItemStack(ARMING_SWORD_SCABBARD.get(tier).get())))
    );

    public static final Map<WeaponTier, RegistryObject<Item>> LONGSWORD_SCABBARD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_longsword_scabbard", SwordSheathItem::new)
    );
    public static final Map<WeaponTier, RegistryObject<Item>> LONGSWORD = ArsenalAPI.registerWeaponsAllTier(
            tier -> register(tier.getUnlocalizedName() + "_longsword", 
                    () -> new LongswordItem(tier, new ItemStack(LONGSWORD_SCABBARD.get(tier).get())))
    );

    public static final RegistryObject<Item> XUANYUANJIAN_SHEATH = register("xuanyuanjian_sheath", () -> new SwordSheathItem(true));
    public static final RegistryObject<ChineseSwordItem> XUANYUANJIAN = register("xuanyuanjian", XuanyuanjianItem::new);

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
