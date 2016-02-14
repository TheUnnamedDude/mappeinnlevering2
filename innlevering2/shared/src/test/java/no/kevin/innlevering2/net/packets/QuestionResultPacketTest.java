package no.kevin.innlevering2.net.packets;

import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionResultPacketTest {

    @Test
    public void testPacketSerialization() throws Exception {
        Packet packet = new QuestionResultPacket(123, true);
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        packet.write(buffer);
        Packet read = new QuestionResultPacket();
        buffer.flip();
        read.read(buffer);
        assertEquals(packet, read);
    }

    @Test
    public void testHandle() throws Exception {
        PacketHandler packetHandler = mock(PacketHandler.class);
        QuestionResultPacket packet = new QuestionResultPacket(123, true);
        packet.handle(packetHandler);
        verify(packetHandler).handle(eq(packet));
    }
}
