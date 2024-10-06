package net.fullstackjones.bigbrainsmpmod;

import net.fullstackjones.bigbrainsmpmod.block.ModBlocks;
import net.fullstackjones.bigbrainsmpmod.data.ModAttachmentTypes;
import net.fullstackjones.bigbrainsmpmod.data.ModDataComponents;
import net.fullstackjones.bigbrainsmpmod.item.ModCreativeModeTabs;
import net.fullstackjones.bigbrainsmpmod.item.ModItems;
import net.fullstackjones.bigbrainsmpmod.menu.ModContainers;
import net.fullstackjones.bigbrainsmpmod.menu.MoneyPouchContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BigBrainSmpMod.MODID)
public class BigBrainSmpMod
{
    public static final String MODID = "bigbrainsmpmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public BigBrainSmpMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ClientInit.init(modEventBus);
        ModAttachmentTypes.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModContainers.register(modEventBus);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
