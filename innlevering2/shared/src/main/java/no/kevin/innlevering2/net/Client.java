package no.kevin.innlevering2.net;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class Client {
    private final ByteChannel channel;
    @Getter
    private final PacketHandler packetHandler;
    @Getter
    private final SocketAddress address;
    private ByteBuffer buffer = ByteBuffer.allocate(8192);

    @Getter
    @Setter
    private String name;

    public Client(ByteChannel channel, SocketAddress address, PacketHandler packetHandler) {
        this.channel = channel;
        this.packetHandler = packetHandler;
        this.address = address;
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
        channel.write(buffer);
    }

    public void close() throws IOException {
        channel.close();
    }

    @Override
    public String toString() {
        return "Client(" + name + ") with the address " + getAddress();
    }
}
