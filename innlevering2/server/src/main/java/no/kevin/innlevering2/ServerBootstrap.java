package no.kevin.innlevering2;

import no.kevin.innlevering2.entity.Question;
import no.kevin.innlevering2.sql.QuestionRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerBootstrap {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/unittest", "unittest", "unittestpassword")) {
            QuestionRepository questionRepository = new QuestionRepository(connection, "questions");
            ArrayList<Question> questions = questionRepository.getAllQuestions();
            ConnectionHandler connectionHandler = new ConnectionHandler(null, 9876, questions);
            connectionHandler.initSelector();
            connectionHandler.run(); // Run it on the main thread for now so it wont exit and eat unicorns
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
