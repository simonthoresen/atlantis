package cmd.p;

import kernel.cmd.Command;
import std.Player;

import java.util.Arrays;
import java.util.List;

public class Look implements Command {

    @Override
    public List<String> getActions() {
        return Arrays.asList("l", "look");
    }

    @Override
    public boolean run(Player ply, String cmd, String arg) {
        ply.output("You look around.\n");
        return true;         
    }
}
