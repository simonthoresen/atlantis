package cmd.p;

import kernel.cmd.Command;
import util.FileTk;
import std.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Help implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("help");
    }

    @Override
    public boolean run(Player ply, String cmd, String arg) {
        try {
            ply.output(FileTk.readFile("help/" + arg) + "\n");
        } catch (IOException e) {
            return ply.notifyFail("No help available for '" + arg + "'.");
        }
        return true;
    }
}