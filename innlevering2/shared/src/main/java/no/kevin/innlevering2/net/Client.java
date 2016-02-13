package no.kevin.innlevering2.net;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Getter
public class Client {
    private final SocketChannel client;
    private final PacketHandler packetHandler;
    private ByteBuffer buffer = ByteBuffer.allocate(8192);

    @Setter
    private String name;

    public Client(SocketChannel client, PacketHandler packetHandler) {
        this.client = client;
        this.packetHandler = packetHandler;
        packetHandler.setClient(this);
    }

    public void sendPacket(Packet packet) throws IOException {
        buffer.clear();

        // Write the content so we can know the packet size
        buffer.position(8);
        packet.write(buffer);
        int length = buffer.position();
        // Set the header
        buffer.position(0);
        buffer.putInt(length);
        buffer.putInt(PacketMapping.getIdByPacket(packet.getClass()));

        buffer.position(0);
        client.write(buffer);
    }

    public void close() throws IOException {
        client.close();
    }
}
