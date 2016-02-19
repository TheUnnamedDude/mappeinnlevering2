package no.kevin.innlevering2.net;

import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.ConsoleReader;
import no.kevin.innlevering2.Status;
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
    private final InetSocketAddress address;
    private final ClientPacketHandler packetHandler;
    private PacketDecoder packetDecoder;

    private Client client;
    private SocketChannel channel;
    private boolean running = true;

    public void connect(PacketDecoder packetDecoder) throws IOException {
        channel = SocketChannel.open();
        channel.connect(address);
        client = new Client(channel, channel.getRemoteAddress(), packetHandler);
        client.sendPacket(new HandshakePacket(name));
        client.sendPacket(new NextQuestionPacket());
        this.packetDecoder = packetDecoder;
    }

    @Override
    public void run() {
        while (running()) {
            try {
                packetDecoder.decode(channel, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean running() {
        return running && channel.isConnected();
    }

    @Override
    public void shutdown() {
        running = false;
        try {
            client.close();
            //TODO: Use non-blocking nio, currently a AsynchronousCloseException is thrown because the channel gets closed from another thread
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
