package kernel.net;

public interface ConnectionHandler {

    public void handleConnect(Connection cnt);

    public void handleDisconnect();

    public void handleInput(String str);
}
