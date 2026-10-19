package net.thecatlord.cat_tweaks.items;

import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.thecatlord.cat_tweaks.CatastropheConfigs;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class ItemCooldowns {
    @SubscribeEvent
    public static void addCooldowns(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide) return;
        if (!ModList.get().isLoaded("ars_nouveau")) return;
        if (event.getItemStack().is(ItemsRegistry.WARP_SCROLL.get())) {
            if (event.getEntity().getCooldowns().isOnCooldown(ItemsRegistry.WARP_SCROLL.get())) {
                event.setCanceled(true);
                return;
            }
            event.getEntity().getCooldowns().addCooldown(
                    ItemsRegistry.WARP_SCROLL.get(),
                    CatastropheConfigs.warpScrollCooldown.getAsInt()
            );
        }
        else if (event.getItemStack().is(ItemsRegistry.STABLE_WARP_SCROLL.get())) {
            if (event.getEntity().getCooldowns().isOnCooldown(ItemsRegistry.STABLE_WARP_SCROLL.get())) {
                event.setCanceled(true);
                return;
            }
            event.getEntity().getCooldowns().addCooldown(

                    ItemsRegistry.STABLE_WARP_SCROLL.get(),
                    CatastropheConfigs.stabilizedWarpCooldown.getAsInt()
            );
        }
    }
}
