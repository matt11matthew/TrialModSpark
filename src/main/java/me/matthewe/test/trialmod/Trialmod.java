package me.matthewe.test.trialmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandles;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Trialmod.MODID)
public class Trialmod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "trialmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "trialmod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "trialmod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "trialmod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // A custom storage-style block crafted from charcoal, with its own texture/model/recipe.
    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .strength(5.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> CHARCOAL_BLOCK_ITEM = ITEMS.register("charcoal_block", () -> new BlockItem(CHARCOAL_BLOCK.get(), new Item.Properties()));

    // Dedicated mod tab that groups all mod content and uses charcoal block as icon.
    public static final RegistryObject<CreativeModeTab> TRIALMOD_TAB = CREATIVE_MODE_TABS.register("trialmod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.trialmod.trialmod_tab"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> CHARCOAL_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CHARCOAL_BLOCK_ITEM.get());
            }).build());

    public Trialmod(FMLJavaModLoadingContext context) {
        // 1. Get the new BusGroup from the context
        var modBusGroup = context.getModBusGroup();

        // 2. Register this class to the modBusGroup using MethodHandles (EventBus 7 standard)
        modBusGroup.register(MethodHandles.lookup(), this);

        // (Optional alternative: If you prefer listener additions over @SubscribeEvent annotations for lifecycle events)
        // FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // 3. Register the Deferred Registers directly to the BusGroup
        BLOCKS.register(modBusGroup);
        ITEMS.register(modBusGroup);
        CREATIVE_MODE_TABS.register(modBusGroup);

        // 4. Register for server and game events on the main Forge bus
        MinecraftForge.EVENT_BUS.register(MethodHandles.lookup(), this);

        // 5. Register configs directly through the context object now
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



}
