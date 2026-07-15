package net.fullstackjones.bigbraincurrency;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = BigBrainCurrency.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static final String CATEGORY_DISTRIBUTION = "BankDistribution";
    public static final String CATEGORY_DISTRIBUTION_PERIOD = "Period";
    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec.BooleanValue DIST_ENABLED;
    public static ModConfigSpec.IntValue DIST_AMOUNT;

    public static ModConfigSpec.IntValue DIST_PERIOD_DAYS;
    public static ModConfigSpec.IntValue DIST_PERIOD_HRS;
    public static ModConfigSpec.IntValue DIST_PERIOD_MINS;
    public static ModConfigSpec.IntValue DIST_PERIOD_SECS;

    static{
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
        COMMON_BUILDER
            .comment("Settings for bank distributions, which gives players coins periodically.")
            .push(CATEGORY_DISTRIBUTION);

        DIST_ENABLED = COMMON_BUILDER.comment("Whether distributions are enabled.")
            .define("enabled", true);
        DIST_AMOUNT = COMMON_BUILDER.comment("How many coins to distribute. [default: 3]")
            .defineInRange("amount", 3, 1, 64);

        COMMON_BUILDER
            .comment("Time between each distribution.")
            .push(CATEGORY_DISTRIBUTION_PERIOD);
        DIST_PERIOD_DAYS = COMMON_BUILDER.defineInRange("days", 1, 0, 365);
        DIST_PERIOD_HRS = COMMON_BUILDER.defineInRange("hours", 0, 0, 23);
        DIST_PERIOD_MINS = COMMON_BUILDER.defineInRange("minutes", 0, 0, 59);
        DIST_PERIOD_SECS = COMMON_BUILDER.defineInRange("seconds", 0, 0, 59);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {

    }
}
