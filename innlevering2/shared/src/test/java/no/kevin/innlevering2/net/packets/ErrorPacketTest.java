package no.kevin.innlevering2.net.packets;

import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ErrorPacketTest {

    @Test
    public void testPacketSerialization() throws Exception {
        Packet packet = new ErrorPacket("fisk");
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        packet.write(buffer);
        Packet read = new ErrorPacket();
        buffer.flip();
        read.read(buffer);
        assertEquals(packet, read);
    }

    @Test
    public void testHandle() throws Exception {
        PacketHandler packetHandler = mock(PacketHandler.class);
        ErrorPacket packet = new ErrorPacket("fisk");
        packet.handle(packetHandler);
        verify(packetHandler).handle(eq(packet));
    }
}
