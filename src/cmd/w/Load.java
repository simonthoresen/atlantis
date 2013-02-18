package cmd.w;

import kernel.Kernel;
import kernel.cmd.Command;
import kernel.obj.CreateListener;
import std.Player;
import std.Wizard;
import util.CompilationFailedException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Load implements Command, CreateListener<Command> {

    private Kernel kernel = null;

    @Override
    public void onCreate(Kernel kernel, Command self) {
        this.kernel = kernel;
    }

    @Override
    public List<String> getActions() {
        return Arrays.asList("load");
    }

    @Override
    public boolean run(Player obj, String act, String arg) {
        Wizard ply = (Wizard)obj;
        if (arg == null) {
            return ply.notifyFail("Usage: load <file>");
        }
        try {
            kernel.getCompiler().compile(ply.getCurrentPath().resolve(arg));
        } catch (FileNotFoundException e) {
            return ply.notifyFail("File '" + arg + "' not found.");
        } catch (CompilationFailedException e) {
            return ply.notifyFail(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ply.notifyFail(e.getMessage());
        }
        ply.output("File '" + arg + "' compiled.\n");
        return true;
    }
}
