package no.kevin.innlevering2;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ConsoleReader {
    BufferedReader in;
    PrintStream out;
    public ConsoleReader(BufferedReader in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String readLine(String prompt, String allowedRegex) {
        try {
            Pattern allowed = Pattern.compile(allowedRegex);
            String line;
            while (true) {
                out.print(prompt + " ");
                if (allowed.matcher(line = in.readLine()).matches()) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
