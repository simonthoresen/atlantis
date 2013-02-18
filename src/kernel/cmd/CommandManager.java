package kernel.cmd;

import kernel.Kernel;
import std.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Kernel kernel;
    private final Map<String, PackageWatcher> cmdPackages = new HashMap<String, PackageWatcher>();

    public CommandManager(Kernel kernel) throws IOException {
        this.kernel = kernel;
    }

    public boolean resolveCommand(Player ply, String act, String arg) {
        for (String packageName : ply.getCommandPackages()) {
            PackageWatcher watcher = cmdPackages.get(packageName);
            if (watcher == null) {
                try {
                    watcher = new PackageWatcher(kernel.getObjectManager(), packageName);
                    cmdPackages.put(packageName, watcher);
                } catch (IOException e) {
                    ply.output("Illegal command package '" + packageName + "'.");
                    continue;
                }
            }
            if (watcher.resolveCommand(ply, act, arg)) {
                return true;
            }
        }
        return false;
    }
}
