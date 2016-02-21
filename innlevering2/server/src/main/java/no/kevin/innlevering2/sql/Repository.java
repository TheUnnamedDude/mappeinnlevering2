package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository {
    Question getById(int id);
    ArrayList<Question> getAllQuestions();
    int getSize();
    Question mapSql(ResultSet resultSet) throws SQLException;
}
