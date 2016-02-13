package no.kevin.innlevering2.net.packets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HandshakePacket implements Packet {
    private String name;

    @Override
    public void read(ByteBuffer in) throws IOException {
        name = readString(in);
    }

    @Override
    public void write(ByteBuffer out) throws IOException {
        writeString(name, out);
    }

    @Override
    public void handle(PacketHandler handler) throws IOException {
        handler.handle(this);
    }
}
