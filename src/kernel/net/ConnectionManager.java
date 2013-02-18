package kernel.net;

import kernel.Kernel;
import std.Login;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public final class ConnectionManager {

    private final Selector selector;
    private final Kernel kernel;

    public ConnectionManager(Kernel kernel, int port) throws IOException {
        this.kernel = kernel;
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void select(long timeout) throws IOException {
        if (timeout > 0) {
            selector.select(timeout);
        } else {
            selector.selectNow();
        }
        for (Iterator<SelectionKey> it = selector.selectedKeys().iterator();
             it.hasNext();) {
            SelectionKey key = it.next();
            it.remove();

            if (!key.isValid()) {
                key.cancel();
                if (key.attachment() instanceof Connection) {
                    ((Connection)key.attachment()).setAvatar(null);
                }
            } else if (key.isAcceptable()) {
                Connection cnt = new Connection(selector, (ServerSocketChannel)key.channel());
                cnt.setAvatar(new Login(kernel));
            } else {
                if (key.isWritable()) {
                    ((Connection)key.attachment()).writeToSocket();
                }
                if (key.isReadable()) {
                    ((Connection)key.attachment()).readFromSocket();
                }
            }
        }
    }
}