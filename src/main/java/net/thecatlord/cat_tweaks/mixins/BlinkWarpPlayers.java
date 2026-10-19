package net.thecatlord.cat_tweaks.mixins;

import com.hollingsworth.arsnouveau.common.items.data.WarpScrollData;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBlink;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EffectBlink.class)
public class BlinkWarpPlayers {
    @WrapOperation(
            method = "onResolveEntity",
            at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/items/data/WarpScrollData;isValid()Z"))
    private boolean cancelPlayerWarp(WarpScrollData instance, Operation<Boolean> original, @Local(argsOnly = true) EntityHitResult hit) {
        boolean allowed = original.call(instance);
        if (!CatastropheConfigs.blinkWarpNerf.getAsBoolean()) return allowed;
        return allowed && !(hit.getEntity() instanceof Player);
    }
}
