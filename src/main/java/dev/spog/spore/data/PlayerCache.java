package dev.spog.spore.data;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerCache {
    private static PlayerCache instance;
    private ConcurrentHashMap<NamespacedKey, Object> cache = new ConcurrentHashMap<>();
    private ConcurrentHashMap<NamespacedKey, Class<?>> classes = new ConcurrentHashMap<>();

    public void set(Player player, String address, Object value) {
        NamespacedKey key = new NamespacedKey(player.getUniqueId().toString(), address);
        cache.put(key, value);
        classes.put(key, value.getClass());
    }

    public <T> @Nullable T get(@NotNull Player player, @NotNull String address) {
        NamespacedKey key = new NamespacedKey(player.getUniqueId().toString(), address);
        Class<T> type = (Class<T>) classes.get(key);
        Object data = cache.get(key);
        if (data == null) return null;
        if (type.isInstance(data)) {
            return type.cast(data);
        }
        return null;
    }

    public Class<?> getClass(Player player, String address) {
        NamespacedKey key = new NamespacedKey(player.getUniqueId().toString(), address);
        return classes.get(key);
    }

    public void clearFor(Player player) {
        for (NamespacedKey key : cache.keySet()) {
            if (!key.getNamespace().equals(player.getUniqueId().toString())) continue;
            cache.remove(key);
            classes.remove(key);
        }
    }

    public void delete(NamespacedKey key) {
        cache.remove(key);
        classes.remove(key);
    }

    public void delete(Player player, String address) {
        NamespacedKey key = new NamespacedKey(player.getUniqueId().toString(), address);
        cache.remove(key);
        classes.remove(key);
    }

    public static PlayerCache getGlobal() {
        if (instance == null) {
            instance = new PlayerCache();
        }
        return instance;
    }
}
