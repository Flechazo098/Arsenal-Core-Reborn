//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.mcmod.arsenal;

import net.minecraftforge.common.ForgeConfigSpec;

public class ArsenalConfig {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.DoubleValue MAXIMUM_POWER_DAMAGE;
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue NORMAL_SWORD_FOIL;
    public static ForgeConfigSpec.BooleanValue XUANYUANJIAN_FOIL;

    public ArsenalConfig() {
    }

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");
        MAXIMUM_POWER_DAMAGE = COMMON_BUILDER.comment("How high is the damage value of xuan yuan jian's power").defineInRange("maximum_power_damage", 25.0F, 0.0F, Float.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
        COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Client settings").push("client");
        NORMAL_SWORD_FOIL = COMMON_BUILDER.comment("Is normal swords foil?").define("foil_normal", true);
        XUANYUANJIAN_FOIL = COMMON_BUILDER.comment("Is the Xuan Yuan Jian foil?").comment("Not recommended").define("foil_xuanyuanjian", false);
        COMMON_BUILDER.pop();
        CLIENT_CONFIG = COMMON_BUILDER.build();
    }
}
