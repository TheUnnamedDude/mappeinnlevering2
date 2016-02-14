package no.kevin.innlevering2.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.Status;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RequiredArgsConstructor
public class PacketDecoder implements Runnable {
    private final Status status;
    private final BlockingQueue<QueueEntry> packetQueue = new LinkedBlockingQueue<>();
    private ByteBuffer buffer = ByteBuffer.allocate(8192); // 8KB should be enough for this

    public void decode(ReadableByteChannel channel, Client client) throws IOException {
        buffer.clear();

        int length = channel.read(buffer);

        if (length == -1 || length < 8) {
            System.out.println("Failed to read from stream, disconnecting");
            channel.close();
            return;
        }

        buffer.flip();
        int packetLength = buffer.getInt();
        int packetId = buffer.getInt();

        Packet packet = PacketMapping.getPacketById(packetId);
        if (packet == null) {
            throw new IOException("Unknown packet id " + packetId);
        }

        packet.read(buffer);
        if (packetLength != buffer.position()) {
            throw new IOException("Stream corrupt, expected a packet with size " + packetLength + " got " + buffer.remaining());
        }
        packetQueue.add(new QueueEntry(packet, client));
    }

    @Override
    public void run() {
        while (status.running()) {
            // TODO: Make this less complicated...
            try {
                QueueEntry entry = packetQueue.take();
                if (entry == null)
                    continue;

                try {
                    entry.getPacket().handle(entry.getClient().getPacketHandler());
                } catch (Exception e) {
                    e.printStackTrace();
                    entry.getClient().close();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class QueueEntry {
        private final Packet packet;
        private final Client client;
    }
}
