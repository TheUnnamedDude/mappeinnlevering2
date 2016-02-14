package no.kevin.innlevering2.net;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
}