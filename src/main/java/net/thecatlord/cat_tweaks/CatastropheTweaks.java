package net.thecatlord.cat_tweaks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.thecatlord.cat_tweaks.attributes.ModAttributes;
import net.thecatlord.cat_tweaks.effects.ModEffects;
import net.thecatlord.cat_tweaks.entities.MobAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CatastropheTweaks.MODID)
public class CatastropheTweaks {
    public static final String MODID = "cat_tweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public CatastropheTweaks(IEventBus eventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CatastropheConfigs.SPEC);
        ModEffects.register(eventBus);
        ModAttributes.register(eventBus);
        MobAttributes.register();
    }
}
