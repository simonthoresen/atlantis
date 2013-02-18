package kernel.cmd;

import std.Player;

public interface CommandAccess {

    public boolean hasAccess(Player ply);
}
