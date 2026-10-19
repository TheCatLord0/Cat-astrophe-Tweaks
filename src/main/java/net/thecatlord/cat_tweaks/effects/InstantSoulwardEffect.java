package net.thecatlord.cat_tweaks.effects;

import com.sammy.malum.registry.common.MalumAttachmentTypes;
import com.sammy.malum.registry.common.MalumAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class InstantSoulwardEffect extends InstantenousMobEffect {
    protected InstantSoulwardEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static int getSoulwardAmount(int amplifier) {
        return ((1 + amplifier) * 2);
    }

    private static int getSoulwardPercent(int amplifier) {
        return ((1 + amplifier) * 5);
    }

    @SubscribeEvent
    public static void addPotionDescription(ItemTooltipEvent event) {
        if (!ModList.get().isLoaded("malum")) return;
        var potion = event.getItemStack().get(DataComponents.POTION_CONTENTS);
        if (potion == null) return;

        for (var instance : potion.getAllEffects()) {
            if (instance.getEffect().value() instanceof InstantSoulwardEffect effect) {
                event.getToolTip().add(effect.description(instance));
            }
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration >= 1;
    }

    public Component description(MobEffectInstance instance) {
        if (!ModList.get().isLoaded("malum")) return Component.literal("Malum is not installed!");
        int amount = getSoulwardAmount(instance.getAmplifier());
        int percent = getSoulwardPercent(instance.getAmplifier());

        return Component.translatable(
                        getDescriptionId() + ".description", amount, percent)
                .withStyle(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!ModList.get().isLoaded("malum")) return false;
        if (!(entity instanceof ServerPlayer player)) return false;
        var soulWard = player.getData(MalumAttachmentTypes.SOUL_WARD);
        var soulwardAmount = player.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        double amount = getSoulwardAmount(amplifier) + getSoulwardPercent(amplifier) / 100.0 * soulwardAmount;

        soulWard.recoverSoulWard(player, amount);
        return true;
    }

    @Override
    public void applyInstantenousEffect(Entity source, Entity indirectSource, LivingEntity entity, int amplifier, double effectiveness) {
        if (!ModList.get().isLoaded("malum")) return;
        if (!(entity instanceof ServerPlayer player)) return;
        var soulWard = player.getData(MalumAttachmentTypes.SOUL_WARD);
        var soulwardAmount = player.getAttributeValue(MalumAttributes.SOUL_WARD_CAPACITY);
        double amount = getSoulwardAmount(amplifier) + getSoulwardPercent(amplifier) / 100.0 * soulwardAmount;

        soulWard.recoverSoulWard(player, amount);
    }
}
