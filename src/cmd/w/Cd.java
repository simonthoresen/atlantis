package cmd.w;

import kernel.cmd.Command;
import kernel.net.TermCap;
import std.Player;
import std.Wizard;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class Cd implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("cd");
    }

    @Override
    public boolean run(Player obj, String act, String arg) {
        Wizard ply = (Wizard)obj;
        if (arg == null) {
            arg = "/src/home/" + ply.getName();
        }
        Path path = ply.getCurrentPath();
        if (arg.charAt(0) == '/') {
            path = FileSystems.getDefault().getPath(".");
            arg = arg.length() > 1 ? arg.substring(1) : null;
        }
        if (arg != null) {
            path = path.resolve(arg);
        }
        if (!Files.exists(path)) {
            return ply.notifyFail("Directory '" + path + "' does not exist.");
        }
        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            return ply.notifyFail(e.toString());
        }
        if (!attrs.isDirectory()) {
            return ply.notifyFail("File '" + path + "' is not a directory.");
        }
        ply.setCurrentPath(path);
        ply.output(TermCap.BOLD + ply.getCurrentPath().toString() + TermCap.END + "\n");
        return true;
    }
}
