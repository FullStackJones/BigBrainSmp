package net.fullstackjones.bigbraincurrency.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.time.LocalDateTime;
import java.util.Objects;

public class BrainBankData implements INBTSerializable<CompoundTag> {
    protected LocalDateTime mLastDistribution;

    public BrainBankData() {
        this(LocalDateTime.MIN);
    }

    public BrainBankData(LocalDateTime lastDistribution) {
        this.mLastDistribution = lastDistribution;
    }

    public LocalDateTime getLastDistribution() {
        return mLastDistribution;
    }

    public void setLastDistribution(LocalDateTime lastDistribution) {
        this.mLastDistribution = lastDistribution;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("LastDistribution", mLastDistribution.toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        setLastDistribution(LocalDateTime.parse(nbt.getString("LastDistribution")));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLastDistribution());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof BrainBankData ex
                    && this.getLastDistribution() == ex.getLastDistribution();
        }
    }
}
