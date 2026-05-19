package dev.spog.spore.minecraft.structure;

import com.google.gson.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.io.*;
import java.nio.file.*;
import java.util.Base64;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@Getter
@Setter
public class Schematic {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private Palette palette;
    private int[] blocks;

    private int width;
    private int height;
    private int length;

    public Schematic(Iterable<Block> input, int width, int height, int length) {
        this.palette = new Palette();
        this.width = width;
        this.height = height;
        this.length = length;
        this.blocks = new int[width * height * length];

        int i = 0;
        for (Block block : input) {
            blocks[i++] = palette.translate(block);
        }
    }

    public Schematic() {
        this.palette = new Palette();
    }

    private int index(int x, int y, int z) {
        return (y * width * length) + (z * width) + x;
    }

    public void paste(World world, int startX, int startY, int startZ) {
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {
                    BlockData data = palette.translate(blocks[i++]);
                    world.getBlockAt(startX + x, startY + y, startZ + z)
                            .setBlockData(data, false);
                }
            }
        }
    }

    public File toFile(File file) {
        JsonObject root = new JsonObject();
        root.addProperty("width", width);
        root.addProperty("height", height);
        root.addProperty("length", length);
        root.addProperty("blocks", encodeBlocks(blocks));

        JsonArray paletteArray = new JsonArray();
        for (String entry : palette.getEntries()) {
            paletteArray.add(entry);
        }
        root.add("palette", paletteArray);

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    public static Schematic fromFile(File file) {
        try (Reader reader = new BufferedReader(new FileReader(file))) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);

            Schematic s = new Schematic();
            s.width  = root.get("width").getAsInt();
            s.height = root.get("height").getAsInt();
            s.length = root.get("length").getAsInt();
            s.blocks = decodeBlocks(root.get("blocks").getAsString());

            JsonArray paletteArray = root.getAsJsonArray("palette");
            String[] entries = new String[paletteArray.size()];
            for (int i = 0; i < entries.length; i++) {
                entries[i] = paletteArray.get(i).getAsString();
            }
            s.palette.setEntries(entries);

            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Encode int[] as Base64 for compact JSON storage
    private static String encodeBlocks(int[] blocks) {
        ByteBuffer buf = ByteBuffer.allocate(blocks.length * 4);
        buf.asIntBuffer().put(blocks);
        return Base64.getEncoder().encodeToString(buf.array());
    }

    private static int[] decodeBlocks(String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        IntBuffer buf = ByteBuffer.wrap(bytes).asIntBuffer();
        int[] blocks = new int[buf.remaining()];
        buf.get(blocks);
        return blocks;
    }

    public boolean hasBlock(int worldX, int worldY, int worldZ, int originX, int originY, int originZ) {
        int localX = worldX - originX;
        int localY = worldY - originY;
        int localZ = worldZ - originZ;

        if (localX < 0 || localX >= width) return false;
        if (localY < 0 || localY >= height) return false;
        if (localZ < 0 || localZ >= length) return false;

        int i = index(localX, localY, localZ);
        return palette.translate(blocks[i]).getMaterial() != Material.AIR;
    }
}