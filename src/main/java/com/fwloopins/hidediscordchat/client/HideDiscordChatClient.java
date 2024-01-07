package com.fwloopins.hidediscordchat.client;

import com.fwloopins.hidediscordchat.client.config.HDCConfig;
import com.fwloopins.hidediscordchat.client.manager.FilterManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class HideDiscordChatClient implements ClientModInitializer {
    private static HDCConfig config = null;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(HDCConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HDCConfig.class).getConfig();

        FilterManager filterManager = new FilterManager();
        filterManager.allowGameListener();
    }

    public static HDCConfig getConfig() {
        return config;
    }
}
