package net.fullstackjones.bigbraincurrency.entities;

import net.fullstackjones.bigbraincurrency.Config;
import net.fullstackjones.bigbraincurrency.data.BrainBankData;
import net.fullstackjones.bigbraincurrency.menu.BrainBankMenu;
import net.fullstackjones.bigbraincurrency.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;


public class BrainBankBlockEntity extends BlockEntity implements MenuProvider {
    private final BrainBankData data = new BrainBankData();

    public BrainBankBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BRAINBANK_ENTITY.get(), pos, blockState);
    }

    public BrainBankData getData() {
        return data;
    }

    public void setLastDistribution(LocalDateTime lastDistribution) {
        data.setLastDistribution(lastDistribution);
        setChanged();
        if(!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void setData(BrainBankData data) {
        this.data.setLastDistribution(data.getLastDistribution());
        setChanged();
        if(!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.bigbraincurrency.brainbank");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BrainBankMenu(containerId, playerInventory, this);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("data", data.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        data.deserializeNBT(registries, tag.getCompound("data"));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public LocalDateTime nextDistributionAvailableAt() {
        if (!Config.DIST_ENABLED.get()) {
            return null;
        }
        LocalDateTime nextDistribution = this.data.getLastDistribution()
            .plusDays(Config.DIST_PERIOD_DAYS.get())
            .plusHours(Config.DIST_PERIOD_HRS.get())
            .plusMinutes(Config.DIST_PERIOD_MINS.get())
            .plusSeconds(Config.DIST_PERIOD_SECS.get());
        return nextDistribution;
    }

    public boolean nextDistributionAvailable() {
        LocalDateTime nextDistribution = nextDistributionAvailableAt();
        LocalDateTime now = LocalDateTime.now();
        return nextDistribution != null && nextDistribution.isBefore(now);
    }
}
