package kernel.cmd;

import kernel.obj.ObjectManager;
import std.Player;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class PackageWatcher {

    private final Map<String, Command> fileIndex = new HashMap<>();
    private final ObjectManager objectMgr;
    private final String packageName;
    private final WatchService watchService;
    private CommandAccess access = null;

    PackageWatcher(ObjectManager objectMgr, String packageName) throws IOException {
        this.objectMgr = objectMgr;
        this.packageName = packageName;

        FileSystem fileSystem = FileSystems.getDefault();
        Path packagePath = fileSystem.getPath("out").resolve(packageName.replace('.', '/'));
        for (Path filePath : Files.newDirectoryStream(packagePath)) {
            registerCommand(filePath.getFileName());
        }

        watchService = fileSystem.newWatchService();
        packagePath.register(watchService,
                             StandardWatchEventKinds.ENTRY_CREATE,
                             StandardWatchEventKinds.ENTRY_DELETE);
    }

    void checkEvents() {
        for (WatchKey key; (key = watchService.poll()) != null;) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }
                Path filePath = (Path)event.context();
                System.err.println("event for class file '" + filePath + "'");
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    registerCommand(filePath);
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    unregisterCommand(filePath);
                }
            }
            if (!key.reset()) {
                throw new Error("Failed to reset watch key.");
            }
        }
    }

    private String toClassName(Path classPath) {
        String className = classPath.toString();
        if (className.contains("$") || !className.endsWith(".class")) {
            return null;
        }
        return className.substring(0, className.length() - 6);
    }

    void registerCommand(Path filePath) {
        String className = toClassName(filePath);
        System.err.println("registerCommand(" + className + ")");
        if (className == null) {
            // empty
        } else if (className.equals("CommandAccess")) {
            access = (CommandAccess)objectMgr.create(packageName + "." + className, CommandAccess.class);
        } else {
            Command cmd = (Command)objectMgr.create(packageName + "." + className, Command.class);
            fileIndex.put(className, cmd);
        }
    }

    void unregisterCommand(Path filePath) {
        String className = toClassName(filePath);
        System.err.println("unregisterCommand(" + className + ")");
        if (className == null) {
            // empty
        } else if (className.equals("CommandAccess")) {
            access = null;
        } else {
            fileIndex.remove(className);
        }
    }

    boolean resolveCommand(Player ply, String act, String arg) {
        checkEvents();
        if (access != null && !access.hasAccess(ply)) {
            return false;
        }
        for (Command cmd : fileIndex.values()) {
            if (cmd.getActions().contains(act) && cmd.run(ply, act, arg)) {
                return true;
            }
        }
        return false;
    }

}
