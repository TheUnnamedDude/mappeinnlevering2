package no.kevin.innlevering2.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface Packet {
    void read(ByteBuffer in) throws IOException;
    void write(ByteBuffer out) throws IOException;
    void handle(PacketHandler handler) throws IOException;

    default String readString(ByteBuffer in) {
        int length = in.getInt();
        if (length < 1)
            return null;
        byte[] buffer = new byte[length];
        in.get(buffer);
        return new String(buffer, Charset.forName("UTF-8"));
    }

    default void writeString(String string, ByteBuffer out) {
        if (string == null) {
            out.putInt(0);
            return;
        }
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        out.putInt(bytes.length);
        out.put(bytes);
    }
}
