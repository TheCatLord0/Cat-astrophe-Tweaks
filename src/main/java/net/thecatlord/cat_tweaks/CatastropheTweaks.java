package net.thecatlord.cat_tweaks;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(CatastropheTweaks.MODID)
public class CatastropheTweaks {
    public static final String MODID = "cat_tweaks";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CatastropheTweaks(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CatastropheConfigs.SPEC);
    }
}
