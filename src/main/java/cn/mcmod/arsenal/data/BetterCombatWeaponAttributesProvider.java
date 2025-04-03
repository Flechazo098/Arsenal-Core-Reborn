package cn.mcmod.arsenal.data;

import cn.mcmod.arsenal.item.ItemRegistry;
import cn.mcmod.arsenal.item.chinese.AncientSwordItem;
import cn.mcmod.arsenal.item.chinese.ChineseSwordItem;
import cn.mcmod.arsenal.item.knight.ArmingSwordItem;
import cn.mcmod.arsenal.item.knight.LongswordItem;
import cn.mcmod.arsenal.item.rapier.RapierItem;
import cn.mcmod.arsenal.item.rapier.SmallswordItem;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BetterCombatWeaponAttributesProvider implements DataProvider {
    protected final DataGenerator generator;
    protected final String modId;
    protected final ExistingFileHelper existingFileHelper;
    protected final PackOutput packOutput;
    private final ExistingFileHelper.IResourceType resourceType;
    protected final Map<ResourceLocation, String> datas;
    public static final Gson DATA_GSON = (new GsonBuilder()).disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).create();

    public BetterCombatWeaponAttributesProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this(generator, existingFileHelper, "arsenal_core");
    }

    public BetterCombatWeaponAttributesProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String modId) {
        this.datas = Maps.newLinkedHashMap();
        this.generator = generator;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
        this.packOutput = generator.getPackOutput();
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", "weapon_attributes");
    }

    public void addData(Item item, String attribute) {
        // 使用 ForgeRegistries 替代已弃用的 BuiltInRegistries.ITEM
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(item);
        this.datas.computeIfAbsent(itemId, (loc) -> {
            this.existingFileHelper.trackGenerated(loc, this.resourceType);
            return attribute;
        });
    }

    // 实现新的 run 方法
    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.datas.clear();
        this.addDatas();

        return CompletableFuture.allOf(
                this.datas.entrySet().stream()
                        .map(entry -> {
                            ResourceLocation loc = entry.getKey();
                            String data = entry.getValue();
                            Path path = this.packOutput.getOutputFolder(PackOutput.Target.DATA_PACK)
                                    .resolve(loc.getNamespace())
                                    .resolve("weapon_attributes")
                                    .resolve(loc.getPath() + ".json");
                            JsonObject jsonObj = new JsonObject();
                            jsonObj.addProperty("parent", data);

                            return DataProvider.saveStable(cache, jsonObj, path);
                        })
                        .toArray(CompletableFuture[]::new)
        );
    }

    @Deprecated
    public void setCache(net.minecraft.data.HashCache cache) {
        // 已被 run(CachedOutput) 替代
    }

    public void addDatas() {
        ItemRegistry.ITEMS.getEntries().forEach(this::setSword);
    }

    public void setSword(RegistryObject<Item> item) {
        if (item.get() instanceof AncientSwordItem) {
            this.addData(item.get(), "arsenal_core:ancient_sword");
        } else if (item.get() instanceof ChineseSwordItem) {
            this.addData(item.get(), "bettercombat:sword");
        } else if (item.get() instanceof SmallswordItem) {
            this.addData(item.get(), "arsenal_core:smallsword");
        } else if (item.get() instanceof RapierItem) {
            this.addData(item.get(), "bettercombat:rapier");
        } else if (item.get() instanceof LongswordItem) {
            this.addData(item.get(), "arsenal_core:longsword");
        } else if (item.get() instanceof ArmingSwordItem) {
            this.addData(item.get(), "bettercombat:sword");
        }
    }

    @Override
    public String getName() {
        return "SIMPLE Arsenal Core Better Combat Weapon Attributes Provider";
    }
}