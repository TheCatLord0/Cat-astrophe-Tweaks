package net.thecatlord.cat_tweaks.mixins;

import com.hollingsworth.arsnouveau.common.spell.effect.EffectFirework;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.fml.ModList;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectFirework.class)
public class FireworkElytra {
    @Inject(method = "spawnFireworkOnEntity",
            at = @At("HEAD"),
            cancellable = true)
    private void cancelFireworkElyta(EntityHitResult entity, Level world, LivingEntity shooter, ItemStack firework, CallbackInfo ci) {
        if (CatastropheConfigs.fireworkPlayer.getAsBoolean()) return;
        if (!(entity.getEntity() instanceof ServerPlayer player)) return;
        ci.cancel();
    }
}
