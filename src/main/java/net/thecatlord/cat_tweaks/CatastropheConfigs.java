package net.thecatlord.cat_tweaks;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class CatastropheConfigs {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.ConfigValue<List<? extends String>> oneShotProtectionBlacklist = BUILDER
            .translation("Oneshot Protection Blacklist")
            .comment("List of damage types that can still oneshot.")
            .defineListAllowEmpty("osp_blacklist", List.of("minecraft:fall"), () -> "", CatastropheConfigs::validateDamageTypes);
    public static final ModConfigSpec.BooleanValue oneShotProtectionFlair = BUILDER
            .translation("Oneshot Protection Flair")
            .comment("Should particles and sounds play upon successful protection?")
            .define("osp_flair", true);
    public static final ModConfigSpec.DoubleValue oneShotProtectionAmount = BUILDER
            .translation("Oneshot Protection Threshold")
            .comment("The %hp needed to survive a oneshot.")
            .defineInRange("osp_threshold", 0.9, 0, 1);
    public static final ModConfigSpec.IntValue oneShotProtectionCooldown = BUILDER
            .translation("Oneshot Protection Cooldown")
            .comment("The time in seconds before protection goes off cooldown.")
            .defineInRange("osp_cooldown", 10, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateDamageTypes(Object value) {
        return value instanceof String name
                && ResourceLocation.tryParse(name) != null;

    }
}