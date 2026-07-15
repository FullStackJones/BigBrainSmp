package net.fullstackjones.bigbraincurrency.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.time.LocalDateTime;

public class BrainBankDataCodec {
    public static final Codec<BrainBankData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(LocalDateTime::parse, LocalDateTime::toString).fieldOf("LastDistribution").forGetter(BrainBankData::getLastDistribution)
    ).apply(instance, BrainBankData::new));

    public static final StreamCodec<ByteBuf, BrainBankData> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(36).map(LocalDateTime::parse, LocalDateTime::toString), BrainBankData::getLastDistribution,
            BrainBankData::new
    );
}
