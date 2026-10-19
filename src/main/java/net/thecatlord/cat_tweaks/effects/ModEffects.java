package net.thecatlord.cat_tweaks.effects;

import io.redspace.ironsspellbooks.effect.InstantManaEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CatastropheTweaks.MODID);

    public static final Holder<MobEffect> GRACE_EFFECT = MOB_EFFECTS.register("grace",
            () -> new GraceEffect(MobEffectCategory.BENEFICIAL, 0xFDC723));
    public static final Holder<MobEffect> INSTANT_SOULWARD_EFFECT = MOB_EFFECTS.register("instant_soulward",
            () -> new InstantSoulwardEffect(MobEffectCategory.BENEFICIAL, 0xDA70D6));
    public static final Holder<MobEffect> INSTANT_MANA_EFFECT = MOB_EFFECTS.register("instant_mana",
            () -> new InstantArsManaEffect(MobEffectCategory.BENEFICIAL, 0x5D3FD3));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
