package no.kevin.innlevering2.net.packets;

import lombok.Data;
import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@Data
public class NextQuestionPacket implements Packet {
    @Override
    public void read(ByteBuffer in) throws IOException {

    }

    @Override
    public void write(ByteBuffer out) throws IOException {

    }

    @Override
    public void handle(PacketHandler handler) throws IOException {
        handler.handle(this);
    }
}
