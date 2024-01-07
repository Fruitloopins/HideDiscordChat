package com.fwloopins.hidediscordchat.client.manager;

import com.fwloopins.hidediscordchat.client.HideDiscordChatClient;
import com.fwloopins.hidediscordchat.client.config.HDCConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FilterManager {
    HDCConfig.Settings settings = HideDiscordChatClient.getConfig().settings;

    public void allowGameListener() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (!settings.enabled) return true;
            if (!isMessageFromDiscord(message)) return true;

            String filteredRanks = settings.filteredRanks;
            boolean exclusiveFilter = settings.exclusiveFilter;
            if (filteredRanks.equals("*")) return exclusiveFilter;

            List<String> filteredRanksList = getFilteredRanksList(settings.filteredRanks.split(","));
            String playerRank = message.getSiblings().get(0).getString().stripTrailing();

            if (exclusiveFilter) {
                return filteredRanksList.contains(playerRank);
            } else {
                return !filteredRanksList.contains(playerRank);
            }
        });
    }

    private boolean isMessageFromDiscord(Text message) {
        List<Text> siblings = message.getSiblings();
        if (siblings.size() < 4) return false;

        Text indexOne = siblings.get(1);
        if (!indexOne.getString().equals("[")) return false;
        TextColor indexOneColour = indexOne.getStyle().getColor();
        if (indexOneColour == null) return false;
        if (!indexOneColour.equals(TextColor.fromFormatting(Formatting.GRAY))) return false;

        Text indexTwo = siblings.get(2);
        if (!indexTwo.getString().equals("Discord")) return false;
        TextColor indexTwoColour = indexTwo.getStyle().getColor();
        if (indexTwoColour == null) return false;
        if (!indexTwoColour.equals(TextColor.fromFormatting(Formatting.AQUA))) return false;

        Text indexThree = siblings.get(3);
        if (!indexThree.getString().split(" ")[0].equals("]")) return false;
        TextColor indexThreeColour = indexThree.getStyle().getColor();
        if (indexThreeColour == null) return false;
        return indexThreeColour.equals(TextColor.fromFormatting(Formatting.GRAY));
    }

    private List<String> getFilteredRanksList(String[] filteredRanks) {
        List<String> filteredRanksList = new ArrayList<>();
        for (String rank : filteredRanks) {
            filteredRanksList.add(rank.stripLeading().stripTrailing());
        }

        return filteredRanksList;
    }
}
