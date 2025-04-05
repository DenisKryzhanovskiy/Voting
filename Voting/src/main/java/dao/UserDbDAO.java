package dao;

import domain.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class UserDbDAO implements RepositoryDAO<User> {

    // SQL-запросы к таблице PC базы данных
    private static final String SELECT_ALL_USERS = "SELECT id, firstName, lastName, email, phone, status FROM User";
    private static final String SELECT_USER_BY_ID = "SELECT id, firstName, lastName, email, phone, status FROM User WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO User (firstName, lastName, email, phone, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE User SET firstName = ?, lastName = ?, email = ?, phone = ?, status = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM User WHERE id = ?";


    private final ConnectionBuilder builder = new DbConnectionBuilder();

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    @Override
    public Long insert(User user) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getStatus());

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating User failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении User: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_USER)) {

            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getStatus());
            pst.setLong(6, user.getId());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении User: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_USER)) {

            pst.setLong(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении User: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(Long id) throws DAOException {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_USER_BY_ID)) {

            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                	user = new User();
                	user.setId(rs.getLong("id"));
                	user.setFirstName(rs.getString("firstName"));
                	user.setLastName(rs.getString("lastName"));
                	user.setEmail(rs.getString("email"));
                	user.setPhone(rs.getString("phone"));
                	user.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при поиске User по ID: " + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                User user = new User();
            	user.setId(rs.getLong("id"));
            	user.setFirstName(rs.getString("firstName"));
            	user.setLastName(rs.getString("lastName"));
            	user.setEmail(rs.getString("email"));
            	user.setPhone(rs.getString("phone"));
            	user.setStatus(rs.getString("status"));
            	
            	users.add(user);
            }

            System.out.println("Найдено User: " + users.size()); // ADD THIS LINE

        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка User: " + e.getMessage(), e);
        }
        return users;
    }
}