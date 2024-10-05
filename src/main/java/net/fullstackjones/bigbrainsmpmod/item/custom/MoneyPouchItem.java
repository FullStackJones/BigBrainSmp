package net.fullstackjones.bigbrainsmpmod.item.custom;

import net.fullstackjones.bigbrainsmpmod.menu.MoneyPouchContainer;
import net.fullstackjones.bigbrainsmpmod.menu.Screens;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

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

    private InteractionResult openPouch(@Nullable Player player, ItemStack stack, Level world)
    {
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer)
        {
            Screens.openMoneyPouch(serverPlayer, serverPlayer.getInventory().selected);
        }

        return InteractionResult.SUCCESS;
    }


}
