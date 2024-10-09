package net.fullstackjones.bigbraincurrency.entitiys;

import net.fullstackjones.bigbraincurrency.BigBrainCurrency;
import net.fullstackjones.bigbraincurrency.block.ModBlocks;
import net.fullstackjones.bigbraincurrency.entitiys.block.ShopEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BigBrainCurrency.MODID);

    public static final Supplier<BlockEntityType<ShopEntity>> SHOP_ENTITY = BLOCK_ENTITY_TYPES.register(
            "shopentity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            ShopEntity::new,
                            ModBlocks.SHOP_BLOCK.get()
                    )
                    .build(null)
    );
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
