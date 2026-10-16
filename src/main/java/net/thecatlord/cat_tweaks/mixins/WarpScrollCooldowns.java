package net.thecatlord.cat_tweaks.mixins;

import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalTile.class)
public abstract class WarpScrollCooldowns {
    @Inject(method = "teleportEntityTo",
            at = @At("RETURN"))
    private static void warpscrollCooldown(CallbackInfoReturnable<Entity> cir) {
        if (!(cir.getReturnValue() instanceof ServerPlayer player)) return;
        player.getCooldowns().addCooldown(
                ItemsRegistry.WARP_SCROLL.get(),
                CatastropheConfigs.warpScrollCooldown.getAsInt() * 20
        );
        player.getCooldowns().addCooldown(
                ItemsRegistry.STABLE_WARP_SCROLL.get(),
                CatastropheConfigs.stabilizedWarpCooldown.getAsInt() * 20
        );
    }
}
