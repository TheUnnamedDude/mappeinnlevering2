package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class QuestionRepository extends BaseSQLRepository {

    public QuestionRepository(Connection con, String dbName) {
        super(con, "SELECT * FROM " + dbName + ";",
                "SELECT * FROM " + dbName + " WHERE ID=?;",
                "SELECT count(*) as size FROM " + dbName + ";");
    }

    @Override
    public Question mapSql(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String question = resultSet.getString("question");
        Pattern allowed_regex = Pattern.compile(resultSet.getString("allowed_regex"));
        Pattern correct_regex = Pattern.compile(resultSet.getString("correct_regex"));
        String display_possible_answer = resultSet.getString("display_possible_answer");
        return new Question(id, question, display_possible_answer, allowed_regex, correct_regex);
    }
}
