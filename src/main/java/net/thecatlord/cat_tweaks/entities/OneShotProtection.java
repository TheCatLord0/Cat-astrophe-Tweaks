package net.thecatlord.cat_tweaks.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class OneShotProtection {
    private static final Map<UUID, Long> Cooldown = new HashMap<>();

    @SubscribeEvent
    private static void oneShotProtection(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        DamageSource source = event.getSource();
        String damageTypeID = source.typeHolder().unwrapKey().map(key -> key.location().toString()).orElse(null);
        if (damageTypeID != null && CatastropheConfigs.oneShotProtectionBlacklist.get().contains(damageTypeID)) return;

        float threshold = CatastropheConfigs.oneShotProtectionAmount.get().floatValue();
        var server = player.serverLevel();

        long now = server.getServer().overworld().getGameTime();
        long cooldownEnd = Cooldown.getOrDefault(player.getUUID(), 0L);
        if (now < cooldownEnd) return;

        if (player.getHealth() < player.getMaxHealth() * threshold) return;
        if (player.getHealth() > event.getNewDamage()) return;
        Cooldown.put(player.getUUID(), now + (CatastropheConfigs.oneShotProtectionCooldown.getAsInt() * 20L));
        event.setNewDamage(0);
        player.setHealth(player.getMaxHealth() * 0.1f);
        player.invulnerableTime = 40;
        if (CatastropheConfigs.oneShotProtectionFlair.getAsBoolean()) {
            server.sendParticles(ParticleTypes.CHERRY_LEAVES, player.getX(), player.getY() + 2, player.getZ(), 25, 1.5, 1.5, 1.5, 0);
            server.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 2, player.getZ(), 25, 1.5, 1.5, 1.5, 0);
            server.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 2.0f, 1.0f);
        }
    }

    @SubscribeEvent
    private static void oneShotCooldownClear(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        Cooldown.put(player.getUUID(), 0L);
    }
}
