package dev.spog.spore.format.text;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.util.ARGBLike;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String message;
    private String shadowColor = null;

    public Message(String message) {
        this.message = message;
    }

    public static Message of(String message) {
        return new Message(message);
    }

    public String getContent() {
        return message;
    }

    public void setContent(String message) {
        this.message = message;
    }

    public @NotNull TextComponent asComponent() {
        LegacyComponentSerializer legacy_hex = LegacyComponentSerializer.builder()
                .character('&')
                .hexColors()
                .useUnusualXRepeatedCharacterHexFormat()
                .build();

        Component deserialized = legacy_hex.deserialize(this.message);

        ShadowColor shadowHighlight = shadowColor == null ? ShadowColor.none() : ShadowColor.fromHexString(shadowColor);

        return (TextComponent) deserialized.shadowColor(shadowHighlight);
    }

    public void shadow(String hex) {
        shadowColor = hex;
    }

    public void send(Audience player) {
        player.sendMessage(asComponent());
    }

    public void announce(String permission) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission(permission)) continue;
            player.sendMessage(asComponent());
        }
    }

    public void announce() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(asComponent());
        }
    }

    public static List<TextComponent> processStringList(List<String> stringList) {
        List<TextComponent> components = new ArrayList<>();
        for (String line : stringList) {
            components.add(Message.of(line).asComponent());
        }
        return components;
    }

    public Message toSmallCaps() {
        String text = this.getContent().toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c) {
                case 'a' -> builder.append('ᴀ');
                case 'b' -> builder.append('ʙ');
                case 'c' -> builder.append('ᴄ');
                case 'd' -> builder.append('ᴅ');
                case 'e' -> builder.append('ᴇ');
                case 'f' -> builder.append('ғ');
                case 'g' -> builder.append('ɢ');
                case 'h' -> builder.append('ʜ');
                case 'i' -> builder.append('ɪ');
                case 'j' -> builder.append('ᴊ');
                case 'k' -> builder.append('ᴋ');
                case 'l' -> builder.append('ʟ');
                case 'm' -> builder.append('ᴍ');
                case 'n' -> builder.append('ɴ');
                case 'o' -> builder.append('ᴏ');
                case 'p' -> builder.append('ᴘ');
                case 'q' -> builder.append('ǫ');
                case 'r' -> builder.append('ʀ');
                case 's' -> builder.append('ꜱ');
                case 't' -> builder.append('ᴛ');
                case 'u' -> builder.append('ᴜ');
                case 'v' -> builder.append('ᴠ');
                case 'w' -> builder.append('ᴡ');
                case 'x' -> builder.append('x');
                case 'y' -> builder.append('ʏ');
                case 'z' -> builder.append('ᴢ');
                case '1' -> builder.append('¹');
                case '2' -> builder.append('²');
                case '3' -> builder.append('³');
                case '4' -> builder.append('⁴');
                case '5' -> builder.append('⁵');
                case '6' -> builder.append('⁶');
                case '7' -> builder.append('⁷');
                case '8' -> builder.append('⁸');
                case '9' -> builder.append('⁹');
                case '0' -> builder.append('⁰');
                default -> builder.append(c);
            }
        }

        this.setContent(builder.toString());

        return this;
    }
}
