package dao;

import domain.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class QuestionDbDAO implements RepositoryDAO<Question> {

    private static final String SELECT_ALL_QUESTIONS =
            "SELECT id, voteid, content, datevote FROM question ORDER BY id";
    private static final String SELECT_QUESTION_BY_ID =
            "SELECT id, voteid, content, datevote FROM question WHERE id = ?";
    private static final String INSERT_QUESTION =
            "INSERT INTO question (voteid, content, datevote) VALUES (?, ?, ?)";
    private static final String UPDATE_QUESTION =
            "UPDATE question SET voteid = ?, content = ?, datevote = ? WHERE id = ?";
    private static final String DELETE_QUESTION =
            "DELETE FROM question WHERE id = ?";
    private static final String SELECT_QUESTIONS_BY_VOTE_ID =
            "SELECT id, voteid, content, datevote FROM question WHERE voteid = ?";
    private static final String DELETE_CHOICES_BY_QUESTION_ID =
            "DELETE FROM choice WHERE questionid = ?";

    private final ConnectionBuilder builder = new DbConnectionBuilder();

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private Question mapResultSetToQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getLong("id"));
        question.setVoteId(rs.getLong("voteid"));
        question.setContent(rs.getString("content"));
        java.sql.Date sqlDate = rs.getDate("datevote");
        question.setDateVote(sqlDate != null ? sqlDate.toLocalDate() : null);
        return question;
    }

    @Override
    public Long insert(Question question) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS)) {

            pst.setLong(1, question.getVoteId());
            pst.setString(2, question.getContent());
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
                    throw new SQLException("Creating Question failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении Вопроса голосования: " + e.getMessage(), e);
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
            pst.setLong(4, question.getId());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении Вопроса голосования: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            // 1. Сначала удаляем все choice для этого вопроса
            try (PreparedStatement pstChoice =
                         con.prepareStatement(DELETE_CHOICES_BY_QUESTION_ID)) {
                pstChoice.setLong(1, id);
                int delChoices = pstChoice.executeUpdate();
                System.out.println("QuestionDbDAO.delete(): удалено choice = " + delChoices);
            }

            // 2. Потом удаляем сам question
            try (PreparedStatement pstQuestion =
                         con.prepareStatement(DELETE_QUESTION)) {
                pstQuestion.setLong(1, id);
                int delQ = pstQuestion.executeUpdate();
                System.out.println("QuestionDbDAO.delete(): удалено вопросов = " + delQ);
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ignore) {}
            }
            throw new DAOException("Ошибка при удалении Вопроса голосования: " + e.getMessage(), e);
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (SQLException ignore) {}
            }
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
                    question = mapResultSetToQuestion(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении Вопроса голосования по ID: " + e.getMessage(), e);
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
                Question question = mapResultSetToQuestion(rs);
                questions.add(question);
            }
            System.out.println("QuestionDbDAO.findAll(): найдено вопросов = " + questions.size());

        } catch (SQLException e) {
            System.out.println("QuestionDbDAO.findAll() ERROR: " + e.getMessage());
            e.printStackTrace();
            throw new DAOException("Ошибка при получении списка Вопросов голосования: " + e.getMessage(), e);
        }
        return questions;
    }

    public List<Question> getByVoteId(Long voteId) throws DAOException {
        List<Question> questions = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_QUESTIONS_BY_VOTE_ID)) {

            pst.setLong(1, voteId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Question question = mapResultSetToQuestion(rs);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении Вопросов голосования по voteId: " + e.getMessage(), e);
        }
        return questions;
    }
}