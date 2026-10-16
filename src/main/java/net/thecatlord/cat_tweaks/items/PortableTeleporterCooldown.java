package net.thecatlord.cat_tweaks.items;


import mekanism.api.event.MekanismTeleportEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class PortableTeleporterCooldown {
    private static final Map<UUID, Long> Cooldown = new HashMap<>();

    @SubscribeEvent
    private static void onTeleport(MekanismTeleportEvent.PortableTeleporter event) {
        {

            var player = event.getEntity();
            long now = player.getServer().overworld().getGameTime();
            long cooldownEnd = Cooldown.getOrDefault(player.getUUID(), 0L);
            long secondsLeft = (long) Math.ceil((cooldownEnd - now) / 20);
            if (now < cooldownEnd) {
                player.displayClientMessage(Component.literal("Teleport is on cooldown for " + secondsLeft + "s").withStyle(ChatFormatting.GREEN), true);
                event.setCanceled(true);
                return;
            }

            Cooldown.put(player.getUUID(), now + (CatastropheConfigs.portableTeleporterCooldown.getAsInt() * 20L));
        }
    }
}
