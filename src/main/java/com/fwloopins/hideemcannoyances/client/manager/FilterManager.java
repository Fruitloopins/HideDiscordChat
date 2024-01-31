package com.fwloopins.hideemcannoyances.client.manager;

import com.fwloopins.hideemcannoyances.client.HideEMCAnnoyancesClient;
import com.fwloopins.hideemcannoyances.client.config.HEAConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class FilterManager {
    HEAConfig.Settings settings = HideEMCAnnoyancesClient.getConfig().settings;

    public void allowGameListener() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (!settings.enabled) return true;

            if (settings.hideObamaMessages && isMessageFromObama(message)) return false;
            if (settings.hideVoteMessages && isMessageVoteMessage(message)) return false;
            if (settings.hideKillMessages && isMessageKillMessage(message)) return false;

            if (settings.discord.hideDiscordMessages && isMessageFromDiscord(message)) {
                String filteredRanks = settings.discord.filteredRanks;
                boolean exclusiveFilter = settings.discord.exclusiveFilter;
                if (filteredRanks.equals("*")) return exclusiveFilter;

                List<String> filteredRanksList = getFilteredRanksList(settings.discord.filteredRanks.split(","));
                String playerRank = message.getSiblings().get(0).getString().stripTrailing();

                if (exclusiveFilter) {
                    return filteredRanksList.contains(playerRank);
                } else {
                    return !filteredRanksList.contains(playerRank);
                }
            }

            return true;
        });
    }

    private boolean isMessageFromObama(Text message) {
        List<Text> siblings = message.getSiblings();
        if (siblings.size() < 6) return false;

        Text indexZero = siblings.get(0);
        if (!indexZero.getString().equals("[")) return false;
        TextColor indexZeroColour = indexZero.getStyle().getColor();
        if (indexZeroColour == null) return false;
        if (!indexZeroColour.equals(TextColor.fromFormatting(Formatting.GOLD))) return false;

        Text indexOne = siblings.get(1);
        return (indexOne.getString().equals("Michelle Obama "));
    }

    private boolean isMessageVoteMessage(Text message) {
        List<Text> siblings = message.getSiblings();
        if (siblings.size() < 4) return false;

        TextColor indexZeroColour = siblings.get(0).getStyle().getColor();
        if (indexZeroColour == null) return false;
        if (!indexZeroColour.equals(TextColor.fromFormatting(Formatting.GREEN))) return false;

        Text indexOne = siblings.get(1);
        if (!indexOne.getString().equals("has voted and received ")) return false;
        TextColor indexOneColour = indexOne.getStyle().getColor();
        if (indexOneColour == null) return false;
        if (!indexOneColour.equals(TextColor.fromFormatting(Formatting.GOLD))) return false;

        Text indexTwo = siblings.get(2);
        if (!indexTwo.getString().equals("a gold crate! ")) return false;
        TextColor indexTwoColour = indexTwo.getStyle().getColor();
        if (indexTwoColour == null) return false;
        if (!indexTwoColour.equals(TextColor.fromFormatting(Formatting.GREEN))) return false;

        Text indexThree = siblings.get(3);
        if (!indexThree.getString().equals("/vote")) return false;
        TextColor indexThreeColour = indexThree.getStyle().getColor();
        if (indexThreeColour == null) return false;
        return (indexThreeColour.equals(TextColor.fromFormatting(Formatting.GRAY)));
    }

    private boolean isMessageKillMessage(Text message) {
        List<Text> siblings = message.getSiblings();
        if (siblings.size() < 2) return false;

        Text indexZero = siblings.get(0);
        if (!indexZero.getString().equals("☠ ")) return false;
        TextColor indexZeroColour = indexZero.getStyle().getColor();
        if (indexZeroColour == null) return false;
        return indexZeroColour.equals(TextColor.fromFormatting(Formatting.RED));
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
