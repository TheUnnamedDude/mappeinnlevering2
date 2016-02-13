package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class QuestionRepositoryTest {
    private ResultSet resultSet;
    private QuestionRepository repository;
    private SQLException silentSqlExceptionMock;

    @Before
    public void setUp() throws Exception {
        Connection connection = mock(Connection.class);
        resultSet = mock(ResultSet.class);

        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Statement statement = mock(Statement.class);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.getInt(anyString())).thenReturn(123);
        when(resultSet.getString(anyString())).thenReturn("filler");
        when(resultSet.next()).thenReturn(true);

        silentSqlExceptionMock = mock(SQLException.class);
        repository = new QuestionRepository(connection, "testdb");
    }

    @Test
    public void testMapper() throws Exception {
        when(resultSet.getInt(eq("questionId"))).thenReturn(1);
        when(resultSet.getString(eq("question"))).thenReturn("Is this working?");
        when(resultSet.getString(eq("answer"))).thenReturn("Yes");
        assertEquals(new Question(1, "Is this working?", "Yes"), repository.getById(1));
    }

    @Test
    public void testGetById() throws Exception {
        when(resultSet.getInt(anyInt())).thenReturn(321);
        Question result = repository.getById(321);
        assertEquals(123, result.getId());
    }

    @Test
    public void testReturnsNullOnException() throws Exception {
        when(resultSet.next()).thenThrow(silentSqlExceptionMock);
        assertNull(repository.getById(1));
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        ArrayList<Question> questions = repository.getAllQuestions();
        assertEquals(3, questions.size());
        assertEquals(new Question(123, "filler", "filler"), questions.get(0));
    }

    @Test
    public void testGetAllQuestionsReturnEmptyOnException() throws Exception {
        when(resultSet.next()).thenThrow(silentSqlExceptionMock);
        assertTrue(repository.getAllQuestions().isEmpty());
    }

    @Test
    public void testGetSize() throws Exception {
        when(resultSet.getInt(eq("size"))).thenReturn(420);
        assertEquals(420, repository.getSize());
    }

    @Test
    public void testGetSizeReturnNullOnException() throws Exception {
        when(resultSet.next()).thenThrow(silentSqlExceptionMock);
        assertEquals(0, repository.getSize());
    }
}