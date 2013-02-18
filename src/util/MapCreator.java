package util;

import java.util.Random;

public abstract class MapCreator {

    private static final Random rnd = new Random();

    private static byte nextByte(float range) {
        int num =  Byte.MIN_VALUE + rnd.nextInt(Byte.MAX_VALUE - Byte.MIN_VALUE);
        return (byte)Math.round(num * Math.max(0, Math.min(1, range)));
    }

    public static byte[][] newHeightMap(int scale, float smoothness) {
        int totalSize = 1 << scale;
        byte[][] heightMap = new byte[totalSize + 1][totalSize +1 ];
        int stepSize = totalSize;
        float range = 1;
        heightMap[0][0] = nextByte(range);
        heightMap[0][stepSize] = nextByte(range);
        heightMap[stepSize][0] = nextByte(range);
        heightMap[stepSize][stepSize] = nextByte(range);
        while (stepSize > 1) {
            for (int x = 0; x < totalSize; x += stepSize) {
                for (int y = 0; y < totalSize; y += stepSize) {
                    heightMap[x + stepSize / 2][y + stepSize / 2] =
                            (byte)((heightMap[x][y] >> 2) +
                                   (heightMap[x][y + stepSize] >> 2) +
                                   (heightMap[x + stepSize][y] >> 2) +
                                   (heightMap[x + stepSize][y + stepSize] >> 2) +
                                   nextByte(range));

                    heightMap[x + stepSize / 2][y] =
                            (byte)((heightMap[x][y] >> 1) +
                                   (heightMap[x + stepSize][y] >> 1) +
                                   nextByte(range));
                    heightMap[x + stepSize / 2][y + stepSize] =
                            (byte)((heightMap[x][y + stepSize] >> 1) +
                                   (heightMap[x + stepSize][y + stepSize] >> 1) +
                                   nextByte(range));
                    heightMap[x][y + stepSize / 2] =
                            (byte)((heightMap[x][y] >> 1) +
                                   (heightMap[x][y + stepSize] >> 1) +
                                   nextByte(range));
                    heightMap[x + stepSize][y + stepSize / 2] =
                            (byte)((heightMap[x + stepSize][y] >> 1) +
                                   (heightMap[x + stepSize][y + stepSize] >> 1) +
                                   nextByte(range));
                }
            }

            stepSize = stepSize / 2;
            range *= Math.pow(2, -smoothness);
        }
        return heightMap;
    }
}
