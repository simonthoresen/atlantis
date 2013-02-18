package util.test;

import junit.framework.TestCase;
import util.MapCreator;

public class HeightMapTestCase extends TestCase {

    public void testGenerator() {
        byte[][] heightMap = MapCreator.newHeightMap(3, 1);
        for (int x = 0; x < heightMap.length; ++x) {
            for (int y = 0; y < heightMap.length; ++y) {
                System.out.format("%1$3d ", heightMap[x][y]);
            }
            System.out.println("");
        }
    }
}
