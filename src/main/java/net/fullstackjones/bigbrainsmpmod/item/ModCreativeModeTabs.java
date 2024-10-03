package net.fullstackjones.bigbrainsmpmod.item;


import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.fullstackjones.bigbrainsmpmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BigBrainSmpMod.MODID);

    public static final Supplier<CreativeModeTab> BIGBRAINSMPTAB = CREATIVE_MODE_TAB.register(
            "bigbrainsmp_tab",
            () -> CreativeModeTab.builder().icon(() ->
                    new ItemStack(ModItems.GOLDCOIN.get()))
                    .title(Component.translatable("creativetab.bigbrainsmpmod.bigbrainsmp_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.COPPERCOIN);
                        output.accept(ModItems.SILVERCOIN);
                        output.accept(ModItems.GOLDCOIN);
                        output.accept(ModItems.PINKCOIN);
                        output.accept(ModBlocks.PIGGYBANK_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
