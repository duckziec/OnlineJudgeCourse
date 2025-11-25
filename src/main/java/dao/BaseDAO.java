package dao;

import exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base DAO with Template Method Pattern
 * Handles common DB operations and resource management
 */
public abstract class BaseDAO<T> {

    /**
     * Execute a SELECT query and return a list of objects
     */
    protected List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        List<T> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapper.mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Query execution failed: " + sql, e);
        }

        return list;
    }

    /**
     * Execute INSERT, UPDATE, DELETE
     */
    protected boolean executeUpdate(String sql, Object... params) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Update execution failed: " + sql, e);
        }
    }

    /**
     * Execute a query and return the single result
     */
    protected T executeQuerySingle(String sql, RowMapper<T> mapper, Object... params) {
        List<T> list = executeQuery(sql, mapper, params);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Execute INSERT and return generated ID
     */
    protected int executeInsertWithGeneratedKey(String sql, Object... params) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            setParameters(stmt, params);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

            throw new DatabaseException("Insert failed, no ID generated");

        } catch (SQLException e) {
            throw new DatabaseException("Insert with key generation failed: " + sql, e);
        }
    }

    /**
     * Execute batch INSERT/UPDATE
     */
    protected int[] executeBatch(String sql, List<Object[]> paramsList) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Object[] params : paramsList) {
                setParameters(stmt, params);
                stmt.addBatch();
            }

            return stmt.executeBatch();

        } catch (SQLException e) {
            throw new DatabaseException("Batch execution failed: " + sql, e);
        }
    }

    /**
     * Set parameters for PreparedStatement
     */
    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Execute in transaction (for complex operations)
     */
    protected void executeInTransaction(TransactionCallback callback) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            callback.execute(conn);

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Rollback failed", ex);
                }
            }
            throw new DatabaseException("Transaction failed", e);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error instead of printStackTrace
                    System.err.println("Failed to close connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Functional interface for transaction callback
     */
    @FunctionalInterface
    protected interface TransactionCallback {
        void execute(Connection conn) throws SQLException;
    }
}