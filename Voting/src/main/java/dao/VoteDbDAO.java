package dao;

import domain.User;
import domain.Vote;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.DAOException;

public class VoteDbDAO implements RepositoryDAO<Vote> {

    private static final String SELECT_ALL_VOTES = "SELECT id, title, dateStart, dateFinish, status FROM Vote ORDER BY Id";
    private static final String SELECT_VOTE_BY_ID = "SELECT id, title, dateStart, dateFinish, status FROM Vote WHERE id = ?";
    private static final String INSERT_VOTE = "INSERT INTO Vote (title, dateStart, dateFinish, status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_VOTE = "UPDATE Vote SET title = ?, dateStart = ?, dateFinish = ?, status = ? WHERE id = ?";
    private static final String DELETE_VOTE = "DELETE FROM Vote WHERE id = ?";
    private static final String DELETE_QUESTIONS_BY_VOTE_ID = "DELETE FROM Question WHERE voteid = ?";
    private static final String DELETE_CHOICES_BY_QUESTION_ID = "DELETE FROM Choice WHERE questionid = ?"; 

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
            throw new DAOException("Ошибка при добавлении Голосования: " + e.getMessage(), e);
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
            throw new DAOException("Ошибка при обновлении Голосования: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws DAOException {
        Connection con = null;
        PreparedStatement deleteChoicesPst = null;
        PreparedStatement deleteQuestionsPst = null;
        PreparedStatement deleteVotePst = null;

        try {
            con = getConnection();
            con.setAutoCommit(false); // Start transaction

            // 1. ПЕРВЫЙ: Удаляем Choices для всех Questions этого Vote
            List<Long> questionIds = getQuestionIdsByVoteId(con, id);
            if (questionIds != null && !questionIds.isEmpty()) {
                deleteChoicesPst = con.prepareStatement(DELETE_CHOICES_BY_QUESTION_ID);
                for (Long questionId : questionIds) {
                    deleteChoicesPst.setLong(1, questionId);
                    deleteChoicesPst.addBatch();
                }
                deleteChoicesPst.executeBatch();
            }

            // 2. ВТОРОЙ: Удаляем Questions
            deleteQuestionsPst = con.prepareStatement(DELETE_QUESTIONS_BY_VOTE_ID);
            deleteQuestionsPst.setLong(1, id);
            deleteQuestionsPst.executeUpdate();

            // 3. ТРЕТИЙ: Удаляем Vote
            deleteVotePst = con.prepareStatement(DELETE_VOTE);
            deleteVotePst.setLong(1, id);
            deleteVotePst.executeUpdate();

            con.commit(); // Commit transaction if all operations were successful

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction in case of errors
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new DAOException("Ошибка при удалении Голосования: " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed
            try {
                if (deleteChoicesPst != null) deleteChoicesPst.close();
                if (deleteQuestionsPst != null) deleteQuestionsPst.close();
                if (deleteVotePst != null) deleteVotePst.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
    
    private List<Long> getQuestionIdsByVoteId(Connection con, Long voteId) throws SQLException {
        List<Long> questionIds = new ArrayList<>();
        final String SELECT_QUESTION_IDS_BY_VOTE_ID = "SELECT id FROM Question WHERE voteid = ?";
        try (PreparedStatement pst = con.prepareStatement(SELECT_QUESTION_IDS_BY_VOTE_ID)) {
            pst.setLong(1, voteId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    questionIds.add(rs.getLong("id"));
                }
            }
        }
        return questionIds;
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
            throw new DAOException("Ошибка при поиске Голосования по ID: " + e.getMessage(), e);
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
            throw new DAOException("Ошибка при получении списка Голосования: " + e.getMessage(), e);
        }
        return votes;
    }
    
    public void updateWithoutId(Vote vote) throws DAOException {
        String sql = "UPDATE Vote SET title = ?, dateStart = ?, dateFinish = ?, status = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

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

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DAOException("Не удалось обновить пользователя (возможно, пользователь не найден).");
            }
        } catch (SQLException e) {
            throw new DAOException("Ошибка при обновлении пользователя: " + e.getMessage(), e);
        }
    }
}