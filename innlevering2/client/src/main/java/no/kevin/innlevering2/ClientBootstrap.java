package no.kevin.innlevering2;

import no.kevin.innlevering2.net.ClientPacketHandler;
import no.kevin.innlevering2.net.PacketDecoder;
import no.kevin.innlevering2.net.QuestionClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

public class ClientBootstrap {

    public static void main(String[] args) {
        try {
            ConsoleReader consoleReader = new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)), System.out);
            ClientPacketHandler packetHandler = new ClientPacketHandler(consoleReader);
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9876);
            QuestionClient client = new QuestionClient("tud", address, packetHandler);
            PacketDecoder packetDecoder = new PacketDecoder(client);
            client.connect(packetDecoder);

            new Thread(packetDecoder).start();
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
