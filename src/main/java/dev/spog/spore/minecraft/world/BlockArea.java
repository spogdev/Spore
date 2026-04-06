package dev.spog.spore.minecraft.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockArea {
    private Location center;
    private int radius;

    public BlockArea(Location center, int radius) {
        this.radius = radius;
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public List<Block> getBlocksInCube() {
        List<Block> blocks = new ArrayList<>();
        World world = center.getWorld();
        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(world.getBlockAt(cx + x, cy + y, cz + z));
                }
            }
        }
        return blocks;
    }

    public List<Block> getBlocksInRadius() {
        List<Block> blocks = new ArrayList<>();
        World world = center.getWorld();
        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();
        int rSquared = radius * radius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z > rSquared) continue;
                    blocks.add(world.getBlockAt(cx + x, cy + y, cz + z));
                }
            }
        }
        return blocks;
    }
}
