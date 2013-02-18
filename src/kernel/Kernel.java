package kernel;

import kernel.cmd.CommandManager;
import kernel.net.ConnectionManager;
import kernel.obj.ObjectCompiler;
import kernel.obj.ObjectManager;

import java.io.IOException;
import java.util.logging.Logger;

public class Kernel {

    private static final Logger log = Logger.getLogger(Kernel.class.getName());
    private final TaskManager taskMgr;
    private final ConnectionManager connectionMgr;
    private final CommandManager commandMgr;
    private final ObjectManager objectMgr;
    private final ObjectCompiler compiler;
    private boolean closed = false;

    public Kernel() throws IOException {
        objectMgr = new ObjectManager(this);
        commandMgr = new CommandManager(this);
        connectionMgr = new ConnectionManager(this, 2010);
        taskMgr = new TaskManager(connectionMgr);
        compiler = new ObjectCompiler(objectMgr);
    }

    public void run() throws IOException {
        log.info("Kernel started.");

        while (!closed) {
            taskMgr.checkTasks();
        }

        log.info("Kernel stopped");
    }

    public void close() {
        closed = true;
    }

    public ConnectionManager getConnectionManager() {
        return connectionMgr;
    }

    public ObjectManager getObjectManager() {
        return objectMgr;
    }

    public ObjectCompiler getCompiler() {
        return compiler;
    }

    public TaskManager getTaskManager() {
        return taskMgr;
    }

    public CommandManager getCommandManager() {
        return commandMgr;
    }
}
