package no.kevin.innlevering2.net;

import no.kevin.innlevering2.net.packets.HandshakePacket;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClientTest {
    private ByteChannel byteChannelMock;
    private PacketHandler packetHandlerMock;
    private SocketAddress addressMock;
    private Client client;
    @Before
    public void setUp() {
        byteChannelMock = mock(ByteChannel.class);
        packetHandlerMock = mock(PacketHandler.class);
        addressMock = mock(SocketAddress.class);
        client = new Client(byteChannelMock, addressMock, packetHandlerMock);
    }

    @Test
    public void testSendPacket() throws Exception {
        when(byteChannelMock.write(any(ByteBuffer.class))).then(answer -> {
            ByteBuffer buffer = answer.getArgumentAt(0, ByteBuffer.class);
            assertTrue(buffer.getInt(0) > 7);
            assertEquals(buffer.getInt(4), 0);
            return 0;
        });
        client.sendPacket(new HandshakePacket("Hello"));
    }

    @Test
    public void testClose() throws Exception {
        client.close();
        verify(byteChannelMock, times(1)).close();
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(client.toString());
    }

    @Test
    public void testSetAndGetName() throws Exception {
        client.setName("test");
        assertEquals("test", client.getName());
    }

    @Test
    public void testGetPacketHandler() throws Exception {
        assertEquals(packetHandlerMock, client.getPacketHandler());
    }
}