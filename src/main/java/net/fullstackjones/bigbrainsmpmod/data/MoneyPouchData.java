package net.fullstackjones.bigbrainsmpmod.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.fullstackjones.bigbrainsmpmod.item.ModItems;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Objects;

public final class MoneyPouchData implements IItemHandler {
    private final int slots;
    private final int pinkCoins;
    private final int goldCoins;
    private final int silverCoins;
    private final int copperCoins;

    public MoneyPouchData(int slots, int pinkCoins, int goldCoins, int silverCoins, int copperCoins) {
        this.slots = slots;
        this.pinkCoins = pinkCoins;
        this.goldCoins = goldCoins;
        this.silverCoins = silverCoins;
        this.copperCoins = copperCoins;
    }

    public int getPinkCoins() {
        return pinkCoins;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public int getSilverCoins() {
        return silverCoins;
    }

    public int getCopperCoins() {
        return copperCoins;
    }

    public MoneyPouchData withUpdatedCoins(int pinkCoins, int goldCoins, int silverCoins, int copperCoins) {
        return new MoneyPouchData(this.slots, pinkCoins, goldCoins, silverCoins, copperCoins);
    }

    public static final Codec<MoneyPouchData> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("slots").forGetter(MoneyPouchData::getSlots),
                    Codec.INT.fieldOf("pinkCoins").forGetter(MoneyPouchData::getPinkCoins),
                    Codec.INT.fieldOf("goldCoins").forGetter(MoneyPouchData::getGoldCoins),
                    Codec.INT.fieldOf("silverCoins").forGetter(MoneyPouchData::getSilverCoins),
                    Codec.INT.fieldOf("copperCoins").forGetter(MoneyPouchData::getCopperCoins)
            ).apply(instance, MoneyPouchData::new)
    );

    public static final StreamCodec<ByteBuf, MoneyPouchData> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MoneyPouchData::getSlots,
            ByteBufCodecs.INT, MoneyPouchData::getPinkCoins,
            ByteBufCodecs.INT, MoneyPouchData::getGoldCoins,
            ByteBufCodecs.INT, MoneyPouchData::getSilverCoins,
            ByteBufCodecs.INT, MoneyPouchData::getCopperCoins,
            MoneyPouchData::new
    );

    @Override
    public int getSlots() {
        return 4; // Number of coin types
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        switch (slot) {
            case 0: return new ItemStack(ModItems.PINKCOIN.asItem(), pinkCoins);
            case 1: return new ItemStack(ModItems.GOLDCOIN.asItem(), goldCoins);
            case 2: return new ItemStack(ModItems.SILVERCOIN.asItem(), silverCoins);
            case 3: return new ItemStack(ModItems.COPPERCOIN.asItem(), copperCoins);
            default: return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (!isItemValid(slot, stack)) return stack;

        int count = stack.getCount();
        if (simulate) {
            switch (slot) {
                case 0: return new ItemStack(ModItems.PINKCOIN.asItem(), Math.max(0, count - pinkCoins));
                case 1: return new ItemStack(ModItems.GOLDCOIN.asItem(), Math.max(0, count - goldCoins));
                case 2: return new ItemStack(ModItems.SILVERCOIN.asItem(), Math.max(0, count - silverCoins));
                case 3: return new ItemStack(ModItems.COPPERCOIN.asItem(), Math.max(0, count - copperCoins));
                default: return stack;
            }
        } else {
            MoneyPouchData updatedData;
            switch (slot) {
                case 0: updatedData = withUpdatedCoins(pinkCoins + count, goldCoins, silverCoins, copperCoins); break;
                case 1: updatedData = withUpdatedCoins(pinkCoins, goldCoins + count, silverCoins, copperCoins); break;
                case 2: updatedData = withUpdatedCoins(pinkCoins, goldCoins, silverCoins + count, copperCoins); break;
                case 3: updatedData = withUpdatedCoins(pinkCoins, goldCoins, silverCoins, copperCoins + count); break;
                default: return stack;
            }
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;

        switch (slot) {
            case 0:
                if (simulate) {
                    return new ItemStack(ModItems.PINKCOIN.asItem(), Math.min(amount, pinkCoins));
                } else {
                    int extracted = Math.min(amount, pinkCoins);
                    MoneyPouchData updatedData = withUpdatedCoins(pinkCoins - extracted, goldCoins, silverCoins, copperCoins);
                    return new ItemStack(ModItems.PINKCOIN.asItem(), extracted);
                }
            case 1:
                if (simulate) {
                    return new ItemStack(ModItems.GOLDCOIN.asItem(), Math.min(amount, goldCoins));
                } else {
                    int extracted = Math.min(amount, goldCoins);
                    MoneyPouchData updatedData = withUpdatedCoins(pinkCoins, goldCoins - extracted, silverCoins, copperCoins);
                    return new ItemStack(ModItems.GOLDCOIN.asItem(), extracted);
                }
            case 2:
                if (simulate) {
                    return new ItemStack(ModItems.SILVERCOIN.asItem(), Math.min(amount, silverCoins));
                } else {
                    int extracted = Math.min(amount, silverCoins);
                    MoneyPouchData updatedData = withUpdatedCoins(pinkCoins, goldCoins, silverCoins - extracted, copperCoins);
                    return new ItemStack(ModItems.SILVERCOIN.asItem(), extracted);
                }
            case 3:
                if (simulate) {
                    return new ItemStack(ModItems.COPPERCOIN.asItem(), Math.min(amount, copperCoins));
                } else {
                    int extracted = Math.min(amount, copperCoins);
                    MoneyPouchData updatedData = withUpdatedCoins(pinkCoins, goldCoins, silverCoins, copperCoins - extracted);
                    return new ItemStack(ModItems.COPPERCOIN.asItem(), extracted);
                }
            default: return ItemStack.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE; // Or any other limit you want to set
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        switch (slot) {
            case 0: return stack.getItem() == ModItems.PINKCOIN.asItem();
            case 1: return stack.getItem() == ModItems.GOLDCOIN.asItem();
            case 2: return stack.getItem() == ModItems.SILVERCOIN.asItem();
            case 3: return stack.getItem() == ModItems.COPPERCOIN.asItem();
            default: return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyPouchData that = (MoneyPouchData) o;
        return slots == that.slots &&
                pinkCoins == that.pinkCoins &&
                goldCoins == that.goldCoins &&
                silverCoins == that.silverCoins &&
                copperCoins == that.copperCoins;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slots, pinkCoins, goldCoins, silverCoins, copperCoins);
    }
}