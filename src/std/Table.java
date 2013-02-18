package std;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Table<T> implements Iterable<Table.Row<T>> {

    public static final Table EMPTY_TABLE = new Table();
    private List<Row<T>> rows = new LinkedList<Row<T>>();

    public Table<T> addRow(int lim, T val) {
        rows.add(new Row<T>(lim, val));
        return this;
    }

    public T getValue(int roll) {
        for (Row<T> row : rows) {
            if (roll <= row.lim) {
                return row.val;
            }
        }
        return rows.get(rows.size() - 1).val;
    }

    public T roll(Dice dice) {
        return getValue(dice.roll());
    }

    public T roll(Die die) {
        return getValue(die.roll());
    }

    @SuppressWarnings({ "unchecked" })
    public static <T> Table<T> emptyTable() {
        return (Table<T>)EMPTY_TABLE;
    }

    @Override
    public Iterator<Row<T>> iterator() {
        return rows.iterator();
    }

    public static class Row<T> {

        private final int lim;
        private final T val;

        public Row(int lim, T val) {
            this.lim = lim;
            this.val = val;
        }

        public int getLimit() {
            return lim;
        }

        public T getValue() {
            return val;
        }
    }
}
