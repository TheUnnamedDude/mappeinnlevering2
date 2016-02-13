package no.kevin.innlevering2.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.Status;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class PacketDecoder implements Runnable {
    private final Status status;
    private final Queue<QueueEntry> packetQueue = new LinkedList<>();
    private ByteBuffer buffer = ByteBuffer.allocate(8192); // 8KB should be enough for this
    private ReentrantLock lock = new ReentrantLock();
    private Condition hasItems = lock.newCondition();

    public void decode(SocketChannel channel, Client client) throws IOException {
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
            throw new IOException("Unknown packet questionId " + packetId);
        }

        packet.read(buffer);
        if (packetLength != buffer.position()) {
            throw new IOException("Stream corrupt, expected a packet with size " + packetLength + " got " + buffer.remaining());
        }

        lock.lock();
        try {
            packetQueue.add(new QueueEntry(packet, client));
            hasItems.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (status.running()) {
            lock.lock();
            // TODO: Make this less complicated...
            try {
                hasItems.await();
                while (!packetQueue.isEmpty()) {
                    QueueEntry entry = packetQueue.poll();
                    try {
                        entry.getPacket().handle(entry.getClient().getPacketHandler());
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            entry.getClient().close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
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
