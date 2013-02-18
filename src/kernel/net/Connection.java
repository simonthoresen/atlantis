package kernel.net;

import kernel.net.TermCap;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Connection {

    private static final Logger log = Logger.getLogger(Connection.class.getName());
    private final Queue<String> outputQueue = new LinkedList<String>();
    private final StringBuilder inputBuilder = new StringBuilder();
    private final SelectionKey selectionKey;
    private ConnectionHandler avatar = null;

    public Connection(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel clientSocket = serverSocket.accept();
        clientSocket.configureBlocking(false);
        selectionKey = clientSocket.register(selector, SelectionKey.OP_READ);
        selectionKey.attach(this);
    }

    public void setAvatar(ConnectionHandler avatar) {
        if (this.avatar != null) {
            this.avatar.handleDisconnect();
        }
        this.avatar = avatar;
        if (avatar != null) {
            avatar.handleConnect(this);
        }
    }

    public void close() throws IOException {
        selectionKey.channel().close();
        setAvatar(null);
    }

    public void output(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        outputQueue.add(TermCap.encode(str));
        if (outputQueue.size() == 1) {
            try {
                selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                log.finest("Added OP_WRITE to interest set.");
            } catch (CancelledKeyException e) {
                log.log(Level.WARNING, null, e);
                setAvatar(null);
            }
        }
    }

    public void writeToSocket() throws IOException {
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        for (String str; (str = outputQueue.peek()) != null;) {
            if (channel.write(ByteBuffer.wrap(str.getBytes())) > 0) {
                outputQueue.poll();
            } else {
                break;
            }
        }
        if (outputQueue.isEmpty()) {
            selectionKey.interestOps(SelectionKey.OP_READ);
            log.finest("Removed OP_WRITE from interest set.");
        }
    }

    public void readFromSocket() throws IOException {
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int numBytes = channel.read(buf);
        if (numBytes <= 0) {
            close();
            return;
        }
        buf.flip();
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        String str = decoder.decode(buf).toString();
        for (int i = 0, len = str.length(); i < len && avatar != null; ++i) {
            char c = str.charAt(i);
            if (c == '\n') {
                invokeAvatar(inputBuilder.toString());
                inputBuilder.setLength(0);
            } else if (c >= 32 && c <= 127){
                inputBuilder.append(c);
            }
        }
    }

    private void invokeAvatar(String input) {
        try {
            avatar.handleInput(input);
        } catch (Throwable t) {
            StringWriter out = new StringWriter();
            t.printStackTrace(new PrintWriter(out));
            output(out.toString() + "\n");
        }
    }
}