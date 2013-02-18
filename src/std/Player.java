package std;

import kernel.net.Connection;
import kernel.net.ConnectionHandler;
import kernel.Kernel;
import util.MapCreator;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player extends Living implements ConnectionHandler {

    private final List<String> cmdPackages = new LinkedList<String>();
    private final Kernel kernel;
    private final Path savePath;
    private Connection cnt = null;
    private String password = null;
    private String failText = null;

    public Player(Kernel kernel, String name) {
        super(name);
        this.kernel = kernel;

        savePath = FileSystems.getDefault().getPath("data/players");
        addCommandPackage("cmd.p");
    }

    @Override
    public void handleConnect(Connection cnt) {
        this.cnt = cnt;
        StringBuilder str = new StringBuilder();
        str.append("\377\375\42 ");
        str.append("\377\373\1 ");

        for (int i = 0; i < 256; ++i) {
            str.append("\033[48;5;").append(i).append("m ");
        }
        str.append("\033[m \n\n");

        for (int i = 0; i < 6; ++i) {
            appendBgColor(0, 0, i, str).append(" ");
            appendBgColor(0, i, 0, str).append(" ");
            appendBgColor(0, i, i, str).append(" ");
            appendBgColor(i, 0, 0, str).append(" ");
            appendBgColor(i, 0, i, str).append(" ");
            appendBgColor(i, i, 0, str).append(" ");
            appendBgColor(i, i, i, str).append(" ");
            str.append("\033[m \n");
        }
        str.append("\n");

        byte[][] heightMap = MapCreator.newHeightMap(7, 0.5f);
        for (int x = 0; x < heightMap.length; ++x) {
            for (int y = 0; y < heightMap.length; ++y) {
                appendBgColor(0, 0, 1 + (heightMap[x][y] - Byte.MIN_VALUE) / 51, str).append(" "); 
            }
            str.append("\033[m \n");
        }

        cnt.output(str.toString());
    }

    public StringBuilder appendBgColor(int red, int green, int blue, StringBuilder out) {
        out.append("\033[48;5;").append(getColorIndex(red, green, blue)).append("m");
        return out;
    }

    public StringBuilder appendFgColor(int red, int green, int blue, StringBuilder out) {
        out.append("\033[38;5;").append(getColorIndex(red, green, blue)).append("m");
        return out;
    }

    public int getColorIndex(int red, int green, int blue) {
        return 16 + Math.min(red, 5) * 36 + Math.min(5, green) * 6 + Math.min(5, blue);
    }

    @Override
    public void handleDisconnect() {
        this.cnt = null;
    }

    @Override
    public void handleInput(String str) {
        str = str.trim();
        if (!str.isEmpty()) {
            String argv[] = str.split(" ", 2);
            failText = "Command '" + argv[0] + "' not found.";
            if (!kernel.getCommandManager().resolveCommand(this, argv[0], argv.length < 2 ? null : argv[1])) {
                output(failText + "\n");
            }
        }
        output("> ");
    }

    public boolean notifyFail(String failText) {
        this.failText = failText;
        return false;
    }

    public void output(String str) {
        if (cnt != null) {
            this.cnt.output(str);
        }
    }

    public boolean save() {
        try {
            Path path = savePath.resolve(getName());
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    Files.newOutputStream(path,
                                          StandardOpenOption.CREATE,
                                          StandardOpenOption.WRITE,
                                          StandardOpenOption.TRUNCATE_EXISTING)));
            out.println(password);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load() {
        try {
            Path path = savePath.resolve(getName());
            if (!Files.exists(path)) {
                return false;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(path)));
            password = in.readLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public void addCommandPackage(String packageName) {
        cmdPackages.add(packageName);
    }

    public void removeCommandPackage(String packageName) {
        cmdPackages.remove(packageName);
    }

    public List<String> getCommandPackages() {
        return Collections.unmodifiableList(cmdPackages);
    }
}
