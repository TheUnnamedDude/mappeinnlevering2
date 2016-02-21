package no.kevin.innlevering2.net;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import no.kevin.innlevering2.ConsoleReader;
import no.kevin.innlevering2.net.packets.*;

import java.io.IOException;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ClientPacketHandler implements PacketHandler {
    private final ConsoleReader consoleReader;
    private static final Pattern YES_PATTERN = Pattern.compile("y(es)?|ja?");
    private static final String CONTINUE_PROMPT = "Vil du fortsette? j(a)/n(ei)";
    private static final String ALTERNATIVES_PATTERN = "ja?|y(es)?|no?|nei";

    @Setter
    private Client client;

    @Override
    public void handle(HandshakePacket packet) throws IOException {
        throw new UnsupportedOperationException("Not implemented client-side");
    }

    @Override
    public void handle(QuestionPacket packet) throws IOException {
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
        System.out.println(packet.isCorrect() ? "Riktig!" : "Feil.");
        boolean sendNext = YES_PATTERN.matcher(consoleReader.readLine(CONTINUE_PROMPT, ALTERNATIVES_PATTERN)).matches();
        client.sendPacket(new NextQuestionPacket(sendNext));
    }

    @Override
    public void handle(EndGameStatsPacket packet) throws IOException {
        System.out.println("Resultater:");
        System.out.println(String.format("Riktige svar: %d/%d", packet.getCorrectAnswers(), packet.getTotalQuestions()));
        System.out.println(String.format("Tid brukt: %.2f sekunder", (float)packet.getTimeElapsed() / 1000.0));
        client.close();
    }
}
