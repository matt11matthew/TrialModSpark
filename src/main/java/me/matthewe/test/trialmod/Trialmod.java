package me.matthewe.test.trialmod;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier; // The 1.21.11 Replacement!
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(Trialmod.MODID)
public class Trialmod {

    public static final String MODID = "trialmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create Deferred Registers
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 1.21.11 FIX: ResourceLocation is now Identifier!
    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .strength(5.0f, 6.0f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
            .setId(ResourceKey.create(Registries.BLOCK, Identifier.parse(MODID + ":charcoal_block")))));

    // 1.21.11 FIX: ResourceLocation is now Identifier!
    public static final RegistryObject<Item> CHARCOAL_BLOCK_ITEM = ITEMS.register("charcoal_block", () -> new BlockItem(CHARCOAL_BLOCK.get(), new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, Identifier.parse(MODID + ":charcoal_block")))
            .useBlockDescriptionPrefix()));

    // Dedicated mod tab
    public static final RegistryObject<CreativeModeTab> TRIALMOD_TAB = CREATIVE_MODE_TABS.register("trialmod_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.trialmod.trialmod_tab"))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> CHARCOAL_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(CHARCOAL_BLOCK_ITEM.get());
            }).build());

    public Trialmod(FMLJavaModLoadingContext context) {
        // Grab the active BusGroup
        var modBusGroup = context.getModBusGroup();

        // 1. Mod Bus Setup
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        BLOCKS.register(modBusGroup);
        ITEMS.register(modBusGroup);
        CREATIVE_MODE_TABS.register(modBusGroup);

        // 2. Forge Bus Setup
        ServerStartingEvent.BUS.addListener(this::onServerStarting);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    private void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}