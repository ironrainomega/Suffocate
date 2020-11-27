package com.tamashenning.suffocate;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("suffocate")
public class Suffocate {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Suffocate() {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                PlayerEntity player = event.player;
                if (player == Minecraft.getInstance().player) {
                    for (int i = 0; i < Config.effectsList.get().size(); i++) {
                        String effectName = Config.effectsList.get().get(i);
                        Integer yLevel = Config.yLevelList.get().get(i);
                        Integer amplifier = Config.amplifierList.get().get(i);

                        ResourceLocation location = ResourceLocation.tryCreate(effectName);
                        Effect effect = ForgeRegistries.POTIONS.getValue(location);

                        if (effect == null)
                            return;

                        if (player.getPosY() > yLevel) {
                            // LOGGER.debug("Adding effect, player position: " + player.getPosY());
                            player.addPotionEffect(new EffectInstance(effect, 60, amplifier, false, false));
                        }

                        EffectInstance currentEffect = player.getActivePotionEffect(effect);
                        if (currentEffect != null && currentEffect.getDuration() == 0) {
                            player.removePotionEffect(effect);
                        }
                    }
                }
            }
        }
    }
}
