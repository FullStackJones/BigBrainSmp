package net.fullstackjones.bigbrainsmpmod;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = BigBrainSmpMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>>  COIN_VALUES = BUILDER
            .comment("Coin Values lowest to highest. (1,9,81,729)")
            .defineListAllowEmpty("coinValues", List.of(1,9,81,729), Config::validateCoinValues);
    static final ModConfigSpec SPEC = BUILDER.build();

    public static int[] coinValues;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    private static boolean validateCoinValues(final Object obj)
    {
        return obj instanceof int[];
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        coinValues = COIN_VALUES.get().stream().mapToInt(i -> i).toArray();
    }
}
