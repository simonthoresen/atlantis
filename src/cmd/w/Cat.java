package cmd.w;

import kernel.cmd.Command;
import kernel.net.TermCap;
import util.FileTk;
import std.Player;
import std.Wizard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Cat implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("cat", "more", "less");
    }

    @Override
    public boolean run(Player obj, String act, String arg) {
        final Wizard ply = (Wizard)obj;
        if (arg == null) {
            return ply.notifyFail("Usage: cat <file>");
        }
        try {
            Iterator<Path> it = Files.newDirectoryStream(ply.getCurrentPath().resolve(arg)).iterator();
            if (!it.hasNext()) {
                return ply.notifyFail("File '" + arg + "' not found.");
            }
            while (it.hasNext()) {
                Path filePath = it.next();
                BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                if (!attrs.isRegularFile()) {
                    throw new IOException("Not a regular file: " + filePath);
                }
                ply.output(TermCap.BOLD + filePath.toString() + ":" + TermCap.END + "\n");
                ply.output(FileTk.readFile(filePath));
            }
        } catch (IOException e) {
            return ply.notifyFail(e.toString());
        }
        return true;
    }
}
