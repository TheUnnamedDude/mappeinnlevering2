package no.kevin.innlevering2.net.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import no.kevin.innlevering2.net.Packet;
import no.kevin.innlevering2.net.PacketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EndGameStatsPacket implements Packet {
    private int correctAnswers;
    private int totalQuestions;

    @Override
    public void read(ByteBuffer in) throws IOException {
        correctAnswers = in.getInt();
        totalQuestions = in.getInt();
    }

    @Override
    public void write(ByteBuffer out) throws IOException {
        out.putInt(correctAnswers);
        out.putInt(totalQuestions);
    }

    @Override
    public void handle(PacketHandler handler) throws IOException {
        handler.handle(this);
    }
}
