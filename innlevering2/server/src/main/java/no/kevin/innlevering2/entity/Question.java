package no.kevin.innlevering2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Question {
    private int id;
    private String question;
    private String answer;
}