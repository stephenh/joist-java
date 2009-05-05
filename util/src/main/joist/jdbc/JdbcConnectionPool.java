/*
 * Copyright 2004-2009 H2 Group. Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: Christian d'Heureuse, www.source-code.biz
 *
 * This class is dual-licensed LGPL and under the H2 License.
 *
 * This module is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * See http://www.gnu.org/licenses/lgpl.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 */
package joist.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Stack;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

/**
 * A simple standalone JDBC connection pool.
 * It is based on the
 * <a href="http://www.source-code.biz/snippets/java/8.htm">
 *  MiniConnectionPoolManager written by Christian d'Heureuse (Java 1.5)
 * </a>. It is used as follows:
 * <pre>
 * import java.sql.*;
 * import org.h2.jdbcx.JdbcConnectionPool;
 * public class Test {
 *     public static void main(String[] args) throws Exception {
 *         JdbcConnectionPool cp = JdbcConnectionPool.create(
 *             "jdbc:h2:~/test", "sa", "sa");
 *         for (int i = 0; i &lt; args.length; i++) {
 *             Connection conn = cp.getConnection();
 *             conn.createStatement().execute(args[i]);
 *             conn.close();
 *         }
 *         cp.dispose();
 *     }
 * }
 * </pre>
 *
 * @author Christian d'Heureuse
 *      (<a href="http://www.source-code.biz">www.source-code.biz</a>)
 * @author Thomas Mueller (ported to Java 1.4, some changes)
 */
public class JdbcConnectionPool implements DataSource {

    private static final int DEFAULT_TIMEOUT = 5 * 60;

    private final ConnectionPoolDataSource dataSource;
    private final Stack<PooledConnection> recycledConnections = new Stack<PooledConnection>();
    private final PoolConnectionEventListener poolConnectionEventListener = new PoolConnectionEventListener();
    private PrintWriter logWriter;
    private int maxConnections = 10;
    private int timeout = JdbcConnectionPool.DEFAULT_TIMEOUT;
    private int activeConnections;
    private boolean isDisposed;

