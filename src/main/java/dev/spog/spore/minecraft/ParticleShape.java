package dev.spog.spore.minecraft;

import dev.spog.spore.Spore;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParticleShape {
    private Particle particle;
    private Location origin;
    private Particle.DustOptions dustOptions;

    public ParticleShape(Location origin, Particle particle, Particle.DustOptions dustOptions) {
        this.particle = particle;
        this.origin = origin;
        this.dustOptions = dustOptions;
    }

    public ParticleShape(Location origin, Particle particle) {
        this.particle = particle;
        this.origin = origin;
        this.dustOptions = null;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Particle.DustOptions getDustOptions() {
        return dustOptions;
    }

    public void setDustOptions(Particle.DustOptions dustOptions) {
        this.dustOptions = dustOptions;
    }

    public List<BoundingBox> circle(double radius) {
        int points = (int) (radius * 40);
        List<BoundingBox> boxes = new ArrayList<>();
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            Location particleLoc = origin.clone().add(x, 0, z);
            BoundingBox box = new BoundingBox(particleLoc.getX() - 0.1, particleLoc.getY() - 0.1, particleLoc.getZ() - 0.1, particleLoc.getX() + 0.1, particleLoc.getY() + 0.1, particleLoc.getZ() + 0.1);
            origin.getWorld().spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
            boxes.add(box);
        }
        return boxes;
    }

    public List<BoundingBox> zone(int rings, long delayBetween) {
        AtomicInteger ringsCounter = new AtomicInteger(1);
        List<BoundingBox> boxes = new ArrayList<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (ringsCounter.get() > rings) {
                    cancel();
                }
                boxes.addAll(ParticleShape.this.circle(ringsCounter.get()));
                ringsCounter.incrementAndGet();
            }
        }.runTaskTimer(Spore.getPlugin(), 0, delayBetween);
        return boxes;
    }

    public @Nullable List<BoundingBox> line(Location destination, int count, double spacing, double maxOffset) throws IllegalArgumentException {
        List<BoundingBox> boxes = new ArrayList<>();
        origin.getWorld().loadChunk(origin.getChunk());
        if (!origin.getWorld().equals(destination.getWorld())) return null;
        double distance = origin.distance(destination);

        if (distance > 100) throw new IllegalArgumentException("Distance too large (Must be <= 100)");

        World world = origin.getWorld();

        double dx = destination.getX() - origin.getX();
        double dy = destination.getY() - origin.getY();
        double dz = destination.getZ() - origin.getZ();

        int points = (int) (distance / spacing);

        for (int i = 0; i <= points; i++) {
            double x = origin.getX() + dx * i / points;
            double y = origin.getY() + dy * i / points;
            double z = origin.getZ() + dz * i / points;
            Location particleLoc = new Location(world, x, y, z);
            BoundingBox box = new BoundingBox(particleLoc.getX() - 0.1, particleLoc.getY() - 0.1, particleLoc.getZ() - 0.1, particleLoc.getX() + 0.1, particleLoc.getY() + 0.1, particleLoc.getZ() + 0.1);
            world.spawnParticle(particle, particleLoc, count, maxOffset, maxOffset, maxOffset, dustOptions);
            boxes.add(box);
        }

        return boxes;
    }
}
