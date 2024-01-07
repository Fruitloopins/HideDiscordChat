package com.fwloopins.hidediscordchat.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "HDC")
public class HDCConfig implements ConfigData {
    @ConfigEntry.Category("Settings")
    @ConfigEntry.Gui.TransitiveObject
    public Settings settings = new Settings();

    public static class Settings {
        @ConfigEntry.Gui.Tooltip
        public boolean enabled = true;
        @ConfigEntry.Gui.Tooltip
        public String filteredRanks = "*";
        @ConfigEntry.Gui.Tooltip
        public boolean exclusiveFilter = false;
    }
}
