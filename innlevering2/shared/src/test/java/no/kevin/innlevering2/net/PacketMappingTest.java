package no.kevin.innlevering2.net;

import org.junit.Test;

import static org.junit.Assert.*;

public class PacketMappingTest {
    @Test
    public void testIdToPacketMapping() {
        for (int i = 0; i < PacketMapping.getMaxPacketId(); i++) {
            Packet packet = PacketMapping.getPacketById(i);
            assertNotNull(packet);
            assertEquals(PacketMapping.getIdByPacket(packet.getClass()), i);
        }
    }

    @Test
    public void testReturnsNullWhenOverOfBounds() {
        assertNull(PacketMapping.getPacketById(PacketMapping.getMaxPacketId() + 1));
    }

    @Test
    public void testReturnsNullWhenUnderBounds() {
        assertNull(PacketMapping.getPacketById(-1));
    }
}
