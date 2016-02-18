package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class QuestionRepository {
    private final String selectAll;
    private final String selectWhereId;
    private final String selectSize;
    private final Connection con;

    public QuestionRepository(Connection con, String dbName) {
        this.con = con;
        this.selectAll = "SELECT * FROM " + dbName + ";";
        this.selectWhereId = "SELECT * FROM " + dbName + " WHERE ID=?;";
        this.selectSize = "SELECT count(*) as size FROM " + dbName + ";";
    }

    public Question getById(int id) {
        try (PreparedStatement preparedStatement = con.prepareStatement(selectWhereId)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return mapSql(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> result = new ArrayList<>();
        try (ResultSet resultSet = con.createStatement().executeQuery(selectAll)) {
            while (resultSet.next()) {
                result.add(mapSql(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getSize() {
        try (ResultSet resultSet = con.createStatement().executeQuery(selectSize)) {
            if (resultSet.next()) {
                return resultSet.getInt("size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Question mapSql(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String question = resultSet.getString("question");
        Pattern allowed_regex = Pattern.compile(resultSet.getString("allowed_regex"));
        Pattern correct_regex = Pattern.compile(resultSet.getString("correct_regex"));
        String display_possible_answer = resultSet.getString("display_possible_answer");
        return new Question(id, question, display_possible_answer, allowed_regex, correct_regex);
    }
}
