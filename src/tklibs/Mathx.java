package tklibs;

import java.util.Random;

/**
 * Created by huynq on 5/20/17.
 */
public class Mathx {
    private static Random random = new Random();

    public static double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static float randomInRange(float min, float max) {
        float range = max - min;
        return ((float)random.nextGaussian() * range) + min;
    }

    public static float uniformRandom(float mean, float devian) {
        return ((float)random.nextGaussian() * devian) + mean;
    }

    public static boolean inRange(float value, float min, float max) {
        return value >= min && value <= max;
    }
}
