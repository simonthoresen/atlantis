package cmd.w;

import std.Player;

public class CommandAccess implements kernel.cmd.CommandAccess {

    @Override
    public boolean hasAccess(Player ply) {
        return ply.getName().equals("foo");
    }
}
