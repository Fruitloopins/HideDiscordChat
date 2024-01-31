package com.fwloopins.hideemcannoyances.client;

import com.fwloopins.hideemcannoyances.client.config.HEAConfig;
import com.fwloopins.hideemcannoyances.client.manager.FilterManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public class HideEMCAnnoyancesClient implements ClientModInitializer {
    private static HEAConfig config = null;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(HEAConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HEAConfig.class).getConfig();

        FilterManager filterManager = new FilterManager();
        filterManager.allowGameListener();
    }

    public static HEAConfig getConfig() {
        return config;
    }
}
