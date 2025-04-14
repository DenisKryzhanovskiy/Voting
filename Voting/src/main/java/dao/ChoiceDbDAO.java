package dao;

import domain.Choice;
import domain.Question;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class ChoiceDbDAO implements RepositoryDAO<Choice> {

    // SQL-запросы к таблице PC базы данных
    private static final String SELECT_ALL_CHOICES = "SELECT id, questionId, userId, choiceUser FROM Choice";
    private static final String SELECT_CHOICE_BY_ID = "SELECT id, questionId, userId, choiceUser FROM Choice WHERE id = ?";
    private static final String INSERT_CHOICE = "INSERT INTO Choice (questionId, userId, choiceUser) VALUES (?, ?, ?)";
    private static final String UPDATE_CHOICE = "UPDATE Choice SET questionId = ?, userId = ?, choiceUser = ? WHERE id = ?";
    private static final String DELETE_CHOICE = "DELETE FROM Choice WHERE id = ?";
    private static final String SELECT_CHOICES_BY_QUESTION_ID = "SELECT id, questionId, userId, choiceUser FROM Choice WHERE questionId = ?";
    private static final String SELECT_CHOICES_BY_USER_ID = "SELECT id, questionId, userId, choiceUser FROM Choice WHERE userId = ?";

    private final ConnectionBuilder builder = new DbConnectionBuilder();

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    private Choice mapResultSetToChoice(ResultSet rs) throws SQLException {
    	Choice choice = new Choice();
    	choice.setId(rs.getLong("id"));
    	choice.setQuestionId(rs.getLong("questionId"));
    	choice.setUserId(rs.getLong("userId"));
    	choice.setChoiceUser(rs.getString("choiceUser"));
        return choice;
    }
    
    @Override
    public Long insert(Choice choice) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_CHOICE, Statement.RETURN_GENERATED_KEYS)) {
        	
        	pst.setLong(1, choice.getQuestionId());
            pst.setLong(2, choice.getUserId());
            pst.setString(3, choice.getChoiceUser());

            pst.executeUpdate();
            
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating Choice failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении Выбора голосующего: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Choice choice) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_CHOICE)) {

        	pst.setLong(1, choice.getQuestionId());
            pst.setLong(2, choice.getUserId());
            pst.setString(3, choice.getChoiceUser());
            pst.setLong(4, choice.getId());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении Выбора голосующего: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_CHOICE)) {

            pst.setLong(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении Выбора голосующего: " + e.getMessage(), e);
        }
    }

    @Override
    public Choice findById(Long id) throws DAOException {
    	Choice choice = null;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_CHOICE_BY_ID)) {

            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                	choice = mapResultSetToChoice(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении Выбора голосующего по ID: " + e.getMessage(), e);
        }
        return choice;
    }

    @Override
    public List<Choice> findAll() throws DAOException {
        List<Choice> choices = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL_CHOICES);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            	Choice choice = mapResultSetToChoice(rs);
            	choices.add(choice);
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка Выборов голосующих: " + e.getMessage(), e);
        }
        return choices;
    }

public List<Choice> getByQuestionId(int questionId) throws DAOException {
    List<Choice> choices = new ArrayList<>();
    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(SELECT_CHOICES_BY_QUESTION_ID)) {

        pst.setInt(1, questionId);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Choice choice = new Choice();
                choice.setId(rs.getLong("id"));
                choice.setQuestionId(rs.getLong("questionId"));
                choice.setUserId(rs.getLong("userId"));
                choice.setChoiceUser(rs.getString("choiceUser"));
                choices.add(choice);
            }
        }
    } catch (SQLException e) {
        throw new DAOException("Ошибка при получении Выбора голосующего по choiceId: " + e.getMessage(), e);
    }
    return choices;
}

public List<Choice> getByUserId(int userId) throws DAOException {
    List<Choice> choices = new ArrayList<>();
    try (Connection con = getConnection();
         PreparedStatement pst = con.prepareStatement(SELECT_CHOICES_BY_USER_ID)) {

        pst.setInt(1, userId);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Choice choice = new Choice();
                choice.setId(rs.getLong("id"));
                choice.setQuestionId(rs.getLong("questionId"));
                choice.setUserId(rs.getLong("userId"));
                choice.setChoiceUser(rs.getString("choiceUser"));
                choices.add(choice);
            }
        }
    } catch (SQLException e) {
        throw new DAOException("Ошибка при получении Выбора голосующего по userId: " + e.getMessage(), e);
    }
    return choices;
}


}