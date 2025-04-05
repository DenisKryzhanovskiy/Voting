package dao;

import domain.Vote;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class VoteDbDAO implements RepositoryDAO<Vote> {

    // SQL-запросы к таблице Laptop базы данных
    private static final String SELECT_ALL_VOTES = "SELECT id, title, dateStart, dateFinish, status FROM Vote";
    private static final String SELECT_VOTE_BY_ID = "SELECT id, title, dateStart, dateFinish, status FROM Vote WHERE id = ?";
    private static final String INSERT_VOTE = "INSERT INTO Vote (title, dateStart, dateFinish, status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_VOTE = "UPDATE Vote SET title = ?, dateStart = ?, dateFinish = ?, status = ? WHERE id = ?";
    private static final String DELETE_VOTE = "DELETE FROM Vote WHERE id = ?";


    private final ConnectionBuilder builder = new DbConnectionBuilder();

    private Connection getConnection() throws SQLException {
        return builder.getConnection();
    }

    @Override
    public Long insert(Vote vote) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_VOTE, Statement.RETURN_GENERATED_KEYS)) {

        	pst.setString(1, vote.getTitle());

            if (vote.getDateStart() != null) {
                pst.setDate(2, Date.valueOf(vote.getDateStart()));
            } else {
                pst.setDate(2, null); 
            }


            if (vote.getDateFinish() != null) {
                pst.setDate(3, Date.valueOf(vote.getDateFinish()));
            } else {
                 pst.setDate(3, null);
            }

            pst.setString(4, vote.getStatus());

            pst.executeUpdate();

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating Vote failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при добавлении Vote: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Vote vote) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE_VOTE)) {

        	 pst.setString(1, vote.getTitle());

             if (vote.getDateStart() != null) {
                 pst.setDate(2, Date.valueOf(vote.getDateStart()));
             } else {
                  pst.setDate(2, null); 
             }


             if (vote.getDateFinish() != null) {
                 pst.setDate(3, Date.valueOf(vote.getDateFinish()));
             } else {
                  pst.setDate(3, null); 
             }


             pst.setString(4, vote.getStatus());
             pst.setLong(5, vote.getId());

             pst.executeUpdate();


        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении Vote: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_VOTE)) {

            pst.setLong(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Ошибка при удалении Vote: " + e.getMessage(), e);
        }
    }

    @Override
    public Vote findById(Long id) throws DAOException {
    	Vote vote = null;
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_VOTE_BY_ID)) {

            pst.setLong(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    vote = new Vote();
                    vote.setId(rs.getLong("id"));
                    vote.setTitle(rs.getString("title"));
                    java.sql.Date sqlDateStart = rs.getDate("dateStart");
                    if (sqlDateStart != null) {
                        vote.setDateStart(sqlDateStart.toLocalDate());
                    } else {
                        vote.setDateStart(null);
                    }

                    java.sql.Date sqlDateFinish = rs.getDate("dateFinish");
                    if (sqlDateFinish != null) {
                        vote.setDateFinish(sqlDateFinish.toLocalDate());
                    } else {
                        vote.setDateFinish(null);
                    }
                    vote.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при поиске Vote по ID: " + e.getMessage(), e);
        }
        return vote;
    }

    @Override
    public List<Vote> findAll() throws DAOException {
        List<Vote> votes = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_ALL_VOTES);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
            	Vote vote = new Vote();
            	vote.setId(rs.getLong("id"));
                vote.setTitle(rs.getString("title"));
                java.sql.Date sqlDateStart = rs.getDate("dateStart");
                if (sqlDateStart != null) {
                    vote.setDateStart(sqlDateStart.toLocalDate());
                } else {
                    vote.setDateStart(null);
                }

                java.sql.Date sqlDateFinish = rs.getDate("dateFinish");
                if (sqlDateFinish != null) {
                    vote.setDateFinish(sqlDateFinish.toLocalDate());
                } else {
                    vote.setDateFinish(null);
                }
                vote.setStatus(rs.getString("status"));
                votes.add(vote);
            }

        } catch (SQLException e) {
            throw new DAOException("Ошибка при получении списка Vote: " + e.getMessage(), e);
        }
        return votes;
    }
}