package no.kevin.innlevering2.net.packets;

import lombok.*;
import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NextQuestionPacket implements Packet {
    @Getter(AccessLevel.NONE)
    boolean wantsMore;

    public boolean wantsMore() {
        return wantsMore;
    }

    @Override
    public void read(ByteBuffer in) throws IOException {
        wantsMore = in.get() == 1;
    }

    @Override
    public void write(ByteBuffer out) throws IOException {
        out.put((byte)(wantsMore ? 1 : 0));
    }

    @Override
    public void handle(PacketHandler handler) throws IOException {
        handler.handle(this);
    }
}
