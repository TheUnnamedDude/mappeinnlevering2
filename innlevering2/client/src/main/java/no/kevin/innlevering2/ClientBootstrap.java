package no.kevin.innlevering2;

import java.io.IOException;
import java.net.InetAddress;

public class ClientBootstrap {

    public static void main(String[] args) {
        try {
            QuestionClient client = new QuestionClient("tud", InetAddress.getByName("127.0.0.1"), 9876);
            client.connect();
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
