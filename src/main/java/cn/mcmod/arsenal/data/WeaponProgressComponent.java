package cn.mcmod.arsenal.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;


public class WeaponProgressComponent {
    public record WeaponProgress(int kills, int level, int cooldown, long lastUseTime) {
        public static final Codec<WeaponProgress> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.INT.fieldOf("kills").forGetter(WeaponProgress::kills),
                        Codec.INT.fieldOf("level").forGetter(WeaponProgress::level),
                        Codec.INT.fieldOf("cooldown").forGetter(WeaponProgress::cooldown),
                        Codec.LONG.fieldOf("lastUseTime").forGetter(WeaponProgress::lastUseTime)
                ).apply(instance, WeaponProgress::new)
        );

        public static final StreamCodec<FriendlyByteBuf, WeaponProgress> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.INT,
                        WeaponProgress::kills,
                        ByteBufCodecs.INT,
                        WeaponProgress::level,
                        ByteBufCodecs.INT,
                        WeaponProgress::cooldown,
                        ByteBufCodecs.LONG,
                        WeaponProgress::lastUseTime,
                        WeaponProgress::new
                );

        public WeaponProgress addKills(int amount) {
            return new WeaponProgress(kills + amount, level, cooldown, lastUseTime);
        }

        public WeaponProgress setLevel(int newLevel) {
            return new WeaponProgress(kills, newLevel, cooldown, lastUseTime);
        }

        public WeaponProgress setCooldown(int newCooldown) {
            return new WeaponProgress(kills, level, newCooldown, lastUseTime);
        }

        public WeaponProgress setLastUseTime(long time) {
            return new WeaponProgress(kills, level, cooldown, time);
        }
    }


    // TODO：此处攻击伤害和速度并未实际应用，所以现在轩辕剑攻击力为 1（？）
    public static final int[] LEVEL_THRESHOLDS = {0, 20, 60, 120, 240, 480};
    public static final float[] ATTACK_SPEEDS = {-1.8F, -1.7F, -1.6F, -1.5F, -1.4F, -1.2F};
    public static final int[] ATTACK_DAMAGES = {5, 6, 7, 8, 10, 12};

    public static int calculateLevel(int kills) {
        for (int i = LEVEL_THRESHOLDS.length - 1; i >= 0; i--) {
            if (kills >= LEVEL_THRESHOLDS[i]) {
                return i;
            }
        }
        return 0;
    }
}