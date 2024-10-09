package net.fullstackjones.bigbraincurrency.block;

import net.fullstackjones.bigbraincurrency.BigBrainCurrency;
import net.fullstackjones.bigbraincurrency.block.custom.PiggyBankBlock;
import net.fullstackjones.bigbraincurrency.block.custom.ShopBlock;
import net.fullstackjones.bigbraincurrency.entitiys.block.ShopEntity;
import net.fullstackjones.bigbraincurrency.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BigBrainCurrency.MODID);

    public static final DeferredBlock<Block> PIGGYBANK_BLOCK = registerBlock(
            "brainbank",
            () -> new PiggyBankBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0f, 2.0f)));

    public static final DeferredBlock<Block> SHOP_BLOCK = registerBlock(
            "shop",
            () -> new ShopBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0f, 2.0f)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
