package dev.spog.spore.minecraft.structure;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Palette {

    // Sequential IDs — use List for O(1) index lookup
    private final List<String> entries = new ArrayList<>();
    private final Map<String, Integer> reverse = new HashMap<>();

    // Cache parsed BlockData to avoid re-parsing identical blockstates
    private final List<BlockData> cache = new ArrayList<>();

    public int translate(Block block) {
        String key = block.getBlockData().getAsString(true);
        return reverse.computeIfAbsent(key, k -> {
            int id = entries.size();
            entries.add(k);
            cache.add(null); // lazy-populated in translate(int)
            return id;
        });
    }

    public BlockData translate(int id) {
        BlockData cached = cache.get(id);
        if (cached != null) return cached;

        BlockData data = Bukkit.createBlockData(entries.get(id));
        cache.set(id, data);
        return data;
    }

    public String[] getEntries() {
        return entries.toArray(new String[0]);
    }

    public void setEntries(String[] newEntries) {
        entries.clear();
        reverse.clear();
        cache.clear();

        for (String entry : newEntries) {
            reverse.put(entry, entries.size());
            entries.add(entry);
            cache.add(null);
        }
    }
}