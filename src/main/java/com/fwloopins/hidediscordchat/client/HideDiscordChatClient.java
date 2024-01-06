package com.fwloopins.hidediscordchat.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.List;

public class HideDiscordChatClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            List<Text> siblings = message.getSiblings();
            if (siblings.size() < 4) return true;

            Text indexOne = siblings.get(1);
            if (!indexOne.getString().equals("[")) return true;
            TextColor indexOneColour = indexOne.getStyle().getColor();
            if (indexOneColour == null) return true;
            if (!indexOneColour.equals(TextColor.fromFormatting(Formatting.GRAY))) return true;

            Text indexTwo = siblings.get(2);
            if (!indexTwo.getString().equals("Discord")) return true;
            TextColor indexTwoColour = indexTwo.getStyle().getColor();
            if (indexTwoColour == null) return true;
            if (!indexTwoColour.equals(TextColor.fromFormatting(Formatting.AQUA))) return true;

            Text indexThree = siblings.get(3);
            if (!indexThree.getString().split(" ")[0].equals("]")) return true;
            TextColor indexThreeColour = indexThree.getStyle().getColor();
            if (indexThreeColour == null) return true;
            return !indexThreeColour.equals(TextColor.fromFormatting(Formatting.GRAY));
        });
    }
}
