package net.fullstackjones.bigbrainsmpmod.menu;

import net.fullstackjones.bigbrainsmpmod.item.custom.MoneyPouchItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;

public class Screens {
    public static void openMoneyPouch(ServerPlayer player, int slot)
    {
        final ItemStack heldItem = player.getInventory().getItem(slot).copy();
        if (heldItem.getCount() > 0 && heldItem.getItem() instanceof MoneyPouchItem)
        {
            player.openMenu(new SimpleMenuProvider(
                    (i, playerInventory, playerEntity) -> new MoneyPouchContainer(i, playerInventory),
                    heldItem.getHoverName()
            ), (data) -> {
                data.writeVarInt(slot);
                ItemStack.OPTIONAL_STREAM_CODEC.encode(data, heldItem);
            });
        }
    }
}
