package no.kevin.innlevering2;

import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.net.Client;
import no.kevin.innlevering2.net.PacketDecoder;
import no.kevin.innlevering2.net.PacketHandler;
import no.kevin.innlevering2.net.packets.HandshakePacket;
import no.kevin.innlevering2.net.packets.NextQuestionPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@RequiredArgsConstructor
public class QuestionClient implements Runnable, Status {
    private final String name;
    private final InetAddress host;
    private final int port;

    private ByteBuffer buffer = ByteBuffer.allocate(8192);
    private Client client;
    private SocketChannel channel;
    private PacketDecoder packetDecoder;
    private QuestionClientPacketHandler packetHandler = new QuestionClientPacketHandler();

    public void connect() throws IOException {
        channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(host, port));
        client = new Client(channel, packetHandler);
        client.sendPacket(new HandshakePacket(name));
        client.sendPacket(new NextQuestionPacket());
        packetDecoder = new PacketDecoder(this);
        new Thread(packetDecoder).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.clear();
                channel.read(buffer);
                packetDecoder.decode(buffer, packetHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean running() {
        return true;
    }

    @Override
    public void shutdown() {

    }
}
