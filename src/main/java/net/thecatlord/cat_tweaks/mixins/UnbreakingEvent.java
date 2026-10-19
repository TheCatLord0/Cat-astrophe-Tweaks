package net.thecatlord.cat_tweaks.mixins;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.thecatlord.cat_tweaks.attributes.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class UnbreakingEvent {
    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V",
            at = @At("HEAD"),
            cancellable = true)
    public void noDurabilityLoss(int amount, LivingEntity entity, EquipmentSlot slot, CallbackInfo ci) {
            var attribute = entity.getAttribute(ModAttributes.UNBREAKING_ATTRIBUTE);
            if (attribute == null) return;
            double chance = attribute.getValue();
            if (Math.random() < chance) {
                ci.cancel();
        }
    }
}
