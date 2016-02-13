package no.kevin.innlevering2;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ConsoleReader {
    BufferedReader in;
    PrintStream out;
    public ConsoleReader(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String readLine(String prompt, String... allowedInput) {
        out.print(prompt);
        try {
            List<String> allowed = Arrays.asList(allowedInput);
            String line;
            while (true) {
                if (allowed.contains(line = in.readLine())) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
