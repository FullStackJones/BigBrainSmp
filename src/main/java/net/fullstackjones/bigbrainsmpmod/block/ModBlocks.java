package net.fullstackjones.bigbrainsmpmod.block;

import net.fullstackjones.bigbrainsmpmod.BigBrainSmpMod;
import net.fullstackjones.bigbrainsmpmod.block.custom.PiggyBankBlock;
import net.fullstackjones.bigbrainsmpmod.item.ModItems;
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
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BigBrainSmpMod.MODID);

    public static final DeferredBlock<Block> PIGGYBANK_BLOCK = registerBlock(
            "brainbank",
            () -> new PiggyBankBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0f, 2.0f)));

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
