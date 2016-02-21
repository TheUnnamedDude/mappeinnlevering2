package no.kevin.innlevering2.sql;

import lombok.RequiredArgsConstructor;
import no.kevin.innlevering2.entity.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RequiredArgsConstructor
public abstract class BaseSQLRepository implements Repository {
    private final Connection con;
    private final String selectAll;
    private final String selectWhereId;
    private final String selectSize;

    @Override
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

    @Override
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

    @Override
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
}
