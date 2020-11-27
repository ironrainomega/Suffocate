package com.tamashenning.suffocate;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "General";

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> effectsList;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> yLevelList;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> amplifierList;

    public static ForgeConfigSpec COMMON_CONFIG;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);


        effectsList = COMMON_BUILDER.comment("List of effects to give").
                defineList("Effects", Arrays.asList("minecraft:slowness", "minecraft:luck"), (n) -> { return true; });

        yLevelList = COMMON_BUILDER.comment("List of Y value after which the effect will kick in").
                defineList("yLevelList", Arrays.asList(200, 254), (n) -> { return true; });

        amplifierList = COMMON_BUILDER.comment("List of amplifiers for the effect... generally keep it 1").
                defineList("amplifierList", Arrays.asList(1, 1), (n) -> { return true; });


        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}
