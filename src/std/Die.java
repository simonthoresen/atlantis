package std;

import java.util.Random;

public class Die {

    public static final Die D5 = new Die(5);
    public static final Die D10 = new Die(10);
    public static final Die D100 = new Die(100);

    private final Random rnd = new Random(System.nanoTime());
    private final int numFaces;

    public Die(int numFaces) {
        this.numFaces = numFaces;
    }

    public int roll() {
        return rnd.nextInt(numFaces) + 1; 
    }
}
