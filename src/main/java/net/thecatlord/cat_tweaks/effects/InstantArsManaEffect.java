package net.thecatlord.cat_tweaks.effects;

import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
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
import org.openjdk.nashorn.internal.ir.CallNode;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class InstantArsManaEffect extends InstantenousMobEffect {
    protected InstantArsManaEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static int getManaAmount(int amplifier) {
        return ((1 + amplifier) * 40);
    }

    private static int getManaPercent(int amplifier) {
        return ((1 + amplifier) * 5);
    }

    @SubscribeEvent
    public static void addPotionDescription(ItemTooltipEvent event) {
        if (!ModList.get().isLoaded("ars_nouveau")) return;
        var potion = event.getItemStack().get(DataComponents.POTION_CONTENTS);
        if (potion == null) return;

        for (var instance : potion.getAllEffects()) {
            if (instance.getEffect().value() instanceof InstantArsManaEffect effect) {
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
        if (!ModList.get().isLoaded("ars_nouveau")) return Component.literal("Ars Nouveau isn't installed!");
        int amount = getManaAmount(instance.getAmplifier());
        int percent = getManaPercent(instance.getAmplifier());

        return Component.translatable(
                        getDescriptionId() + ".description", amount, percent)
                .withStyle(ChatFormatting.DARK_PURPLE);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!ModList.get().isLoaded("ars_nouveau")) return false;

        if (!(entity instanceof ServerPlayer player)) return false;
        var mana = CapabilityRegistry.getMana(player);
        var manaAmount = player.getAttributeValue(PerkAttributes.MAX_MANA);
        double amount = getManaAmount(amplifier) + getManaPercent(amplifier) / 100.0 * manaAmount;

        mana.addMana((amount));
        return true;
    }

    @Override
    public void applyInstantenousEffect(Entity source, Entity indirectSource, LivingEntity entity, int amplifier, double effectiveness) {
        if (!ModList.get().isLoaded("ars_nouveau")) return;

        if (!(entity instanceof ServerPlayer player)) return;
        var mana = CapabilityRegistry.getMana(player);
        var manaAmount = player.getAttributeValue(PerkAttributes.MAX_MANA);
        double amount = getManaAmount(amplifier) + getManaPercent(amplifier) / 100.0 * manaAmount;

        mana.addMana((amount));
    }
}
