package cn.mcmod.arsenal.compat.curios;

import cn.mcmod.arsenal.item.WeaponFrogItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosWrapper implements ICurio {
    private final ItemStack sheath;

    public CuriosWrapper(ItemStack sheath) {
        this.sheath = sheath;
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GENERIC.value(), 1.0F, 1.0F);
    }

    @Override
    public boolean canSync(SlotContext slotContext) {
        return true;
    }

    @Override
    public void readSyncData(SlotContext slotContext, CompoundTag nbt) {
        HolderLookup.Provider registries = slotContext.entity().level().registryAccess();
        WeaponFrogItem.getInventory(this.sheath).deserializeNBT(registries, nbt);
    }

    @Override
    public CompoundTag writeSyncData(SlotContext slotContext) {
        HolderLookup.Provider registries = slotContext.entity().level().registryAccess();
        return WeaponFrogItem.getInventory(this.sheath).serializeNBT(registries);
    }

    @Override
    public ItemStack getStack() {
        return this.sheath;
    }
}