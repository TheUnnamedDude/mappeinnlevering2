package no.kevin.innlevering2.net;

import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.entity.Question;
import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ServerPacketHandler implements PacketHandler {
    private final ArrayList<Question> questions;
    private final Logger logger = Logger.getLogger("server");
    private final static Random RANDOM = new Random();

    private Client client;
    private int lastQuestionId;
    private boolean hasHandshaked = false;
    private int correctAnswers;
    private long lastQuestion;
    private int timeElapsed;

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
        boolean correct = questions.get(lastQuestionId).getAnswerRegex().matcher(packet.getAnswer().toLowerCase()).matches();
        if (correct) {
            correctAnswers ++;
        }
        client.sendPacket(new QuestionResultPacket(lastQuestionId, correct));
        timeElapsed = (int) (System.currentTimeMillis() - lastQuestion);
    }

    @Override
    public void handle(ErrorPacket packet) throws IOException {
        logger.info(String.format("Client <%s> disconnected: %s", client.getAddress(), packet.getMessage()));
        client.close();
    }

    @Override
    public void handle(NextQuestionPacket packet) throws IOException {
        if (!hasHandshaked) {
            err("No handshake received");
            return;
        }
        if (packet.wantsMore()) {
            lastQuestionId = RANDOM.nextInt(questions.size());
            Question question = questions.get(lastQuestionId);
            client.sendPacket(new QuestionPacket(lastQuestionId, question.getQuestion(), question.getPossibleAnswerRegex().pattern()));
            lastQuestion = System.currentTimeMillis();
        } else {
            client.sendPacket(new EndGameStatsPacket(correctAnswers, lastQuestionId, timeElapsed));
            client.close();
        }
    }

    @Override
    public void handle(QuestionResultPacket packet) throws IOException {
        err("Cannot send a question result to the server");
    }

    @Override
    public void handle(EndGameStatsPacket packet) throws IOException {
        err("Cannot send a end game stats packet to the server");
    }

    private void err(String message) throws IOException {
        client.sendPacket(new ErrorPacket(message));
        client.close();
    }

}
