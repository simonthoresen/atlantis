package std;

import java.util.*;

public class Choice<T> implements Iterable<T> {

    private final int numRequired;
    private final List<T> options = new ArrayList<T>();

    public Choice(int numRequired, List<T> options) {
        this.numRequired = numRequired;
        this.options.addAll(options);
    }

    public Choice(int numRequired, T... options) {
        this(numRequired, Arrays.asList(options));
    }

    public int getNumRequired() {
        return numRequired;
    }

    public int getNumOptions() {
        return options.size();
    }

    public T getOption(int i) {
        return options.get(i);
    }

    public boolean contains(T option) {
        return options.contains(option);
    }

    public List<T> getOptions() {
        return new LinkedList<T>(options);
    }

    @Override
    public Iterator<T> iterator() {
        return options.iterator();
    }
}
