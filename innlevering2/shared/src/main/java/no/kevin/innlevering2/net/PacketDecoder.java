package no.kevin.innlevering2.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.Status;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class PacketDecoder implements Runnable {
    private final Status status;
    private final Queue<QueueEntry> packetQueue = new LinkedList<>();

    public void decode(ByteBuffer buffer, PacketHandler packetHandler) throws IOException {
        synchronized (packetQueue) {
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

            packetQueue.add(new QueueEntry(packet, packetHandler));
            packetQueue.notify();
        }
    }

    @Override
    public void run() {
        while (status.running()) {
            synchronized (packetQueue) {
                while (packetQueue.isEmpty()) {
                    try {
                        packetQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                QueueEntry entry = packetQueue.poll();
                try {
                    entry.getPacket().handle(entry.getPacketHandler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class QueueEntry {
        private final Packet packet;
        private final PacketHandler packetHandler;
    }
}
