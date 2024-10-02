package net.fullstackjones.bigbrainsmpmod.data;

import com.mojang.serialization.Codec;
import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class ModAttachmentTypes {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BigBrainSmpMod.MODID);

    public static final Supplier<AttachmentType<String>> UBI = ATTACHMENT_TYPES.register(
            "ubi", () -> AttachmentType.builder(() -> LocalDateTime.now().toString()).serialize(Codec.STRING).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
