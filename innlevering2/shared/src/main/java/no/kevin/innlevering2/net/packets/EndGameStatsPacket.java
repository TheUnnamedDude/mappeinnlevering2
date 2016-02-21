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
public class EndGameStatsPacket implements Packet {
    private int correctAnswers;
    private int totalQuestions;
    private int timeTaken;

    @Override
    public void read(ByteBuffer in) throws IOException {
        correctAnswers = in.getInt();
        totalQuestions = in.getInt();
        timeTaken = in.getInt();
    }

    @Override
    public void write(ByteBuffer out) throws IOException {
        out.putInt(correctAnswers);
        out.putInt(totalQuestions);
        out.putInt(timeTaken);
    }

    @Override
    public void handle(PacketHandler handler) throws IOException {
        handler.handle(this);
    }
}
