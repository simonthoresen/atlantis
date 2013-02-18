package cmd.w;

import kernel.cmd.Command;
import kernel.net.TermCap;
import std.Player;
import std.Wizard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

public class Ls implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("ls");
    }

    @Override
    public boolean run(Player obj, String act, String arg) {
        Wizard ply = (Wizard)obj;
        Path dirPath = ply.getCurrentPath();
        ply.output(TermCap.BOLD + dirPath.toString() + ":" + TermCap.END + "\n");
        try {
            for (Path filePath : Files.newDirectoryStream(dirPath)) {
                String fileName = filePath.getName(filePath.getNameCount() - 1).toString();
                BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                Formatter fmt = new Formatter();
                if (attrs.isDirectory()) {
                    ply.output(fmt.format("%1$8s %2$-28s %3$s\n",
                                          "",
                                          "     *** Directory ***",
                                          fileName).toString());
                } else {
                    ply.output(fmt.format("%1$8s %2$-28tc %3$s\n",
                                          attrs.size(),
                                          attrs.lastModifiedTime().toMillis(),
                                          fileName).toString());
                }
            }
        } catch (IOException e) {
            return ply.notifyFail(e.toString());
        }
        return true;
    }
}
