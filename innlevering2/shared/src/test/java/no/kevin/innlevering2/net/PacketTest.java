package no.kevin.innlevering2.net;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class PacketTest {
    Packet packet = new Packet() {
        @Override
        public void read(ByteBuffer in) throws IOException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public void write(ByteBuffer out) throws IOException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public void handle(PacketHandler handler) throws IOException {
            throw new UnsupportedOperationException("Not implemented.");
        }
    };
    String string = "abcdefghijklmnopqrstuvwxyzæøå!";
    byte[] bytes = new byte[] {
            0x00, 0x00, 0x00, 0x21,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68,
            0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F, 0x70,
            0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78,
            0x79, 0x7A, (byte)0xC3, (byte)0xA6, (byte)0xC3,
            (byte)0xB8, (byte)0xC3, (byte)0xA5, (byte)0x21
    };

    byte[] empty = new byte[] {
            0x00, 0x00, 0x00, 0x00
    };

    @Test
    public void testReadString() throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        assertEquals(string, packet.readString(buffer));
    }

    @Test
    public void testReadStringReturnNullOn0Size() throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(empty);
        assertNull(packet.readString(buffer));
    }

    @Test
    public void testWriteString() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length); // Add 4 bytes for the size
        packet.writeString(string, buffer);
        assertArrayEquals(bytes, buffer.array());
    }

    @Test
    public void testWriteStringWithNull() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        packet.writeString(null, buffer);
        assertArrayEquals(empty, buffer.array());
    }
}