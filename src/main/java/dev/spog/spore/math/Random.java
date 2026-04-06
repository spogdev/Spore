package dev.spog.spore.math;

import java.util.List;

public class Random {
    public static int randInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static boolean randomFromPercent(double percent) {
        return randInt(1, 100) <= percent;
    }

    public static Object randomFromList(List<?> list) {
        return list.get(randInt(0, list.size() - 1));
    }
}
