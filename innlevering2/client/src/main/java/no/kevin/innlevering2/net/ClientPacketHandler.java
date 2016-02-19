package no.kevin.innlevering2.net;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import no.kevin.innlevering2.ConsoleReader;
import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;

@RequiredArgsConstructor
public class ClientPacketHandler implements PacketHandler {
    private final ConsoleReader consoleReader;

    @Setter
    private Client client;

    @Override
    public void handle(HandshakePacket packet) throws IOException {
        throw new UnsupportedOperationException("Not implemented client-side");
    }

    @Override
    public void handle(QuestionPacket packet) throws IOException {
        System.out.println("Got question!!");
        String answer = consoleReader.readLine(packet.getQuestion(), packet.getAllowedAnswersRegex());
        client.sendPacket(new AnswerPacket(packet.getQuestionId(), answer));
    }

    @Override
    public void handle(AnswerPacket packet) throws IOException {
        throw new UnsupportedOperationException("Not implemented client-side");
    }

    @Override
    public void handle(ErrorPacket packet) throws IOException {
        System.err.println("An error occurred");
        System.err.println(packet);
        client.close();
    }

    @Override
    public void handle(NextQuestionPacket packet) throws IOException {
        throw new UnsupportedOperationException("Not implemented client-side");
    }

    @Override
    public void handle(QuestionResultPacket packet) throws IOException {
        System.out.println(packet);
        client.sendPacket(new NextQuestionPacket());
    }

    @Override
    public void handle(EndGameStatsPacket packet) throws IOException {
        System.out.println(packet);
        client.close();
    }
}
