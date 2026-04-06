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

    public void send(String permission) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission(permission)) continue;
            player.sendMessage(asComponent());
        }
    }
}
