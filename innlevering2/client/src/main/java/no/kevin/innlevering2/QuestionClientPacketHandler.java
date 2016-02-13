package no.kevin.innlevering2;

import no.kevin.innlevering2.net.Client;
import no.kevin.innlevering2.net.PacketHandler;
import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;

public class QuestionClientPacketHandler implements PacketHandler {
    private Client client;

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void handle(HandshakePacket packet) throws IOException {

    }

    @Override
    public void handle(QuestionPacket packet) throws IOException {
        System.out.println("Got question!!");
        System.out.println(packet);
        client.sendPacket(new AnswerPacket(packet.getQuestionId(), "yes"));
    }

    @Override
    public void handle(AnswerPacket packet) throws IOException {
    }

    @Override
    public void handle(ErrorPacket packet) throws IOException {
        System.err.println("An error occurred");
        System.err.println(packet);
    }

    @Override
    public void handle(NextQuestionPacket packet) throws IOException {

    }

    @Override
    public void handle(QuestionResultPacket packet) throws IOException {
        System.out.println(packet);
        client.sendPacket(new NextQuestionPacket());
    }
}
