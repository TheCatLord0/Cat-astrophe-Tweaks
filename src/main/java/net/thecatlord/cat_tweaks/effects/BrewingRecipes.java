package net.thecatlord.cat_tweaks.effects;

import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.sammy.malum.registry.common.item.MalumItems;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.thecatlord.cat_tweaks.CatastropheTweaks;

@EventBusSubscriber(modid = CatastropheTweaks.MODID)
public class BrewingRecipes {

    @SubscribeEvent
    public static void registerRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        if (ModList.get().isLoaded("malum")) {
            builder.addMix(
                    Potions.AWKWARD,
                    MalumItems.REFINED_SOULSTONE.get(),
                    PotionItem.INSTANT_SOULWARD_POTION_LV1
            );
            builder.addMix(
                    PotionItem.INSTANT_SOULWARD_POTION_LV1,
                    MalumItems.REFINED_BRILLIANCE.get(),
                    PotionItem.INSTANT_SOULWARD_POTION_LV2
            );
            builder.addMix(
                    PotionItem.INSTANT_SOULWARD_POTION_LV2,
                    MalumItems.SOUL_STAINED_STEEL_NUGGET.get(),
                    PotionItem.INSTANT_SOULWARD_POTION_LV3
            );
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            builder.addMix(
                    Potions.AWKWARD,
                    ItemsRegistry.SOURCE_GEM.get(),
                    PotionItem.INSTANT_MANA_POTION_LV1
            );
        }
    }

}