    private JdbcConnectionPool(ConnectionPoolDataSource dataSource) {
        this.dataSource = dataSource;
        try {
            this.logWriter = dataSource.getLogWriter();
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * Constructs a new connection pool.
     *
     * @param dataSource the data source to create connections
     * @return the connection pool
     */
    public static JdbcConnectionPool create(ConnectionPoolDataSource dataSource) {
        return new JdbcConnectionPool(dataSource);
    }

    /**
     * Sets the maximum number of connections to use from now on.
     * The default value is 10 connections.
     *
     * @param max the maximum number of connections
     */
    public synchronized void setMaxConnections(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("Invalid maxConnections value: " + max);
        }
        this.maxConnections = max;
        // notify waiting threads if the value was increased
        this.notifyAll();
    }

    /**
     * Gets the maximum number of connections to use.
     *
     * @return the max the maximum number of connections
     */
    public synchronized int getMaxConnections() {
        return this.maxConnections;
    }

    /**
     * Gets the maximum time in seconds to wait for a free connection.
     *
     * @return the timeout in seconds
     */
    public synchronized int getLoginTimeout() {
        return this.timeout;
    }

    /**
     * Sets the maximum time in seconds to wait for a free connection.
     * The default timeout is 5 minutes. Calling this method with the
     * value 0 will set the timeout to the default value.
     *
     * @param seconds the timeout, 0 meaning the default
     */
    public synchronized void setLoginTimeout(int seconds) {
        if (seconds == 0) {
            seconds = JdbcConnectionPool.DEFAULT_TIMEOUT;
        }
        this.timeout = seconds;
    }

    /**
     * Closes all unused pooled connections.
     */
    public synchronized void dispose() throws SQLException {
        if (this.isDisposed) {
            return;
        }
        this.isDisposed = true;
        SQLException e = null;
        while (!this.recycledConnections.isEmpty()) {
            PooledConnection pc = this.recycledConnections.pop();
            try {
                pc.close();
            } catch (SQLException e2) {
                if (e == null) {
                    e = e2;
                }
            }
        }
        if (e != null) {
            throw e;
        }
    }

    /**
     * Retrieves a connection from the connection pool. If
     * <code>maxConnections</code> connections are already in use, the method
     * waits until a connection becomes available or <code>timeout</code>
     * seconds elapsed. When the application is finished using the connection,
     * it must close it in order to return it to the pool.
     * If no connection becomes available within the given timeout, an exception
     * with SQL state 08001 and vendor code 8001 is thrown.
     *
     * @return a new Connection object.
     * @throws SQLException when a new connection could not be established,
     *      or a timeout occurred
     */
    public Connection getConnection() throws SQLException {
        for (int i = 0;; i++) {
            synchronized (this) {
                if (this.activeConnections < this.maxConnections) {
                    return this.getConnectionNow();
                }
                if (i >= this.timeout) {
                    throw new SQLException("Login timeout", "08001", 8001);
                }
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }

    private Connection getConnectionNow() throws SQLException {
        if (this.isDisposed) {
            throw new IllegalStateException("Connection pool has been disposed.");
        }
        PooledConnection pc;
        if (!this.recycledConnections.empty()) {
            pc = this.recycledConnections.pop();
        } else {
            pc = this.dataSource.getPooledConnection();
        }
        Connection conn = pc.getConnection();
        this.activeConnections++;
        pc.addConnectionEventListener(this.poolConnectionEventListener);
        return conn;
    }

    /**
     * This method usually puts the connection back into the pool. There are
     * some exception: If the pool is disposed, the connection is disposed as
     * well. If the pool is full, the connection is closed.
     *
     * @param pc the pooled connection
     */
    synchronized void recycleConnection(PooledConnection pc) {
        if (this.isDisposed) {
            this.disposeConnection(pc);
            return;
        }
        if (this.activeConnections <= 0) {
            throw new AssertionError();
        }
        this.activeConnections--;
        if (this.activeConnections < this.maxConnections) {
            this.recycledConnections.push(pc);
        } else {
            this.closeConnection(pc);
        }
        this.notifyAll();
    }

    private void closeConnection(PooledConnection pc) {
        try {
            pc.close();
        } catch (SQLException e) {
            this.log("Error while closing database connection: " + e.toString());
        }
    }

    /**
     * Close the connection, and don't add it back to the pool.
     *
     * @param pc the pooled connection
     */
    synchronized void disposeConnection(PooledConnection pc) {
        if (this.activeConnections <= 0) {
            throw new AssertionError();
        }
        this.activeConnections--;
        this.notifyAll();
        this.closeConnection(pc);
    }

    private void log(String msg) {
        String s = this.getClass().getName() + ": " + msg;
        try {
            if (this.logWriter == null) {
                System.err.println(s);
            } else {
                this.logWriter.println(s);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * This event listener informs the connection pool that about closed and
     * broken connections.
     */
    class PoolConnectionEventListener implements ConnectionEventListener {
        public void connectionClosed(ConnectionEvent event) {
            PooledConnection pc = (PooledConnection) event.getSource();
            pc.removeConnectionEventListener(this);
            JdbcConnectionPool.this.recycleConnection(pc);
        }

        public void connectionErrorOccurred(ConnectionEvent event) {
            PooledConnection pc = (PooledConnection) event.getSource();
            pc.removeConnectionEventListener(this);
            JdbcConnectionPool.this.disposeConnection(pc);
        }
    }

    /**
     * Returns the number of active (open) connections of this pool. This is the
     * number of <code>Connection</code> objects that have been issued by
     * getConnection() for which <code>Connection.close()</code> has
     * not yet been called.
     *
     * @return the number of active connections.
     */
    public synchronized int getActiveConnections() {
        return this.activeConnections;
    }

    /**
     * INTERNAL
     */
    public Connection getConnection(String username, String password) {
        throw new UnsupportedOperationException();
    }

    /**
     * INTERNAL
     */
    public PrintWriter getLogWriter() {
        return this.logWriter;
    }

    /**
     * INTERNAL
     */
    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    /**
     * [Not supported] Return an object of this class if possible.
     *
     * @param iface the class
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("unwrap");
    }

    /**
     * [Not supported] Checks if unwrap can return an object of this class.
     *
     * @param iface the class
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("isWrapperFor");
    }

}
