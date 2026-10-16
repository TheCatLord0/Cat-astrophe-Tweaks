package net.thecatlord.cat_tweaks.entities;

import com.alexthw.sauce.registry.ModRegistry;
import io.redspace.ironslib.registry.IronsLibRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.thecatlord.cat_tweaks.CatastropheConfigs;

public class MobAttributes {
    public static void mobModification(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(ModList.get().isLoaded("sauce"))) return;
        if (!(event.getEntity() instanceof LivingEntity entity)) return;

        var dodgeAttribute = entity.getAttribute(IronsLibRegistries.AttributeRegistry.DODGE_CHANCE);
        var armorPiercingAttribute = entity.getAttribute(IronsLibRegistries.AttributeRegistry.ARMOR_PIERCE);
        var healthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);

        double dodge;
        double pierce;
        double health;

        if (entity instanceof Skeleton) {
            health = CatastropheConfigs.skeletonHealth.getAsInt();
            dodge = CatastropheConfigs.skeletonDodge.getAsDouble();
            healthAttribute.setBaseValue(health);
        } else if (entity instanceof Zombie) {
            pierce = CatastropheConfigs.zombieArmorPierce.getAsDouble();
            armorPiercingAttribute.setBaseValue(pierce);
        } else if (entity instanceof Spider) {
            dodge = CatastropheConfigs.spiderDodge.getAsDouble();
            dodgeAttribute.setBaseValue(dodge);
        } else {
        }
    }

    public static void spellResistanceJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(ModList.get().isLoaded("sauce"))) return;
        if (!(event.getEntity() instanceof LivingEntity entity)) return;

        var attribute = entity.getAttribute(ModRegistry.SPELL_RESISTANCE);
        if (attribute == null) return;

        double resistance;

        if (entity instanceof Player) {
            resistance = CatastropheConfigs.playerResistance.getAsDouble();
        } else if (entity.getType().is(Tags.EntityTypes.BOSSES)) {
            resistance = CatastropheConfigs.bossResistance.getAsDouble();
        } else {
            resistance = CatastropheConfigs.mobResistance.getAsDouble();
        }

        attribute.setBaseValue(resistance);
    }

    public static void register() {
        NeoForge.EVENT_BUS.addListener(MobAttributes::mobModification);
        NeoForge.EVENT_BUS.addListener(MobAttributes::spellResistanceJoin);
    }
}
