package net.thecatlord.cat_tweaks.attributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTE =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, CatastropheTweaks.MODID);

    public static final Holder<Attribute> UNBREAKING_ATTRIBUTE = ATTRIBUTE.register("unbreaking",
            () -> new PercentageAttribute("cat_tweaks.attribute.unbreaking", 0.0D, 0.0D, 1.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTE.register(eventBus);
        eventBus.addListener(ModAttributes::modifyEntityAttributes);
    }

    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entity -> ATTRIBUTE.getEntries().forEach(attribute -> event.add(entity, attribute)));
    }
}
