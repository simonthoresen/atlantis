package kernel.cmd;

import std.Player;

import java.util.List;

public interface Command {

    public List<String> getActions();

    public boolean run(Player ply, String act, String arg);
}
