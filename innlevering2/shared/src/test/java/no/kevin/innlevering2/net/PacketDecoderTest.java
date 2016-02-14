package no.kevin.innlevering2.net;

import no.kevin.innlevering2.Status;
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

    @Test
    public void testRun() throws Exception {

    }
}