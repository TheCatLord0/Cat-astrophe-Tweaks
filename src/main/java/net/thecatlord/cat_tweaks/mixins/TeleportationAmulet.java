package net.thecatlord.cat_tweaks.mixins;

import io.redspace.ironsspellbooks.api.events.SpellTeleportEvent;
import io.redspace.ironsspellbooks.player.ServerPlayerEvents;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(ServerPlayerEvents.class)
public abstract class TeleportationAmulet {
    @Unique
    private static final Map<UUID, Long> Cooldown = new HashMap<>();

    @Inject(method = "onSpellTeleport",
            at = @At("HEAD"),
            cancellable = true)
    private static void evasionCooldown(SpellTeleportEvent event, CallbackInfo ci) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        if (!(entity instanceof ServerPlayer player)) return;
        if (!ItemRegistry.TELEPORTATION_AMULET.get().isEquippedBy(player)) return;
        ci.cancel();
        var server = player.level();

        long now = server.getServer().overworld().getGameTime();
        long cooldownEnd = Cooldown.getOrDefault(player.getUUID(), 0L);
        if (now < cooldownEnd) return;

        player.addEffect(new MobEffectInstance(MobEffectRegistry.EVASION, 3 * 20, 0, false, false, true));
        Cooldown.put(player.getUUID(), now + (CatastropheConfigs.teleportationAmuletCooldown.getAsInt() * 20L));
    }
}
