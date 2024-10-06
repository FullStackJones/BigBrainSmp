package net.fullstackjones.bigbrainsmpmod.data;

import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData.BASIC_CODEC;
import static net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData.BASIC_STREAM_CODEC;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(BigBrainSmpMod.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MoneyPouchData>> MONEYPOUCH_DATA = REGISTRAR.registerComponentType(
            "moneypouch_data",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(BASIC_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(BASIC_STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }


}
