package std;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Container extends Item implements Iterable<Item> {

    private final List<Item> items = new LinkedList<Item>();

    public int addItem(Item item) {
        items.add(item);
        return items.size() - 1;
    }

    public int getNumItems() {
        return items.size();
    }

    public Item getItem(int i) {
        return items.get(i);
    }

    public Item removeItem(int i) {
        return items.remove(i);
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
