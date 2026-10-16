package net.thecatlord.cat_tweaks.effects;

import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class GraceEffect extends MobEffect {
    public GraceEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @SubscribeEvent
    public static void graceImmunity(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(ModEffects.GRACE_EFFECT)) {
            DamageSource source = event.getSource();
            String damageTypeID = source.typeHolder().unwrapKey().map(key -> key.location().toString()).orElse(null);
            if (damageTypeID != null && CatastropheConfigs.graceBlacklist.get().contains(damageTypeID)) return;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void graceDeath(PlayerEvent.PlayerRespawnEvent event) {
        if (!CatastropheConfigs.graceRespawn.getAsBoolean()) return;
        Player player = event.getEntity();
        player.addEffect(new MobEffectInstance(ModEffects.GRACE_EFFECT, 30 * 20, 0, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30 * 20, 2, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 30 * 20, 2, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 30 * 20, 0, false, false, true));
    }

    @SubscribeEvent
    public static void graceJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!CatastropheConfigs.graceJoin.getAsBoolean()) return;
        Player player = event.getEntity();
        player.addEffect(new MobEffectInstance(ModEffects.GRACE_EFFECT, 5 * 20, 0, true, false, false));
    }

    @SubscribeEvent
    public static void graceHit(LivingDamageEvent.Pre event) {
        var attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;
        if (player.hasEffect(ModEffects.GRACE_EFFECT)) {
            player.removeEffect(ModEffects.GRACE_EFFECT);
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.removeEffect(MobEffects.JUMP);
            player.removeEffect(MobEffects.SATURATION);

            if (ModList.get().isLoaded("irons_spellbooks")) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.INSTANT_MANA, 1, 255, true, false, false));
            }
        }
    }
}
