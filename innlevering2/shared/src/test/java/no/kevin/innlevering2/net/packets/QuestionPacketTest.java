package no.kevin.innlevering2.net.packets;

import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionPacketTest {

    @Test
    public void testPacketSerialization() throws Exception {
        Packet packet = new QuestionPacket(123, "fisk", "yes");
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        packet.write(buffer);
        Packet read = new QuestionPacket();
        buffer.flip();
        read.read(buffer);
        assertEquals(packet, read);
    }

    @Test
    public void testHandle() throws Exception {
        PacketHandler packetHandler = mock(PacketHandler.class);
        QuestionPacket packet = new QuestionPacket(123, "fisk", "yes");
        packet.handle(packetHandler);
        verify(packetHandler).handle(eq(packet));
    }
}
