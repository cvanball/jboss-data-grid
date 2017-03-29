package com.redhat.jdg.producer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>ConnectionFactory</code> for database connections.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 * @author <a href="mailto:cojan.van.ballegooijen@redhat.com">Cojan van Ballegooijen</a>
 */
public class DbConnectionFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbConnectionFactory.class);

	private static final String DB_DRIVER_CLASS_NAME;
	
	private static final String DB_URL;

	private static final String DB_HOST;

	private static final String DB_PORT;

	private static final String DB_NAME;

	private static final String DB_USERNAME;

	private static final String DB_PASSWORD;
	
	// Load the db driver class.
	static {
		DB_DRIVER_CLASS_NAME = System.getProperty("db.driver.class");
		DB_URL = System.getProperty("db.url");
		DB_HOST = System.getProperty("db.hostname");
		DB_PORT = System.getProperty("db.port");
		DB_NAME = System.getProperty("db.name");
		DB_USERNAME = System.getProperty("db.username");
		DB_PASSWORD = System.getProperty("db.password");
		try {
			Class.forName(DB_DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException cnfe) {
			String message = "Can not find database driver.";
			LOGGER.error(message, cnfe);
			throw new RuntimeException(message, cnfe);
		}
	}

	public Connection getConnection() {

		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					DB_URL + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException sqle) {
			String message = "Unable to create database connection.";
			LOGGER.error(message);
			throw new RuntimeException(message, sqle);
		}
		return connection;
	}

}
