package cn.mcmod.arsenal.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(
        bus = Bus.MOD
)
public class DataGen {
    public DataGen() {
    }

    private static class ArsenalBlockTagsProvider extends BlockTagsProvider {
        public ArsenalBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, "arsenal_core", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            // 这里可以添加方块标签，但现在不需要
        }
    }

    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // 添加数据提供器
        generator.addProvider(event.includeServer(), new SwordRecipes(packOutput));
        generator.addProvider(event.includeClient(), new SwordModelProvider(packOutput, "arsenal_core", existingFileHelper));

        // 使用具体 BlockTagsProvider 实现
        BlockTagsProvider blockTagsProvider = new ArsenalBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);

        // 添加物品标签提供器
        generator.addProvider(event.includeServer(),
                new SwordTagsProvider(packOutput, lookupProvider, blockTagsProvider, existingFileHelper));

        // 添加 BetterCombat 武器属性提供器
        generator.addProvider(event.includeServer(),
                new BetterCombatWeaponAttributesProvider(generator, existingFileHelper));
    }
}