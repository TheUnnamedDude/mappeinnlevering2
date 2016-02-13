package no.kevin.innlevering2.net;

import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.entity.Question;
import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ServerPacketHandler implements PacketHandler {
    private final ArrayList<Question> questions;
    private final Logger logger = Logger.getLogger("server");

    private Client client;
    private int lastQuestionId;
    private boolean hasHandshaked = false;

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void handle(HandshakePacket packet) throws IOException {
        if (hasHandshaked) {
            err("Handshake packet already received");
            return;
        }
        client.setName(packet.getName());
        logger.info("Client connected with name " + client.getName());
        hasHandshaked = true;
    }

    @Override
    public void handle(QuestionPacket packet) throws IOException {
        err("Cannot send a question to the server.");
    }

    @Override
    public void handle(AnswerPacket packet) throws IOException {
        if (!hasHandshaked) {
            err("No handshake received");
            return;
        }
        if (packet.getQuestionId() != lastQuestionId || packet.getQuestionId() >= questions.size()) {
            err("Question questionId mismatch");
            return;
        }
        boolean correct = packet.getAnswer().equalsIgnoreCase(questions.get(lastQuestionId).getAnswer());
        client.sendPacket(new QuestionResultPacket(lastQuestionId, correct));
        lastQuestionId ++;
    }

    @Override
    public void handle(ErrorPacket packet) throws IOException {
        logger.info(String.format("Client <%s> disconnected: %s", client.getClient().getRemoteAddress(), packet.getMessage()));
        client.close();
    }

    @Override
    public void handle(NextQuestionPacket packet) throws IOException {
        if (!hasHandshaked) {
            err("No handshake received");
            return;
        }
        if (lastQuestionId <= questions.size()) {
            Question question = questions.get(lastQuestionId);
            client.sendPacket(new QuestionPacket(lastQuestionId, question.getQuestion()));
        } else {
            err("No more questions!");
        }
    }

    @Override
    public void handle(QuestionResultPacket packet) throws IOException {
        err("Cannot send a question result to the server");
    }

    private void err(String message) throws IOException {
        client.sendPacket(new ErrorPacket(message));
        client.close();
    }

}
