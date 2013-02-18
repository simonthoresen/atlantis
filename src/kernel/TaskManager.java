package kernel;

import kernel.net.ConnectionManager;

import java.util.PriorityQueue;
import java.io.IOException;

public class TaskManager {

    private final PriorityQueue<Entry> tasks = new PriorityQueue<Entry>();
    private final ConnectionManager connectionMgr;

    public TaskManager(ConnectionManager connectionMgr) {
        this.connectionMgr = connectionMgr;
    }

    public void checkTasks() throws IOException {
        long timeout = Integer.MAX_VALUE;
        if (!tasks.isEmpty()) {
            timeout = (tasks.peek().time - System.nanoTime()) / (1000 * 1000);
        }
        connectionMgr.select(timeout);

        long now = System.nanoTime();
        while (!tasks.isEmpty() && tasks.peek().time <= now) {
            tasks.poll().task.run();
        }
    }

    public void submit(Runnable task) {
        tasks.add(new Entry(task, 0));
    }

    public void schedule(Runnable task, int delayMillis) {
        tasks.add(new Entry(task, System.nanoTime() + delayMillis * 1000 * 1000));
    }

    private static class Entry implements Comparable<Entry> {

        final Runnable task;
        final Long time;

        public Entry(Runnable task, long time) {
            this.task = task;
            this.time = time;
        }

        @Override
        public int compareTo(Entry rhs) {
            return time.compareTo(rhs.time);
        }
    }
}
