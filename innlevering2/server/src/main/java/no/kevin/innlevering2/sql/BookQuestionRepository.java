package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BookQuestionRepository extends BaseSQLRepository {
    public BookQuestionRepository(Connection con, String dbName) {
        super(con, "SELECT * FROM " + dbName + ";",
                "SELECT * FROM " + dbName + " WHERE ID=?;",
                "SELECT count(*) as size FROM " + dbName + ";");
    }

    @Override
    public Question mapSql(ResultSet resultSet) throws SQLException {
        //`forfatter`, `tittel`, `ISBN`, `sider`, `utgitt`
        int id = resultSet.getInt("id");
        String forfatter = resultSet.getString("forfatter");
        String tittel = resultSet.getString("tittel");
        //String isbn = resultSet.getString("sider");
        //int sider = resultSet.getInt("sider");
        //int utgitt = resultSet.getInt("utgitt");
        String authorPattern = Arrays.stream(forfatter.split(" |,"))
                .filter(s -> !s.trim().isEmpty())
                .map(BookQuestionRepository::quote)
                .collect(Collectors.joining());
        Pattern correctAnswer = Pattern.compile(authorPattern + ".*");
        System.out.println(correctAnswer);
        return new Question(id, "Hvem har skrevet \"" + tittel + "\"?", "", Pattern.compile(".+"), correctAnswer);
    }

    private static String quote(String original) {
        return "(?=.*" + Pattern.quote(original.toLowerCase()) + ")";
    }
}
