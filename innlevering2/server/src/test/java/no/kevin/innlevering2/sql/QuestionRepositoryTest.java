package no.kevin.innlevering2.sql;

import no.kevin.innlevering2.entity.Question;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
        when(resultSet.getInt(eq("id"))).thenReturn(1);
        when(resultSet.getString(eq("display_possible_answer"))).thenReturn("Yes/No");
        when(resultSet.getString(eq("question"))).thenReturn("Is this working?");
        when(resultSet.getString(eq("correct_regex"))).thenReturn("yes");
        when(resultSet.getString(eq("allowed_regex"))).thenReturn("yes|no");
        Question question = repository.getById(1);
        assertEquals(1, question.getId());
        assertEquals("Is this working?", question.getQuestion());
        assertEquals("Yes/No", question.getPossibleAnswerDisplayText());
        assertEquals("yes|no", question.getPossibleAnswerRegex().pattern());
        assertEquals("yes", question.getAnswerRegex().pattern());
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
        Question question = questions.get(0);
        assertEquals(123, question.getId());
        assertEquals("filler", question.getQuestion());
        assertEquals("filler", question.getPossibleAnswerDisplayText());
        assertEquals("filler", question.getPossibleAnswerRegex().pattern());
        assertEquals("filler", question.getAnswerRegex().pattern());
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