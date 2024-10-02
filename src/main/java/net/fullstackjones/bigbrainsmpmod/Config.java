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

    private static final ModConfigSpec.ConfigValue<Integer>  PIGGY_BANK_COIN_VALUE = BUILDER
            .comment("the coin value you are able to withdraw from the piggy bank")
            .define("piggybankcoinvalue", 1);

    private static final ModConfigSpec.ConfigValue<Integer>  PIGGY_BANK_COIN_AMOUNT = BUILDER
            .comment("the amount of coins you are able to withdraw from the piggy bank")
            .define("piggybankcoinamount", 1);
    static final ModConfigSpec SPEC = BUILDER.build();

    public static int[] coinValues;
    public static int piggyBankCoinAmount;
    public static int piggyBankCoinValue;

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
        piggyBankCoinAmount = PIGGY_BANK_COIN_AMOUNT.get();
        piggyBankCoinValue = PIGGY_BANK_COIN_VALUE.get();
    }
}
