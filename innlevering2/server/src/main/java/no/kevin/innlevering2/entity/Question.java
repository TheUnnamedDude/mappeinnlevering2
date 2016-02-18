package no.kevin.innlevering2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Question {
    private int id;
    private String question;
    private String possibleAnswerDisplayText;
    private Pattern possibleAnswerRegex;
    private Pattern answerRegex;
}