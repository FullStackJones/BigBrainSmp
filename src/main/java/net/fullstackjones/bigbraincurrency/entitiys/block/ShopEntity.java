package net.fullstackjones.bigbraincurrency.entitiys.block;

import net.fullstackjones.bigbraincurrency.entitiys.ModBlockEntitys;
import net.fullstackjones.bigbraincurrency.menu.ShopMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShopEntity extends BaseContainerBlockEntity {
    public static final int SIZE = 27;
    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    public ShopEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntitys.SHOP_ENTITY.get(), pPos, pBlockState);
    }

    public int getContainerSize() {
        return SIZE;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.bigbraincurrency.shopentity");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ShopMenu(containerId, inventory);
    }
}
