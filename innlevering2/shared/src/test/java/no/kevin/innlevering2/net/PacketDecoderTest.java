package no.kevin.innlevering2.net;

import no.kevin.innlevering2.Status;
import no.kevin.innlevering2.net.packets.HandshakePacket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PacketDecoderTest {
    ReadableByteChannel channel;
    Client client;
    Status status;
    PacketDecoder packetDecoder;


    @Before
    public void setUp() {
        channel = mock(ReadableByteChannel.class);
        client = mock(Client.class);
        status = mock(Status.class);
        packetDecoder = new PacketDecoder(status);
    }

    @Test
    public void testClosedOnTooSmallPacket() throws Exception {
        packetDecoder.decode(channel, client);
        verify(channel, times(1)).close();
    }

    @Test(expected = IOException.class)
    public void testExceptionOnInvalidPacketId() throws Exception {
        when(channel.read(any(ByteBuffer.class))).then(answer -> {
            ByteBuffer arg = answer.getArgumentAt(0, ByteBuffer.class);
            arg.putInt(20);
            arg.putInt(-1);
            return 20;
        });
        packetDecoder.decode(channel, client);
    }

    @Test(expected = IOException.class)
    public void testExceptionOnWrongPacketSize() throws Exception {
        when(channel.read(any(ByteBuffer.class))).then(answer -> {
            ByteBuffer arg = answer.getArgumentAt(0, ByteBuffer.class);
            arg.putInt(16);
            arg.putInt(0);
            new HandshakePacket("tests").write(arg);
            return 20;
        });
        packetDecoder.decode(channel, client);
    }

    private void sampleHeaderPacket() throws Exception {
        when(channel.read(any(ByteBuffer.class))).then(answer -> {
            ByteBuffer arg = answer.getArgumentAt(0, ByteBuffer.class);
            arg.putInt(16);
            arg.putInt(0);
            new HandshakePacket("test").write(arg);
            return 20;
        });
    }

    @Test(timeout = 1000L)
    public void testRunExecutePacketManager() throws Exception {
        PacketHandler packetHandler = mock(PacketHandler.class);
        when(client.getPacketHandler()).thenReturn(packetHandler);
        sampleHeaderPacket();
        packetDecoder.decode(channel, client);
        when(status.running()).thenReturn(true, false);
        packetDecoder.run();
        verify(packetHandler, times(1)).handle(any(HandshakePacket.class));
    }

    @Test
    public void testClientClosedOnPacketManagerException() throws Exception {
        PacketHandler packetHandler = mock(PacketHandler.class);
        when(client.getPacketHandler()).thenReturn(packetHandler);
        doThrow(mock(IOException.class)).when(packetHandler).handle(any(HandshakePacket.class));
        sampleHeaderPacket();
        packetDecoder.decode(channel, client);
        when(status.running()).thenReturn(true, false);
        packetDecoder.run();
        verify(client, times(1)).close();
    }
}