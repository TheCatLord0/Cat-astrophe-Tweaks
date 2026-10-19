package net.thecatlord.cat_tweaks;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class CatastropheConfigs {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.ConfigValue<List<? extends String>> oneShotProtectionBlacklist;
    public static final ModConfigSpec.DoubleValue oneShotProtectionAmount;
    public static final ModConfigSpec.IntValue oneShotProtectionCooldown;
    public static final ModConfigSpec.BooleanValue oneShotProtectionFlair;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> bossDamageCapBlacklist;
    public static final ModConfigSpec.DoubleValue bossDamageCapAmount;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> graceBlacklist;
    public static final ModConfigSpec.BooleanValue graceRespawn;
    public static final ModConfigSpec.BooleanValue graceJoin;

    public static final ModConfigSpec.IntValue skeletonHealth;
    public static final ModConfigSpec.DoubleValue skeletonDodge;
    public static final ModConfigSpec.IntValue zombieArmorPierce;
    public static final ModConfigSpec.DoubleValue spiderDodge;

    public static final ModConfigSpec.DoubleValue bossResistance;
    public static final ModConfigSpec.DoubleValue playerResistance;
    public static final ModConfigSpec.DoubleValue mobResistance;

    public static final ModConfigSpec.IntValue teleportationAmuletCooldown;
    public static final ModConfigSpec.IntValue stabilizedWarpCooldown;
    public static final ModConfigSpec.IntValue warpScrollCooldown;
    public static final ModConfigSpec.IntValue portableTeleporterCooldown;

    static {
        BUILDER.translation("configuration.cat_tweaks.osp");
        BUILDER.push("one_shot_protection");
        oneShotProtectionBlacklist = BUILDER
                .translation("One-shot Protection Blacklist")
                .comment("List of damage types that can still one-shot.")
                .defineListAllowEmpty("oneshot_protection_blacklist", List.of("minecraft:fall", "minecraft:generic_kill"), () -> "", CatastropheConfigs::validateDamageTypes);
        oneShotProtectionAmount = BUILDER
                .translation("One-shot Protection Threshold")
                .comment("The %hp needed to survive a one-shot.")
                .defineInRange("oneshot_protection_threshold", 0.9, 0, 1);
        oneShotProtectionCooldown = BUILDER
                .translation("One-shot Protection Cooldown")
                .comment("The time in seconds before protection goes off cooldown.")
                .defineInRange("oneshot_protection_cooldown", 10, 0, Integer.MAX_VALUE);
        oneShotProtectionFlair = BUILDER
                .translation("One-shot Protection Flair")
                .comment("Should particles and sounds play upon successful protection?")
                .define("oneshot_protection_flair", true);
    }

    static {
        BUILDER.pop();
        BUILDER.translation("configuration.cat_tweaks.bdc");
        BUILDER.push("boss_damage_cap");
        bossDamageCapBlacklist = BUILDER
                .translation("Boss Damage Cap Blacklist")
                .comment("List of damage types that can bypass boss damage cap.")
                .defineListAllowEmpty("boss_damage_cap_blacklist", List.of("minecraft:fall", "minecraft:generic_kill"), () -> "", CatastropheConfigs::validateDamageTypes);
        bossDamageCapAmount = BUILDER
                .translation("Boss Damage Cap Limit")
                .comment("The max amount of damage a boss can take in a hit.")
                .defineInRange("boss_damage_cap_limit", 0.1, 0, 1);
    }

    static {
        BUILDER.pop();
        BUILDER.translation("configuration.cat_tweaks.grace");
        BUILDER.push("grace");
        graceBlacklist = BUILDER
                .translation("Grace Blacklist")
                .comment("List of damage types that can bypass Grace damage immunity.")
                .defineListAllowEmpty("grace_blacklist", List.of("minecraft:generic_kill"), () -> "", CatastropheConfigs::validateDamageTypes);
        graceRespawn = BUILDER
                .translation("Respawn Grace")
                .comment("Should respawning grant Grace?")
                .define("grace_respawn", true);
        graceJoin = BUILDER
                .translation("Joining Grace")
                .comment("Should joining grant Grace?")
                .define("grace_join", true);
    }

    static {
        BUILDER.pop();
        BUILDER.translation("configuration.cat_tweaks.mobs");
        BUILDER.push("mobs");
        skeletonHealth = BUILDER
                .translation("Skeleton Health")
                .comment("How much health a skeleton has")
                .defineInRange("skeleton_health", 12, 1, Integer.MAX_VALUE);
        skeletonDodge = BUILDER
                .translation("Skeleton Dodge Chance")
                .comment("The chance a Skeleton dodges an attack")
                .defineInRange("skeleton_dodge_chance", 0.2, 0, 1);
        zombieArmorPierce = BUILDER
                .translation("Zombie Armor Pierce")
                .comment("The amount of Armor Pierce a zombie has.")
                .defineInRange("zombie_armor_pierce", 2 , 0, Integer.MAX_VALUE);
        spiderDodge = BUILDER
                .translation("Spider Dodge Chance")
                .comment("The chance a Spider dodges an attack.")
                .defineInRange("spider_dodge_chance", 0.2, 0, 1);
        bossResistance = BUILDER
                .translation("Ars Nouveau Boss Spell Resistance")
                .comment("The amount of resistance bosses get against Ars Nouveau.")
                .defineInRange("boss_spell_resistance", 0.7, 0, 1);
        playerResistance = BUILDER
                .translation("Ars Nouveau Player Spell Resistance")
                .comment("The amount of resistance Players get against Ars Nouveau.")
                .defineInRange("player_spell_resistance", 0.6, 0, 1);
        mobResistance = BUILDER
                .translation("Ars Nouveau Mob Spell Resistance")
                .comment("The amount of resistance other mobs get against Ars Nouveau.")
                .defineInRange("mob_spell_resistance", 0.2, 0, 1);
    }

    static {
        BUILDER.pop();
        BUILDER.translation("configuration.cat_tweaks.items");
        BUILDER.push("items");
        teleportationAmuletCooldown = BUILDER
                .translation("Teleportation Amulet Cooldown")
                .comment("The time in seconds before Teleportation Amulet can give Evasion.")
                .defineInRange("teleporation_amulet_cooldown", 5, 0, Integer.MAX_VALUE);
        stabilizedWarpCooldown = BUILDER
                .translation("Stabilized Warpscroll Cooldown")
                .comment("How long should Stabilized Warpscroll be on cooldown after warp?.")
                .defineInRange("stabilized_warpscroll_cooldown", 10, 0, 107374182);
        warpScrollCooldown = BUILDER
                .translation("Warpscroll Cooldown")
                .comment("How long should Warpscroll be on cooldown after warp?.")
                .defineInRange("warpscroll_cooldown", 5, 0, 107374182);
        portableTeleporterCooldown = BUILDER
                .translation("Portable Teleporter Cooldown")
                .comment("How long should Portable Teleporter be on cooldown after teleport?.")
                .defineInRange("portable_teleporter_cooldown", 5, 0, Integer.MAX_VALUE);
    }

    static {
        BUILDER.pop();
    }
    public static final ModConfigSpec.BooleanValue fireworkPlayer = BUILDER
            .translation("Firework Glyph Nerf")
            .comment("Should Firework Glyph work on players?")
                .define("firework_glyph_players", true);
    public static final ModConfigSpec.BooleanValue blinkWarpNerf = BUILDER
            .translation("Blink Glyph Nerf")
            .comment("Should Blink Glyph not be able to teleport players while holding a warpscroll?")
                .define("blink_glyph_players", true);


    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateDamageTypes(Object value) {
        return value instanceof String name
                && ResourceLocation.tryParse(name) != null;

    }
}