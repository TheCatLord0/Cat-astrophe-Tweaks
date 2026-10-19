package net.thecatlord.cat_tweaks.effects;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

public class PotionItem {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(
                    Registries.POTION,
                    CatastropheTweaks.MODID
            );
    public static final DeferredHolder<Potion, Potion> INSTANT_SOULWARD_POTION_LV1 = POTIONS.register("instant_soulward_lv1", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_SOULWARD_EFFECT, 1, 0)));
    public static final DeferredHolder<Potion, Potion> INSTANT_SOULWARD_POTION_LV2 = POTIONS.register("instant_soulward_lv2", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_SOULWARD_EFFECT, 1, 1)));
    public static final DeferredHolder<Potion, Potion> INSTANT_SOULWARD_POTION_LV3 = POTIONS.register("instant_soulward_lv3", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_SOULWARD_EFFECT, 1, 2)));

    public static final DeferredHolder<Potion, Potion> INSTANT_MANA_POTION_LV1 = POTIONS.register("instant_mana_lv1", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_MANA_EFFECT, 1, 0)));
    public static final DeferredHolder<Potion, Potion> INSTANT_MANA_POTION_LV2 = POTIONS.register("instant_mana_lv2", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_MANA_EFFECT, 1, 1)));
    public static final DeferredHolder<Potion, Potion> INSTANT_MANA_POTION_LV3 = POTIONS.register("instant_mana_lv3", () -> new Potion(new MobEffectInstance(ModEffects.INSTANT_MANA_EFFECT, 1, 2)));


    public static void register(IEventBus modEventBus) {
        POTIONS.register(modEventBus);
    }
}
