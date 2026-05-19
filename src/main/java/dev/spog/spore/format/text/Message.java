package dev.spog.spore.format.text;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Message {
    private String message;

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

        return legacy_hex.deserialize(this.message);
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
