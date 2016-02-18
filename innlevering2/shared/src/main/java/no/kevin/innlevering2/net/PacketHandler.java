package no.kevin.innlevering2.net;

import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;

public interface PacketHandler {
    void setClient(Client client);

    void handle(HandshakePacket packet) throws IOException;
    void handle(QuestionPacket packet) throws IOException;
    void handle(AnswerPacket packet) throws IOException;
    void handle(ErrorPacket packet) throws IOException;
    void handle(NextQuestionPacket packet) throws IOException;
    void handle(QuestionResultPacket packet) throws IOException;
    void handle(EndGameStatsPacket packet) throws IOException;
}
