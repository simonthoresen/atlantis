package std;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Dice {

    public static final Dice NO_DICE = new Dice();
    private final List<Die> dice = new LinkedList<Die>();

    public Dice(List<Die> dice) {
        this.dice.addAll(dice);
    }

    public Dice(Die... dice) {
        this(Arrays.asList(dice));
    }

    public int roll() {
        int ret = 0;
        for (Die die : dice) {
            ret += die.roll();
        }
        return ret;
    }
}
