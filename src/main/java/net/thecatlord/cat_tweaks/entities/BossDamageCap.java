package net.thecatlord.cat_tweaks.entities;

import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class BossDamageCap {

    @SubscribeEvent
    private static void bossDamageCap(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer) return;
        if (!entity.getType().is(Tags.EntityTypes.BOSSES)) return;
        DamageSource source = event.getSource();
        String damageTypeID = source.typeHolder().unwrapKey().map(key -> key.location().toString()).orElse(null);
        if (damageTypeID != null && CatastropheConfigs.bossDamageCapBlacklist.get().contains(damageTypeID)) return;

        float limit = CatastropheConfigs.bossDamageCapAmount.get().floatValue();
        var server = entity.level();

        if (event.getNewDamage() < entity.getMaxHealth() * limit) return;
        event.setNewDamage(entity.getMaxHealth() * limit);
        if (CatastropheConfigs.oneShotProtectionFlair.getAsBoolean()) {
            if (ModList.get().isLoaded("irons_spellbooks")) {
                server.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundRegistry.FIRE_DAGGER_PARRY, SoundSource.HOSTILE, 2.0f, 1.0f);
            } else {
                server.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.HOSTILE, 2.0f, 1.0f);
            }
        }
    }
}
