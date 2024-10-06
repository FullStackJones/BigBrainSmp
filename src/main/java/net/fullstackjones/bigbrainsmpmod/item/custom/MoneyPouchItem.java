package net.fullstackjones.bigbrainsmpmod.item.custom;

import net.fullstackjones.bigbrainsmpmod.data.ModDataComponents;
import net.fullstackjones.bigbrainsmpmod.data.MoneyPouchData;
import net.fullstackjones.bigbrainsmpmod.menu.Screens;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.UnaryOperator;

public class MoneyPouchItem extends Item {
    public MoneyPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (usedHand != InteractionHand.MAIN_HAND)
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        InteractionResult result = openPouch(player, stack, level);
        return new InteractionResultHolder<>(result, stack);
    }

    private InteractionResult openPouch(@Nullable Player player, ItemStack stack, Level world) {
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            MoneyPouchData data = getMoneyPouchData(stack);
            if(data == null) {
                data = new MoneyPouchData(4, 0, 0, 0, 0);
            }
            // Pass data to the component
            Screens.openMoneyPouch(serverPlayer, serverPlayer.getInventory().selected, data);
        }
        return InteractionResult.SUCCESS;
    }

    private MoneyPouchData getMoneyPouchData(ItemStack stack) {
        DataComponentMap components = stack.getComponents();
        DataComponentType<MoneyPouchData> type = ModDataComponents.MONEYPOUCH_DATA.get();
        return components.get(type);
    }

    public void setMoneyPouchData(ItemStack stack, MoneyPouchData data) {
        DataComponentType<MoneyPouchData> type = ModDataComponents.MONEYPOUCH_DATA.get();
        UnaryOperator<MoneyPouchData> updater = existingData -> data;
        stack.update(type, data,updater);
    }


}

