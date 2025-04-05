package dao;

import domain.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class QuestionDbDAO implements RepositoryDAO<Question> {

    // SQL-запросы к таблице PC базы данных
    private static final String SELECT_ALL_QUESTIONS = "SELECT id, voteId, content, dateVote FROM Question";
    private static final String SELECT_QUESTION_BY_ID = "SELECT id, voteId, content, dateVote FROM Question WHERE id = ?";
    private static final String INSERT_QUESTION = "INSERT INTO Question (voteId, content, dateVote) VALUES (?, ?, ?,)";
    private static final String UPDATE_QUESTION = "UPDATE Question SET voteId = ?, content = ?, dateVote = ? WHERE id = ?";
    private static final String DELETE_QUESTION = "DELETE FROM Question WHERE id = ?";
    private static final String SELECT_QUESTIONS_BY_VOTE_ID = "SELECT id, voteId, content, dateVote FROM Question WHERE voteId = ?";


    private final ConnectionBuilder builder = new DbConnectionBuilder();

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    @Override
    public Long insert(Question question) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS)) {
        	pst.setLong(1, question.getVoteId());
            pst.setString(2, question.getContent());
            pst.setDate(2, Date.valueOf(question.getDateVote()));

            if (question.getDateVote() != null) {
                pst.setDate(3, Date.valueOf(question.getDateVote())); 
            } else {
                pst.setDate(3, null); 
            }

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating User failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении Question: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Question question) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_QUESTION)) {

        	pst.setLong(1, question.getVoteId()); 
            pst.setString(2, question.getContent());

            if (question.getDateVote() != null) {
                pst.setDate(3, Date.valueOf(question.getDateVote()));
            } else {
                pst.setDate(3, null);
            }

            pst.setLong(4, question.getId());  //Corrected to 4
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении Question: " + e.getMessage(), e);
        }
    }
   
    @Override
    public void delete(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_QUESTION)) {

            pst.setLong(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении Question: " + e.getMessage(), e);
        }
    }

    @Override
    public Question findById(Long id) throws DAOException {
    	Question question = null;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_QUESTION_BY_ID)) {

            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                	question = new Question();
                	question.setId(rs.getLong("id"));
                    question.setVoteId(rs.getLong("voteId"));
                    question.setContent(rs.getString("content"));
                  	java.sql.Date sqlDate = rs.getDate("dateVote");
                    if (sqlDate != null) {
                        question.setDateVote(sqlDate.toLocalDate());
                    } else {
                    	question.setDateVote(null);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при поиске Question по ID: " + e.getMessage(), e);
        }
        return question;
    }

    @Override
    public List<Question> findAll() throws DAOException {
        List<Question> questions = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL_QUESTIONS);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            	Question question = new Question();
            	question.setId(rs.getLong("id"));
            	question.setVoteId(rs.getLong("voteId"));
                question.setContent(rs.getString("content"));
            	java.sql.Date sqlDate = rs.getDate("dateVote");
                if (sqlDate != null) {
                    question.setDateVote(sqlDate.toLocalDate());
                } else {
                	question.setDateVote(null);
                }
                questions.add(question);
            }

            System.out.println("Найдено Question: " + questions.size()); // ADD THIS LINE

        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка Question: " + e.getMessage(), e);
        }
        return questions;
    }
    public List<Question> getByVoteId(int voteId) throws DAOException {
        List<Question> questions = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_QUESTIONS_BY_VOTE_ID)) {

            pst.setInt(1, voteId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getLong("id"));
                    question.setVoteId(rs.getLong("voteId"));
                    question.setContent(rs.getString("content"));
                    Date dateVote = rs.getDate("dateVote");
                     if (dateVote != null) {
                         question.setDateVote(dateVote.toLocalDate()); // Convert back to LocalDate
                     }
                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении Question по voteId: " + e.getMessage(), e);
        }

        return questions;
    }
}