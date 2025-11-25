package dao;

import app.AppConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Singleton Connection Pool using HikariCP
 * Thread-safe and efficient connection management
 */
public class DBConnection {
    private static HikariDataSource dataSource;
    private static volatile DBConnection instance;

    // Private constructor để đảm bảo Singleton
    private DBConnection() {
        initializeDataSource();
    }

    /**
     * Get a singleton instance (Thread-safe Double-Checked Locking)
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Initialize HikariCP DataSource
     */
    private void initializeDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(AppConfig.getDbUrl());
            config.setUsername(AppConfig.getDbUser());
            config.setPassword(AppConfig.getDbPassword());

            // Connection Pool Configuration
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            // Performance settings
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            System.out.println("✓ Kết nối database thành công!");
        } catch (Exception e) {
            System.err.println("✗ Lỗi kết nối database: " + e.getMessage());
            throw new RuntimeException("Không thể khởi tạo connection pool", e);
        }
    }

    /**
     * Get connection from pool
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            getInstance();
        }
        return dataSource.getConnection();
    }

    /**
     * Close the connection pool (call on app shutdown)
     */
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Get pool statistics (for monitoring)
     */
    public static String getPoolStats() {
        if (dataSource != null) {
            return String.format("Active: %d, Idle: %d, Total: %d, Waiting: %d",
                    dataSource.getHikariPoolMXBean().getActiveConnections(),
                    dataSource.getHikariPoolMXBean().getIdleConnections(),
                    dataSource.getHikariPoolMXBean().getTotalConnections(),
                    dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        }
        return "DataSource not initialized";
    }
}