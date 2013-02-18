package std;

import kernel.net.Connection;
import kernel.net.ConnectionHandler;
import kernel.Kernel;
import util.FileTk;

import java.io.IOException;

public class Login implements ConnectionHandler {

    private static enum State {
        ENTER_NAME,
        ENTER_PASSWORD,
        CREATE_PASSWORD,
        REPEAT_PASSWORD
    }

    private final Kernel kernel;
    private State state = State.ENTER_NAME;
    private Connection cnt = null;
    private String username = null;
    private Player player = null;

    public Login(Kernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public void handleConnect(Connection cnt) {
        this.cnt = cnt;
        try {
            output(FileTk.readFile("help/welcome"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        output("Name: ");
    }

    @Override
    public void handleDisconnect() {
        this.cnt = null;
    }

    @Override
    public void handleInput(String str) {
        switch (state) {
        case ENTER_NAME:
            str = str.trim();
            if (str.isEmpty()) {
                output("Name: ");
                break;
            }
            str = toUserName(str);
            if (str == null) {
                output("Invaid name. Try again.\n");
                output("Name: ");
                break;
            }
            username = str;
            if (loadPlayer()) {
                output("Password: ");
                state = State.ENTER_PASSWORD;
            } else {
                output("New player. You must have a password.\n");
                output("Password: ");
                state = State.CREATE_PASSWORD;
            }
            break;
        case ENTER_PASSWORD:
            if (str.isEmpty() || !player.isPassword(str)) {
                output("Incorrect password. Try again.\n");
                output("Name: ");
                state = State.ENTER_NAME;
                break;
            }
            gotoAtlantis();
            break;
        case CREATE_PASSWORD:
            if (str.isEmpty()) {
                output("Incorrect password. Try again.\n");
                output("Name: ");
                state = State.ENTER_NAME;
                break;
            }
            player.setPassword(str);
            output("Again: ");
            state = State.REPEAT_PASSWORD;
            break;
        case REPEAT_PASSWORD:
            if (str.isEmpty() || !player.isPassword(str)) {
                output("The passwords did not match. Try again.\n");
                output("Name: ");
                state = State.ENTER_NAME;
                break;
            }
            gotoPlayerCreation();
            break;
        }
    }

    private String toUserName(String str) {
        String ret = str.toLowerCase();
        for (int i = 0, len = ret.length(); i < len; ++i) {
            if ("abcdefghijklmnopqrstuvwxyz".indexOf(ret.charAt(i)) < 0) {
                return null;
            }
        }
        return ret;
    }

    private void output(String str) {
        if (cnt != null) {
            cnt.output(str);
        }
    }

    private boolean loadPlayer() {
        player = new Wizard(kernel, username);
        return player.load();
    }

    private void gotoPlayerCreation() {
        cnt.setAvatar(new PlayerCreator(kernel, player));
    }

    private void gotoAtlantis() {
        cnt.setAvatar(player);
    }
}
