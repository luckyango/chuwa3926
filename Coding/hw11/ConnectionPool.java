package Coding.hw11;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

// Design a simple connection pool that supports the following features:
// 1. Initialize with minSize connections
// 2. getConnection(): Get a connection (create new one if pool is empty and under maxSize)
// 3. releaseConnection(Connection conn): Return a connection to the pool
// 4. Use BlockingQueue for available connections
// Complete the skeleton code:
// public class SimpleConnectionPool {
//  private final BlockingQueue<Connection> available;
//  private final Set<Connection> inUse;
// java-backend-system-design-homework.md 2026-04-23
// 4 / 5
//  private final int maxSize;
//  private final DataSource dataSource;
//  public SimpleConnectionPool(DataSource ds, int minSize, int maxSize) {
//  this.dataSource = ds;
//  this.maxSize = maxSize;
//  this.available = new LinkedBlockingQueue<>();
//  this.inUse = ConcurrentHashMap.newKeySet();
//  // TODO: Pre-create minSize connections
//  }
//  public Connection getConnection(long timeoutMs) throws SQLException {
//  // TODO: Implement
//  // 1. Try to poll from available queue
//  // 2. If null and total connections < maxSize, create new
//  // 3. If null and at maxSize, wait with timeout
//  // 4. Add to inUse set before returning
//  }
//  public void releaseConnection(Connection conn) {
//  // TODO: Implement
//  // 1. Remove from inUse
//  // 2. Add back to available queue
//  }
//  private int totalConnections() {
//  return available.size() + inUse.size();
//  }
// }
public class ConnectionPool {
    private final BlockingQueue<Connection> available;
    private final Set<Connection> inUse;
    private final int maxSize;
    private final DataSource dataSource;

    public ConnectionPool(DataSource ds, int minSize, int maxSize) throws SQLException{
        this.dataSource = ds;
        this.maxSize = maxSize;
        this.available = new LinkedBlockingQueue<>();
        this.inUse = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < minSize; i++) {
            available.add(dataSource.getConnection());
        }
    }
    public Connection getConnection(long timeoutMs) throws SQLException {
        Connection conn = available.poll();

        if (conn == null) {
            if (totalConnections() < maxSize) {
                conn = dataSource.getConnection();
            } else {
                try {
                    conn = available.poll(timeoutMs, TimeUnit.MILLISECONDS);
                    if (conn == null) {
                        throw new SQLException("Timeout");
                    }
                } catch (InterruptedException e) {
                    throw new SQLException(e);
                }
            }
        }

        inUse.add(conn);
        return conn;
    }

    public void releaseConnection(Connection conn) {
        inUse.remove(conn);
        available.offer(conn);
    }
    private int totalConnections() {
        return available.size() + inUse.size();
    }
}

