package cmd.p;

import kernel.cmd.Command;
import std.Player;

import java.util.Arrays;
import java.util.List;

public class Score implements Command {
    
    @Override
    public List<String> getActions() {
        return Arrays.asList("sc", "score");
    }

    @Override
    public boolean run(Player ply, String cmd, String arg) {
        ply.output("You check the score.\n");
        return true;
    }
}