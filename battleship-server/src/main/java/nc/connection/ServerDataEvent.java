package nc.connection;

import java.nio.channels.SocketChannel;

public class ServerDataEvent {
    Server server;
    public SocketChannel socket;
    public byte[] data;

    ServerDataEvent(Server server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }
}
