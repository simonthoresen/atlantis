package cmd.p;

import kernel.cmd.Command;
import std.Player;

import java.util.Arrays;
import java.util.List;

public class Map implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("m", "map");
    }

    @Override
    public boolean run(Player ply, String cmd, String arg) {
        ply.output("You check the map.\n");
        return true;
    }
}