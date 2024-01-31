package com.fwloopins.hideemcannoyances.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "HEA")
public class HEAConfig implements ConfigData {
    @ConfigEntry.Category("Settings")
    @ConfigEntry.Gui.TransitiveObject
    public Settings settings = new Settings();

    public static class Settings {
        @ConfigEntry.Gui.Tooltip
        public boolean enabled = true;
        @ConfigEntry.Gui.Tooltip
        public boolean hideObamaMessages = true;
        @ConfigEntry.Gui.Tooltip
        public boolean hideVoteMessages = true;
        @ConfigEntry.Gui.Tooltip
        public boolean hideKillMessages = true;
        @ConfigEntry.Category("Discord")
        @ConfigEntry.Gui.CollapsibleObject
        public Discord discord = new Discord();

        public static class Discord {
            @ConfigEntry.Gui.Tooltip
            public boolean hideDiscordMessages = true;
            @ConfigEntry.Gui.Tooltip
            public String filteredRanks = "*";
            @ConfigEntry.Gui.Tooltip
            public boolean exclusiveFilter = false;
        }
    }
}
